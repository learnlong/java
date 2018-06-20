package com.worksyun.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.worksyun.api.mapper.AppversionMapper;
import com.worksyun.api.mapper.UserbaseinfoMapper;
import com.worksyun.api.model.Appversion;
import com.worksyun.api.model.Userbaseinfo;
import com.worksyun.commons.model.LoginModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController("/Appversion")
@Api(description = "版本相关操作")
public class AppversionController {
	
	@Autowired
	private AppversionMapper appversionMapper;
	
	@Autowired
	private UserbaseinfoMapper userbaseinfoMapper;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/appversionUpdate")
	@ApiOperation(value = "app更新", notes = "app更新")
	@ResponseBody
	public ResponseEntity<LoginModel> appversionUpdate(
			@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "token", required = true) String token,
			@RequestParam(value = "versionNumber", required = true) String versionNumber,
			@RequestParam(value = "channel", required = true) String channel,
			HttpServletRequest request)
			 {
		Map<String, Object> ParamMap = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String,Object> mp = new HashMap<String,Object>();
			try {
				ParamMap.put("userId", userId);
				List<Userbaseinfo> userBaseInfo = userbaseinfoMapper.selectUserbaseinfoByUserId(ParamMap);
				// 验证用户是否存在
				if (userBaseInfo.isEmpty()) {
					return new ResponseEntity<LoginModel>(new LoginModel(false, "用户不存在！"), new HttpHeaders(),
							HttpStatus.OK);
				}
				// 验证token是否正确
				String newtoken = (String) redisTemplate.boundHashOps(userId).get("token");
				if (!newtoken.equals(token)) {
					return new ResponseEntity<LoginModel>(new LoginModel(false, "登录信息已过期，请重新登录！"), new HttpHeaders(),
							HttpStatus.OK);
				}
				//String versionNumber = request.getParameter("versionNumber");
				//mp.put("versionNumber", versionNumber);
				//String channel = request.getParameter("channel");
				int chann = Integer.parseInt(channel);
				mp.put("channel", chann);
				List<Appversion> appversion = appversionMapper.selectAppversion(mp);
				if(appversion==null) {
					return new ResponseEntity<LoginModel>(new LoginModel(false, "", data), new HttpHeaders(), HttpStatus.OK);
				}
				if(chann==2) {
					data.put("appversion", appversion.get(0));
					return new ResponseEntity<LoginModel>(new LoginModel(true, "", data), new HttpHeaders(), HttpStatus.OK);
				}
				String myversionNumber = "";
				if(appversion.size()>0) {
					myversionNumber = appversion.get(0).getVersionNumber();
				}
				int i =compareVersion(myversionNumber,versionNumber);
				if(i==1) {
					data.put("appversion", appversion.get(0));
					return new ResponseEntity<LoginModel>(new LoginModel(true, "", data), new HttpHeaders(), HttpStatus.OK);
				}else {
					return new ResponseEntity<LoginModel>(new LoginModel(false, "", data), new HttpHeaders(), HttpStatus.OK);
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				return new ResponseEntity<LoginModel>(new LoginModel(false, "操作异常！" + e.getStackTrace(), data),
						new HttpHeaders(), HttpStatus.OK);
			}	
	}
	
	public static int compareVersion(String version1, String version2) {
	    if (version1.equals(version2)) {
	        return 0;
	    }
	    String[] version1Array = version1.split("\\.");
	    String[] version2Array = version2.split("\\.");
	    int index = 0;
	    //获取最小长度值
	    int minLen = Math.min(version1Array.length, version2Array.length);
	    int diff = 0;
	    //循环判断每位的大小
	    while (index < minLen && (diff = Integer.parseInt(version1Array[index]) - Integer.parseInt(version2Array[index])) == 0) {
	        index++;
	    }
	    if (diff == 0) {
	        //如果位数不一致，比较多余位数
	        for (int i = index; i < version1Array.length; i++) {
	            if (Integer.parseInt(version1Array[i]) > 0) {
	                return 1;
	            }
	        }

	        for (int i = index; i < version2Array.length; i++) {
	            if (Integer.parseInt(version2Array[i]) > 0) {
	                return -1;
	            }
	        }
	        return 0;
	    } else {
	        return diff > 0 ? 1 : -1;
	    }
	}
}

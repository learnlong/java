package com.worksyun.api.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.worksyun.api.mapper.HardwareinfoMapper;
import com.worksyun.api.model.Hardwareinfo;
import com.worksyun.api.model.Mififlowstatistics;
import com.worksyun.api.service.MififlowstatisticsService;
import com.worksyun.commons.model.LoginModel;
import com.worksyun.commons.util.DateUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "mifi相关操作")
public class MifiController {

	@Autowired
	private MififlowstatisticsService mififlowstatisticsService;

	@RequestMapping(value = "/api/mi-fi/config", method = RequestMethod.GET)
	@ApiOperation(value = "Mi-Fi获取初始化配置", notes = "Mi-Fi获取初始化配置")
	public ResponseEntity<String> whitelist() {
		return ResponseEntity.ok().
				header("Content-Type", "text/plain").
				body("whitelist_domain1=itunes.apple.com \r\n" + 
						"whitelist_domain2=*.itunes.apple.com \r\n" + 
						"whitelist_domain3=*.phobos.apple.com \r\n" + 
						"whitelist_domain4=*.aaplimg.com  \r\n" + 
						"whitelist_domain5=*.mzstatic.com  \r\n" + 
						"whitelist_ip1=101.89.153.94 ");
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/api/mi-fi/stats", method = RequestMethod.POST)
	@ApiOperation(value = "Mi-Fi获取初始化配置", notes = "Mi-Fi获取初始化配置")
	//@ResponseBody
	public ResponseEntity<String> reset(@RequestParam(value = "mac", required = true) String mac,
			@RequestParam(value = "imei", required = true) String imei,
			@RequestParam(value = "start_at", required = true) Integer start_at,
			@RequestParam(value = "up", required = true) Integer up,
			@RequestParam(value = "down", required = true) Integer down,
			@RequestParam(value = "device_mac", required = true) String[] device_mac,
			HttpServletRequest request) {
		    String result = "";
		    //流量重置统计时间
		    Calendar c = Calendar.getInstance();
			// int seconds = 1521529976;//数据库中提取的数据
			long millions = new Long(start_at).longValue() * 1000;
			c.setTimeInMillis(millions);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = sdf.format(c.getTime());
			Date date= null;
			try {
				date = DateUtil.formatToDate(dateString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		    //移除设备mac并记录设备
		    for(int i = 0;i<device_mac.length;i++) {
		    	String devicemac = device_mac[i];
		    	//记录设备 设备为空不记录
		    	if(!devicemac.equals("")||devicemac!=null) {
		    		Mififlowstatistics mififlow = new Mififlowstatistics();
					mififlow.setDown(down);
					mififlow.setImei(imei);
					mififlow.setMac(mac);
					mififlow.setReset_time(date);
					mififlow.setUp(up);
					mififlow.setExt(devicemac);
					mififlow.setCreateTime(new Date());
					mififlowstatisticsService.save(mififlow);
		    	}
		    	String cmac = (String) redisTemplate.boundHashOps(devicemac).get("mac");
		    	String cmei = (String) redisTemplate.boundHashOps(devicemac).get("imei");
		    	String dmac = (String) redisTemplate.boundHashOps(devicemac).get("device_mac");
		    	if(cmac==null||cmei==null||dmac==null) {
		    		result = "remove_mac= " + "\r\n";
		    	}else {
		    		if(cmac.equals(mac)&&cmei.equals(imei)&&dmac.equals(devicemac)) {
			    		String initTime = (String) redisTemplate.boundHashOps(devicemac).get("initTime");
			    		Date initDate = null;
			    		try {
							initDate = DateUtil.formatToDate(initTime);
						} catch (ParseException e) {
							e.printStackTrace();
						}
			    		Calendar ca=Calendar.getInstance();
			    		ca.setTime(initDate);
			    		ca.add(Calendar.HOUR_OF_DAY, 8);
			    		Long dtime = ca.getTimeInMillis();
			    		Date tdate = new Date();
			    		Long ttime = tdate.getTime();
			    		if(dtime==ttime) {
			    			redisTemplate.delete(devicemac);
			    			result = result +"remove_mac"+i+1+"="+dmac +"\r\n";
			    		}else {
			    			result = "remove_mac= " + "\r\n";
			    		}
			    	}else {
			    		result = "remove_mac= " + "\r\n";
			    	}
		    		/*redisTemplate.delete(devicemac);
	    			result = result +"remove_mac"+i+1+"="+dmac +"\r\n";*/
		    	}
		    	
		    }
			
			//每3小时返回true
			Date systemdate = new Date();
			long systime = systemdate.getTime();
			c.add(Calendar.HOUR_OF_DAY, 3);
			Date clientdate = c.getTime();
			long clienttime = clientdate.getTime();
			if (systime >= clienttime) {
				result = result + "reset=true";
				return ResponseEntity.ok().header("Content-Type", "text/plain").body(result);
			} else {
				result = result + "reset=false";
				return ResponseEntity.ok().header("Content-Type", "text/plain").body(result);
			}
	}
	
	
	@Autowired
	private HardwareinfoMapper hardwareinfoMapper;
	
	@SuppressWarnings("unused")
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/api/mi-fi/valite", method = RequestMethod.POST)
	@ApiOperation(value = "app与后台交互验证", notes = "app与后台交互验证")
	//@ResponseBody
	public ResponseEntity<LoginModel> mifivalite(
			//@RequestParam(value = "mac", required = true) String mac,
			//@RequestParam(value = "imei", required = true) String imei,
			//@RequestParam(value = "device_mac", required = true) String device_mac,
			//@RequestParam(value = "userId", required = true) String userId,
			HttpServletRequest request
                       ) {
		String userId = request.getParameter("userId");
		String mac = request.getParameter("mac");
		String imei = request.getParameter("imei");
		String device_mac = request.getParameter("device_mac");
		Map<String,Object> mp = new HashMap<String,Object>();
		mp.put("mac", mac);
		mp.put("imei", imei);
		List<Hardwareinfo> hardwareinfo = hardwareinfoMapper.SelectHardwareinfoValite(mp);
		Map<String,Object> data = new HashMap<String,Object>();
		if(hardwareinfo.size()==0) {
			data.put("result", "false");
			return new ResponseEntity<LoginModel>(new LoginModel(true, "", data), new HttpHeaders(), HttpStatus.OK);
		}else {
			Date initDate = new Date();
			String initTime = DateUtil.format(initDate);
			Map<String,Object> mp2 = new HashMap<String,Object>();
			mp2.put("mac", mac);
			mp2.put("imei", imei);
			mp2.put("device_mac", device_mac);
			mp2.put("initTime", initTime);
			redisTemplate.opsForHash().putAll(device_mac, mp2);
			mp.put("userId", userId);
			List<Hardwareinfo> hardwareinfo2 = hardwareinfoMapper.SelectHardwareinfoValite2(mp);
			if(hardwareinfo2.isEmpty()) {
				data.put("isower", "0");
			}else {
				data.put("isower", "1");
			}
			data.put("result", "true");
			return new ResponseEntity<LoginModel>(new LoginModel(true, "", data), new HttpHeaders(), HttpStatus.OK);
		}
	}
}

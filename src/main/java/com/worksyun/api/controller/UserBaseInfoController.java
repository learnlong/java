package com.worksyun.api.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.PatternSyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.worksyun.api.mapper.UserbaseinfoMapper;
import com.worksyun.api.model.Userbaseinfo;
import com.worksyun.api.service.UserBaseInfoService;
import com.worksyun.commons.model.LoginModel;
import com.worksyun.commons.util.Md5;
import com.worksyun.commons.util.UploadFileUtil;
import com.worksyun.commons.util.UseCommonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Demo
 * 
 * @auth:cyf
 * @see: [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@RestController("/userBaseInfo")
@Api(description = "用户操作相关API")
public class UserBaseInfoController {
	@Autowired
	private UserBaseInfoService userBaseInfoService;

	@Autowired
	private UserbaseinfoMapper userbaseinfoMapper;

	@SuppressWarnings("unused")
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/registered", method = RequestMethod.POST)
	@ApiOperation(value = "注册", notes = "注册")
	@ResponseBody
	public ResponseEntity<LoginModel> registered(@RequestParam(value = "mobile", required = true) String mobile,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "messagecode", required = true) String messagecode,
			@RequestParam(value = "area", required = true) String area, HttpSession session) {
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			boolean bl = UseCommonUtil.isPhoneLegal(mobile);
			if (!bl) {
				return new ResponseEntity<LoginModel>(new LoginModel(false, "请输入正确手机号!", data), new HttpHeaders(),
						HttpStatus.OK);
			}
			Userbaseinfo userbaseinfos = new Userbaseinfo();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("mobile", mobile);
			List<Userbaseinfo> userBaseInfo = userbaseinfoMapper.selectUserbaseinfoByTel(paramMap);
			if (userBaseInfo.size() != 0) {
				if (mobile.equals("18301873352")) {
					userbaseinfoMapper.updateByPrimaryKey(userBaseInfo.get(0));
					return new ResponseEntity<LoginModel>(new LoginModel(true, "", data), new HttpHeaders(),
							HttpStatus.OK);
				} else {
					return new ResponseEntity<LoginModel>(new LoginModel(false, "该用户已存在!", data), new HttpHeaders(),
							HttpStatus.OK);
				}
			}
			//获取验证码
			//String mymessagecode = (String) session.getAttribute("messagecode");
			String mymessagecode = (String) redisTemplate.boundHashOps(mobile).get("messagecode");
				if (!messagecode.equals(mymessagecode)) {
					return new ResponseEntity<LoginModel>(new LoginModel(false, "验证码不正确，请重新输入!", data),
							new HttpHeaders(), HttpStatus.OK);
				}
			
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			UseCommonUtil u = new UseCommonUtil();
			String name = u.getStringRandom(8);
			userbaseinfos.setName(name);
			userbaseinfos.setUserid(uuid);
			userbaseinfos.setMobile(mobile);
			userbaseinfos.setArea(area);
			userbaseinfos.setStatus(1);
			userbaseinfos.setIntegral(0);
			userbaseinfos.setUsertype(2);
			userbaseinfos.setCardpicture1("");
			userbaseinfos.setCardpicture2("");
			userbaseinfos.setAvatar("");
			userbaseinfos.setCertificationid("");
			userbaseinfos.setCreationuserid("");
			userbaseinfos.setCreationusername("");
			userbaseinfos.setIdcard("");
			userbaseinfos.setModifytime(new Date());
			userbaseinfos.setModifyuserid("");
			userbaseinfos.setModifyusername("");
			userbaseinfos.setNickname("");
			userbaseinfos.setPassword(new Md5().toMD5(password));
			userbaseinfos.setCreationtime(new Date());
			userBaseInfoService.save(userbaseinfos);
			return new ResponseEntity<LoginModel>(new LoginModel(true, "", data), new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<LoginModel>(new LoginModel(false, "操作异常!" + e.getMessage(), data),
					new HttpHeaders(), HttpStatus.OK);
		}
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/changePassword")
	@ApiOperation(value = "修改密码", notes = "修改密码")
	public ResponseEntity<LoginModel> changePassword(@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "token", required = true) String token, HttpServletRequest request) {
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			Map<String, Object> ParamMap = new HashMap<String, Object>();
			ParamMap.put("userId", userId);
			List<Userbaseinfo> userBaseInfo = userbaseinfoMapper.selectUserbaseinfoByUserId(ParamMap);
			// 验证用户是否存在
			if (userBaseInfo.isEmpty()) {
				return new ResponseEntity<LoginModel>(new LoginModel(false, "用户不存在！", data), new HttpHeaders(),
						HttpStatus.OK);
			}
			// 验证token是否正确
			String newtoken = (String) redisTemplate.boundHashOps(userId).get("token");
			if (!newtoken.equals(token)) {
				return new ResponseEntity<LoginModel>(new LoginModel(false, "登录信息已过期，请重新登录！", data), new HttpHeaders(),
						HttpStatus.OK);
			}

			String password = request.getParameter("password");
			String oldpassword = request.getParameter("oldpassword");
			String oldpassword2 = userBaseInfo.get(0).getPassword();

			Userbaseinfo userbaseinfo = new Userbaseinfo();
			userbaseinfo = userbaseinfoMapper.selectByPrimaryKey(userId);
			if (password == null || oldpassword == null) {
				return new ResponseEntity<LoginModel>(new LoginModel(false, "密码不能为空!", data), new HttpHeaders(),
						HttpStatus.OK);
			}
			if (password.equals(oldpassword)) {
				return new ResponseEntity<LoginModel>(new LoginModel(false, "新密码和旧密码不能相同!", data), new HttpHeaders(),
						HttpStatus.OK);
			}
			String moldpassword = new Md5().toMD5(oldpassword);
			if (!moldpassword.equals(oldpassword2)) {
				return new ResponseEntity<LoginModel>(new LoginModel(false, "请输入正确旧密码!", data), new HttpHeaders(),
						HttpStatus.OK);
			}
			userbaseinfo.setPassword(new Md5().toMD5(password));
			userbaseinfoMapper.updateByPrimaryKey(userbaseinfo);
			redisTemplate.delete(userId);
			return new ResponseEntity<LoginModel>(new LoginModel(true, "修改密码成功！", data), new HttpHeaders(),
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<LoginModel>(new LoginModel(false, "操作异常!" + e.getMessage(), data),
					new HttpHeaders(), HttpStatus.OK);
		}
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/updateUserbaseInfo")
	@ApiOperation(value = "修改用户资料", notes = "修改用户资料")
	@ResponseBody
	public ResponseEntity<LoginModel> updateUserbaseInfo(@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "token", required = true) String token, HttpServletRequest request)
			throws IOException {
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			Map<String, Object> ParamMap = new HashMap<String, Object>();
			ParamMap.put("userId", userId);
			List<Userbaseinfo> userBaseInfo = userbaseinfoMapper.selectUserbaseinfoByUserId(ParamMap);
			// 验证用户是否存在
			if (userBaseInfo.isEmpty()) {
				return new ResponseEntity<LoginModel>(new LoginModel(false, "用户不存在！", data), new HttpHeaders(),
						HttpStatus.OK);
			}
			// 验证token是否正确
			String newtoken = (String) redisTemplate.boundHashOps(userId).get("token");
			if (!newtoken.equals(token)) {
				return new ResponseEntity<LoginModel>(new LoginModel(false, "登录信息已过期，请重新登录！", data), new HttpHeaders(),
						HttpStatus.OK);
			}
			String nickName = request.getParameter("nickName");

			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			String avater = null;
			MultipartFile pic = multipartRequest.getFile("avatar");
			if (pic == null) {
				avater = "";
			} else {
				avater = UploadFileUtil.createPath2(pic);
				userBaseInfo.get(0).setAvatar(avater);
			}
		
			userBaseInfo.get(0).setNickname(nickName);
			userBaseInfo.get(0).setModifytime(new Date());
			userbaseinfoMapper.updateByPrimaryKey(userBaseInfo.get(0));
			String a5 = "";
			if ("".equals(avater) || avater == null) {
				a5 = "";
			} else {
				String a2 = avater.replaceAll("\\\\", "/");
				String[] a3 = a2.split("/");
				String a4 = "";
				for (int i = 4; i < a3.length; i++) {
					a4 = a4 + "/" + a3[i];
				}
				//CustomSystemUtil cs = new CustomSystemUtil();
				//String addr = cs.getMyIP();
        		a5 = request.getScheme() + "://" + "116.62.240.253" + ":" + "8888" + "/upload" + a4.trim().toString();
			}
			data.put("avatar", a5);
			data.put("name", nickName);
			// data.put("userBaseInfo", userBaseInfo.get(0));
			return new ResponseEntity<LoginModel>(new LoginModel(true, "", data), new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {

			e.printStackTrace();
			return new ResponseEntity<LoginModel>(new LoginModel(false, "操作异常!" + e.getMessage(), data),
					new HttpHeaders(), HttpStatus.OK);
		}
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/showUserbaseInfo")
	@ApiOperation(value = "显示用户资料", notes = "显示用户资料")
	@ResponseBody
	public ResponseEntity<LoginModel> showUserbaseInfo(@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "token", required = true) String token, HttpServletRequest request)
			throws IOException {
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> ParamMap = new HashMap<String, Object>();
		try {
			ParamMap.put("userId", userId);
			List<Userbaseinfo> userBaseInfo = userbaseinfoMapper.selectUserbaseinfoByUserId(ParamMap);
			// 验证用户是否存在
			if (userBaseInfo.isEmpty()) {
				return new ResponseEntity<LoginModel>(new LoginModel(false, "用户不存在！", data), new HttpHeaders(),
						HttpStatus.OK);
			}
			// 验证token是否正确
			String newtoken = (String) redisTemplate.boundHashOps(userId).get("token");
			if (!newtoken.equals(token)) {
				return new ResponseEntity<LoginModel>(new LoginModel(false, "登录信息已过期，请重新登录！", data), new HttpHeaders(),
						HttpStatus.OK);
			}
			String avatar = userBaseInfo.get(0).getAvatar();
			String name = userBaseInfo.get(0).getNickname();
			String a5 = "";
			if ("".equals(avatar) || avatar == null) {
				a5 = "";
			} else {
				String a2 = avatar.replaceAll("\\\\", "/");
				String[] a3 = a2.split("/");
				String a4 = "";
				for (int i = 4; i < a3.length; i++) {
					a4 = a4 + "/" + a3[i];
				}
			    //CustomSystemUtil cs = new CustomSystemUtil();
			    //String addr = cs.getMyIP();
          		a5 = request.getScheme() + "://" + "116.62.240.253" + ":" + "8888" + "/upload" + a4.trim().toString();
			}
			data.put("avatar", a5);
			if (name == null) {
				name = "";
			}
			data.put("name", name);
			return new ResponseEntity<LoginModel>(new LoginModel(true, "", data), new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<LoginModel>(new LoginModel(false, "操作异常!" + e.getMessage(), data),
					new HttpHeaders(), HttpStatus.OK);
		}
	}
	
	
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/remeberPassword")
	@ApiOperation(value = "忘记密码", notes = "忘记密码")
	@ResponseBody
	public ResponseEntity<LoginModel> remeberPassword(
			@RequestParam(value = "mobile", required = true) String mobile,
			@RequestParam(value = "messagecode", required = true) String messagecode,
			@RequestParam(value = "password", required = true) String password,
			HttpSession session)
			throws IOException {
		Map<String,Object> data = new HashMap<String,Object>();
			try {
				boolean bl = UseCommonUtil.isPhoneLegal(mobile);
				if (!bl) {
					return new ResponseEntity<LoginModel>(new LoginModel(false, "请输入正确手机号!", data), new HttpHeaders(),
							HttpStatus.OK);
				}
				String mymessagecode = (String) redisTemplate.boundHashOps(mobile).get("messagecode");
					if (!messagecode.equals(mymessagecode)) {
						return new ResponseEntity<LoginModel>(new LoginModel(false, "验证码不正确，请重新输入!", data),
								new HttpHeaders(), HttpStatus.OK);
					}
					Map<String,Object> param = new HashMap<String,Object>();
					param.put("mobile", mobile);
					List<Userbaseinfo> userbaseinfo = userbaseinfoMapper.selectUserbaseinfoByTel(param);
					if(userbaseinfo.isEmpty()) {
						return new ResponseEntity<LoginModel>(new LoginModel(false, "该用户不存在!", data),
								new HttpHeaders(), HttpStatus.OK);
					}else {
						userbaseinfo.get(0).setPassword(new Md5().toMD5(password));
						userbaseinfoMapper.updateByPrimaryKey(userbaseinfo.get(0));
						return new ResponseEntity<LoginModel>(new LoginModel(true, "修改密码成功！", data), new HttpHeaders(), HttpStatus.OK);
					}
			} catch (PatternSyntaxException e) {
				return new ResponseEntity<LoginModel>(new LoginModel(false, "操作异常!" + e.getMessage(), data),
						new HttpHeaders(), HttpStatus.OK);
			}
	}
}

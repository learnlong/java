package com.worksyun.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.worksyun.api.mapper.CertificationMapper;
import com.worksyun.api.mapper.UserbaseinfoMapper;
import com.worksyun.api.model.Certification;
import com.worksyun.api.model.Userbaseinfo;
import com.worksyun.api.service.CertificationService;
import com.worksyun.commons.model.LoginModel;
import com.worksyun.commons.util.Md5;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController("/tokens")
@Api(description = "登录生成token")
public class TokenController {

	@SuppressWarnings("unused")
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private UserbaseinfoMapper userbaseinfoMapper;

	@Autowired
	private CertificationMapper certificationMapper;

	@SuppressWarnings("unused")
	@Autowired
	private CertificationService certificationService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ApiOperation(value = "登录", notes = "登录")
	public ResponseEntity login(@RequestParam(value = "mobile", required = true) String mobile,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "deviceid", required = true) String deviceid) {
		List<Userbaseinfo> userBaseInfo;
		String sendtoken;
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("mobile", mobile);
			userBaseInfo = userbaseinfoMapper.selectUserbaseinfoByTel(paramMap);
			sendtoken = UUID.randomUUID().toString().replace("-", "");
			if (userBaseInfo.size() == 0) {
				return new ResponseEntity<LoginModel>(new LoginModel(false, "用户不存在，请注册!", data), new HttpHeaders(),
						HttpStatus.OK);
			} else {
				String getpassword = userBaseInfo.get(0).getPassword();
				if (!getpassword.equals(new Md5().toMD5(password))) {
					return new ResponseEntity<LoginModel>(new LoginModel(false, "用户密码不匹配，请重新登录！", data),
							new HttpHeaders(), HttpStatus.OK);
				} else {
					Object userId = userBaseInfo.get(0).getUserid();
					// userbaseinfoMapper.updateByPrimaryKey(userBaseInfo.get(0));
					String CertificationId = userBaseInfo.get(0).getCertificationid();
					Certification certification = certificationMapper.selectByPrimaryKey(CertificationId);
					if (certification == null) {
						data.put("localhoststatus", "0");
					} else {
						data.put("localhoststatus", certification.getLocalhoststatus());
					}
					BoundHashOperations<String, String, String> boundHashOperations = redisTemplate
							.boundHashOps(userId);
					Map<String, String> newmap = boundHashOperations.entries();
					Map<String, Object> map = new HashMap<String, Object>();

					if (newmap.isEmpty()) {
						map.put("userid", userId);
						map.put("token", sendtoken);
						map.put("deviceid", deviceid);
						redisTemplate.opsForHash().putAll(userId, map);
						redisTemplate.expire(userId, 864000000, TimeUnit.MILLISECONDS);// 登录成功，设置token过期时间。
						data.put("userBaseInfo", userBaseInfo.get(0));
						data.put("token", sendtoken);
						return new ResponseEntity<LoginModel>(new LoginModel(true, "", data), new HttpHeaders(),
								HttpStatus.OK);
					} else {
						String newtoken = (String) redisTemplate.boundHashOps(userId).get("token");
						String newdeviceid = (String) redisTemplate.boundHashOps(userId).get("deviceid");
						if (!deviceid.equals(newdeviceid)) {
							redisTemplate.delete(userId);
							String sendtoken3 = UUID.randomUUID().toString().replace("-", "");
							Map<String, Object> newmap2 = new HashMap<String, Object>();
							newmap2.put("userid", userId);
							newmap2.put("token", sendtoken3);
							newmap2.put("deviceid", deviceid);
							redisTemplate.opsForHash().putAll(userId, newmap2);
							redisTemplate.expire(userId, 864000000, TimeUnit.MILLISECONDS);// 登录成功，设置token过期时间。
							data.put("userBaseInfo", userBaseInfo.get(0));
							data.put("token", sendtoken3);
							return new ResponseEntity<LoginModel>(new LoginModel(true, "", data), new HttpHeaders(),
									HttpStatus.OK);
						} else {
							data.put("userBaseInfo", userBaseInfo.get(0));
							data.put("token", newtoken);
							redisTemplate.expire(userId, 864000000, TimeUnit.MILLISECONDS);// 登录成功，设置token过期时间。
							return new ResponseEntity<LoginModel>(new LoginModel(true, "", data), new HttpHeaders(),
									HttpStatus.OK);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<LoginModel>(new LoginModel(false, "操作异常！" + e.getMessage(), data),
					new HttpHeaders(), HttpStatus.OK);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/checkToken", method = RequestMethod.POST)
	@ApiOperation(value = "快速登陆", notes = "快速登陆")
	public ResponseEntity checkToken(@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "token", required = true) String token) {
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			BoundHashOperations<String, String, String> boundHashOperations = redisTemplate.boundHashOps(userId);
			Map<String, String> oldmap = boundHashOperations.entries();
			String oldtoken = oldmap.get("token");
			if (oldtoken == null) {
				return new ResponseEntity<LoginModel>(new LoginModel(false, "token不存在！", data), new HttpHeaders(),
						HttpStatus.OK);
			}
			if (oldtoken.equals(token)) {
				Userbaseinfo userBaseInfo = userbaseinfoMapper.selectByPrimaryKey(userId);
				String CertificationId = userBaseInfo.getCertificationid();
				if(CertificationId==null) {
					data.put("localhoststatus", 0);
				}else {
					Certification certification = certificationMapper.selectByPrimaryKey(CertificationId);
					if (certification == null) {
						data.put("localhoststatus", 0);
					} else {
						data.put("localhoststatus", certification.getLocalhoststatus());
					}
				}
				
				data.put("userBaseInfo", userBaseInfo);
				data.put("token", token);
				return new ResponseEntity<LoginModel>(new LoginModel(true, "验证成功！", data), new HttpHeaders(),
						HttpStatus.OK);
			}

			return new ResponseEntity<LoginModel>(new LoginModel(false, "登录信息已过期，请重新登录！", data), new HttpHeaders(),
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<LoginModel>(new LoginModel(false, "操作异常！" + e.getMessage(), data),
					new HttpHeaders(), HttpStatus.OK);
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/loginOut", method = RequestMethod.POST)
	@ApiOperation(value = "退出登录", notes = "退出登录")
	public ResponseEntity<LoginModel> loginout(@RequestParam(value = "userId", required = true) String userId) {
		Map<String,Object> data = new HashMap<String,Object>();
		try {
			redisTemplate.delete(userId);
			return new ResponseEntity<LoginModel>(new LoginModel(true, "退出登录成功！", data), new HttpHeaders(),
					HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<LoginModel>(new LoginModel(false, "操作异常！" + e.getMessage(), data),
					new HttpHeaders(), HttpStatus.OK);
		}
	}
}
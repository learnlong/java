package com.worksyun.api.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.worksyun.api.mapper.UseraddressMapper;
import com.worksyun.api.mapper.UserbaseinfoMapper;
import com.worksyun.api.model.Useraddress;
import com.worksyun.api.model.Userbaseinfo;
import com.worksyun.api.service.UserAddressService;
import com.worksyun.commons.model.LoginModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController("/UserAddress")
@Api(description = "收货地址相关操作")
public class UserAddressController {

	@Autowired
	private UserbaseinfoMapper userbaseinfoMapper;

	@Autowired
	private UseraddressMapper UseradderessMapper;

	@SuppressWarnings("unused")
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private UserAddressService UserAddressService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveAddress", method = RequestMethod.POST)
	@ApiOperation(value = "新增收货地址", notes = "新增收货地址")
	@ResponseBody
	public ResponseEntity<LoginModel> saveAddress(@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "token", required = true) String token, HttpServletRequest request) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			paramMap.put("userId", userId);
			List<Userbaseinfo> userBaseInfo2 = userbaseinfoMapper.selectUserbaseinfoByUserId(paramMap);
			// 验证用户是否存在
			if (userBaseInfo2.isEmpty()) {
				return new ResponseEntity<LoginModel>(new LoginModel(false, "用户不存在！"), new HttpHeaders(),
						HttpStatus.OK);
			}
			// 验证token是否正确
			String newtoken = (String) redisTemplate.boundHashOps(userId).get("token");
			if (!newtoken.equals(token)) {
				return new ResponseEntity<LoginModel>(new LoginModel(false, "登录信息已过期，请重新登录！"), new HttpHeaders(),
						HttpStatus.OK);
			}
			String name = userBaseInfo2.get(0).getName();
			String userName = request.getParameter("userName");
			String contactNumber = request.getParameter("contactNumber");
			String shippingAddress = request.getParameter("shippingAddress");
			String postCode = request.getParameter("postCode");
			Useraddress usd = new Useraddress();
			Map<String, Object> tmap2 = new HashMap<String, Object>();
			tmap2.put("userId", userId);
			int count = UseradderessMapper.selectUserAddressByUserId4(tmap2);
			if (count > 10) {
				return new ResponseEntity<LoginModel>(new LoginModel(false, "地址请少于10个！", data), new HttpHeaders(),
						HttpStatus.OK);
			}
			String addressid = UUID.randomUUID().toString().replaceAll("-", "");
			usd.setAddressid(addressid);
			usd.setPostCode(postCode);
			usd.setUsername(userName);
			usd.setContactnumber(contactNumber);
			usd.setShippingaddress(shippingAddress);
			usd.setIsdefault(false);
			usd.setCreationtime(new Date());
			usd.setCreationuserid(userId);
			usd.setCreationusername(name);
			usd.setModifytime2(new Date());
			usd.setModifyusername("");
			usd.setModifyuserid("");
			usd.setStatus(1);
			usd.setUserid(userId);
			UserAddressService.save(usd);
			Map<String, Object> tmap = new HashMap<String, Object>();
			tmap.put("userId", userId);
			List<Useraddress> tuseraddress = UseradderessMapper.selectUserAddressByUserId(tmap);
			if (tuseraddress.size() == 0) {
				List<Useraddress> tuseraddress2 = UseradderessMapper.selectUserAddressByUserId2(tmap);
				Useraddress tu = tuseraddress2.get(0);
				tu.setIsdefault(true);
				UseradderessMapper.updateByPrimaryKey(tu);
			}
			// data.put("UserAddress", usd);
			return new ResponseEntity<LoginModel>(new LoginModel(true, "", data), new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<LoginModel>(new LoginModel(false, "操作异常！" + e.getStackTrace()), new HttpHeaders(),
					HttpStatus.OK);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/modifyAddress", method = RequestMethod.POST)
	@ApiOperation(value = "修改收货地址", notes = "修改收货地址")
	@ResponseBody
	public ResponseEntity<LoginModel> modifyAddress(@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "token", required = true) String token, HttpServletRequest request) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			paramMap.put("userId", userId);
			List<Userbaseinfo> userBaseInfo2 = userbaseinfoMapper.selectUserbaseinfoByUserId(paramMap);
			// 验证用户是否存在
			if (userBaseInfo2.isEmpty()) {
				return new ResponseEntity<LoginModel>(new LoginModel(false, "用户不存在！"), new HttpHeaders(),
						HttpStatus.OK);
			}
			// 验证token是否正确
			String newtoken = (String) redisTemplate.boundHashOps(userId).get("token");
			if (!newtoken.equals(token)) {
				return new ResponseEntity<LoginModel>(new LoginModel(false, "登录信息已过期，请重新登录！"), new HttpHeaders(),
						HttpStatus.OK);
			}
			String userName = request.getParameter("userName");
			String contactNumber = request.getParameter("contactNumber");
			String shippingAddress = request.getParameter("shippingAddress");
			String addressId = request.getParameter("addressId");
			String postCode = request.getParameter("postCode");
			Useraddress usd = UseradderessMapper.selectByPrimaryKey(addressId);
			usd.setAddressid(addressId);
			usd.setContactnumber(contactNumber);
			usd.setUsername(userName);
			usd.setShippingaddress(shippingAddress);
			usd.setPostCode(postCode);
			UseradderessMapper.updateByPrimaryKey(usd);
			return new ResponseEntity<LoginModel>(new LoginModel(true, "", data), new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<LoginModel>(new LoginModel(false, "操作异常！" + e.getStackTrace()), new HttpHeaders(),
					HttpStatus.OK);
		}

	}

	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping(value = "/getAdressByUserId", method = RequestMethod.GET)
	@ApiOperation(value = "查询收货地址", notes = "查询收货地址")
	@ResponseBody
	public ResponseEntity<LoginModel> getAdressByUserId(@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "token", required = true) String token) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			paramMap.put("userId", userId);
			List<Userbaseinfo> userBaseInfo2 = userbaseinfoMapper.selectUserbaseinfoByUserId(paramMap);
			// 验证用户是否存在
			if (userBaseInfo2.isEmpty()) {
				return new ResponseEntity<LoginModel>(new LoginModel(false, "用户不存在！"), new HttpHeaders(),
						HttpStatus.OK);
			}
			// 验证token是否正确
			String newtoken = (String) redisTemplate.boundHashOps(userId).get("token");
			if (!newtoken.equals(token)) {
				return new ResponseEntity<LoginModel>(new LoginModel(false, "登录信息已过期，请重新登录！"), new HttpHeaders(),
						HttpStatus.OK);
			}
			Map<String, Object> tmap = new HashMap<String, Object>();
			tmap.put("userId", userId);
			List<Useraddress> userAddress = UseradderessMapper.selectUserAddressByUserId3(tmap);
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

			for (int i = 0; i < userAddress.size(); i++) {
				Map<String, Object> m1 = new HashMap<String, Object>();
				String addressId = userAddress.get(i).getAddressid();
				m1.put("addressId", addressId);
				String userId2 = userAddress.get(i).getUserid();
				m1.put("userId", userId);
				Boolean isDefault = userAddress.get(i).getIsdefault();
				if (isDefault == true) {
					m1.put("isDefault", "1");
				} else {
					m1.put("isDefault", "0");
				}
				String userName = userAddress.get(i).getUsername();
				m1.put("userName", userName);
				String Contactnumber = userAddress.get(i).getContactnumber();
				m1.put("contactNumber", Contactnumber);
				String shippingAddress = userAddress.get(i).getShippingaddress();
				m1.put("shippingAddress", shippingAddress);
				String postCode = userAddress.get(i).getPostCode();
				m1.put("postCode", postCode);
				list.add(m1);
			}
			if (userAddress == null) {
				return new ResponseEntity<LoginModel>(new LoginModel(false, "用户地址不存在！"), new HttpHeaders(),
						HttpStatus.OK);
			} else {
				data.put("userAddress", list);
				return new ResponseEntity<LoginModel>(new LoginModel(true, "", data), new HttpHeaders(), HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<LoginModel>(new LoginModel(false, "操作异常！" + e.getStackTrace()), new HttpHeaders(),
					HttpStatus.OK);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteAddress", method = RequestMethod.POST)
	@ApiOperation(value = "删除收货地址", notes = "删除收货地址")
	@ResponseBody
	public ResponseEntity<LoginModel> deleteAddress(@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "token", required = true) String token, HttpServletRequest request) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			paramMap.put("userId", userId);
			List<Userbaseinfo> userBaseInfo2 = userbaseinfoMapper.selectUserbaseinfoByUserId(paramMap);
			// 验证用户是否存在
			if (userBaseInfo2.isEmpty()) {
				return new ResponseEntity<LoginModel>(new LoginModel(false, "用户不存在！"), new HttpHeaders(),
						HttpStatus.OK);
			}
			// 验证token是否正确
			String newtoken = (String) redisTemplate.boundHashOps(userId).get("token");
			if (!newtoken.equals(token)) {
				return new ResponseEntity<LoginModel>(new LoginModel(false, "登录信息已过期，请重新登录！"), new HttpHeaders(),
						HttpStatus.OK);
			}
			String addressId = request.getParameter("addressId");
			Useraddress useraddress = UseradderessMapper.selectByPrimaryKey(addressId);
			useraddress.setStatus(3);
			UseradderessMapper.updateByPrimaryKey(useraddress);
			return new ResponseEntity<LoginModel>(new LoginModel(true, "", data), new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<LoginModel>(new LoginModel(false, "操作异常！" + e.getStackTrace()), new HttpHeaders(),
					HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/updateDefault", method = RequestMethod.POST)
	@ApiOperation(value = "更改默认地址", notes = "更改默认地址")
	@ResponseBody
	public ResponseEntity<LoginModel> updateDefault(@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "token", required = true) String token, HttpServletRequest request) {
		String addressId = request.getParameter("addressId");
		String isDefault = request.getParameter("isDefault");
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("userId", userId);
			List<Useraddress> userAddress = UseradderessMapper.selectUserAddressByUserId3(map);
			if (userAddress.size() > 0) {
				for (int i = 0; i < userAddress.size(); i++) {
					String addressid = userAddress.get(0).getAddressid();
					Useraddress useraddress = UseradderessMapper.selectByPrimaryKey(addressid);
					useraddress.setIsdefault(false);
					;
					UseradderessMapper.updateByPrimaryKey(useraddress);
				}
			}
			Useraddress useraddress2 = UseradderessMapper.selectByPrimaryKey(addressId);
			if (isDefault.equals("1")) {
				useraddress2.setIsdefault(true);
			}
			UseradderessMapper.updateByPrimaryKey(useraddress2);
			return new ResponseEntity<LoginModel>(new LoginModel(true, "", data), new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<LoginModel>(new LoginModel(false, "操作异常！" + e.getStackTrace()), new HttpHeaders(),
					HttpStatus.OK);
		}
	}
}

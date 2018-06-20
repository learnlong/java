package com.worksyun.api.controller;

import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.worksyun.api.mapper.EquipmentorderMapper;
import com.worksyun.api.mapper.HardwareinfoMapper;
import com.worksyun.api.mapper.TelecommunicationMapper;
import com.worksyun.api.mapper.UseraddressMapper;
import com.worksyun.api.mapper.UserbaseinfoMapper;
import com.worksyun.api.model.Equipmentorder;
import com.worksyun.api.model.Hardwareinfo;
import com.worksyun.api.model.Telecommunication;
import com.worksyun.api.model.Useraddress;
import com.worksyun.api.model.Userbaseinfo;
import com.worksyun.api.service.EquipmentOrderService;
import com.worksyun.commons.model.LoginModel;
import com.worksyun.commons.util.CustomSystemUtil;
//import com.worksyun.commons.util.DateUtil;
import com.worksyun.commons.util.UseCommonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "订单相关操作")
public class EquipmentorderController {

	@Autowired
	private EquipmentorderMapper equipmentOrderMapper;

	@Autowired
	private HardwareinfoMapper hardwareinfoMapper;

	@Autowired
	private UserbaseinfoMapper userbaseinfoMapper;

	@Autowired
	private UseraddressMapper useraddressMapper;

	@Autowired
	private EquipmentOrderService equipmentOrderService;

	@Autowired
	private TelecommunicationMapper telecommunicationMapper;

	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/createPrice", method = RequestMethod.POST)
	@ApiOperation(value = "生成价格", notes = "生成价格")
	@ResponseBody
	public ResponseEntity<LoginModel> createPrice(@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "token", required = true) String token, HttpServletRequest request) {
		Map<String, Object> ParamMap = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			ParamMap.put("userId", userId);
			List<Userbaseinfo> userBaseInfo2 = userbaseinfoMapper.selectUserbaseinfoByUserId(ParamMap);
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
			List<Hardwareinfo> Hardwareinfo2 = hardwareinfoMapper.selectAll();
			BigDecimal price = Hardwareinfo2.get(0).getHardwareprice();
			List<Telecommunication> telecommunication2 = telecommunicationMapper.selectAll();
			BigDecimal price2 = telecommunication2.get(0).getTelecommunicationprice();
			price.add(price2);
			data.put("price", price.add(price2));
			return new ResponseEntity<LoginModel>(new LoginModel(true, "", data), new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {

			e.printStackTrace();
			return new ResponseEntity<LoginModel>(new LoginModel(false, "操作异常" + e.getStackTrace()), new HttpHeaders(),
					HttpStatus.OK);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/createOrder", method = RequestMethod.POST)
	@ApiOperation(value = "生成订单", notes = "生成订单")
	@ResponseBody
	public ResponseEntity<LoginModel> createOrder(@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "token", required = true) String token,
			@RequestParam(value = "addressId", required = true) String addressId,
			@RequestParam(value = "hardwareId", required = true) String hardwareId, HttpServletRequest request) {
		Map<String, Object> ParamMap = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			ParamMap.put("userId", userId);
			List<Userbaseinfo> userBaseInfo2 = userbaseinfoMapper.selectUserbaseinfoByUserId(ParamMap);
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
			List<Hardwareinfo> hardwareinfo = hardwareinfoMapper.selectHardwareinfoByStsatus();
			if (hardwareinfo.isEmpty()) {
				return new ResponseEntity<LoginModel>(new LoginModel(false, "设备已全部启用，无法再分配设备！"), new HttpHeaders(),
						HttpStatus.OK);
			}
			Map<String,Object> m2 = new HashMap<String,Object>();
			m2.put("hardwareId", hardwareId);
			List<Object> list  = new ArrayList<Object>();
			list.add("1");
			list.add("2");
			list.add("5");
			m2.put("list", list);
			int count2 = equipmentOrderMapper.selectCountOrderByhard(m2);
			if(count2>0) {
				Hardwareinfo hard = hardwareinfoMapper.selectByPrimaryKey(hardwareId);
				hard.setStatus(2);
				hardwareinfoMapper.updateByPrimaryKey(hard);
				return new ResponseEntity<LoginModel>(new LoginModel(false, "当前设备已被抢购，请重新下单！"), new HttpHeaders(),
						HttpStatus.OK);
			}
			// 生成价格
			List<Hardwareinfo> Hardwareinfo2 = hardwareinfoMapper.selectAll();
			BigDecimal price = Hardwareinfo2.get(0).getHardwareprice();
			List<Telecommunication> telecommunication2 = telecommunicationMapper.selectAll();
			BigDecimal price2 = telecommunication2.get(0).getTelecommunicationprice();
			
			// 随机分配一个设备
			Hardwareinfo hardwareinfo2 = hardwareinfoMapper.selectByPrimaryKey(hardwareId);
			if (hardwareinfo2 != null) {
				hardwareinfo2.setStatus(5);
				hardwareinfo2.setUserid(userId);
				hardwareinfoMapper.updateByPrimaryKey(hardwareinfo2);
			}

			// 生成订单
			Equipmentorder equipmentorder = new Equipmentorder();
			equipmentorder.setAddressid(addressId);
			equipmentorder.setHardwareid(hardwareId);
			equipmentorder.setCreationuserid(userId);
			equipmentorder.setUserid(userId);
			equipmentorder.setOrderstatus(1);
			equipmentorder.setCreationtime(new Date());
			equipmentorder.setStatus(1);
			equipmentorder.setPrice(price.add(price2));
			UseCommonUtil usc = new UseCommonUtil();
			long s = new Date().getTime();
			String s2 = String.valueOf(s);
			String ss = usc.getStringRandom(5);
			String s3 = s2 + ss;
			equipmentorder.setOrderid(s3);
			equipmentOrderService.save(equipmentorder);
			data.put("orderId", s3);
			data.put("price", String.valueOf(equipmentorder.getPrice()));
			String hname = hardwareinfo.get(0).getName();
			data.put("hardwareName", hname);
			return new ResponseEntity<LoginModel>(new LoginModel(true, "", data), new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<LoginModel>(new LoginModel(false, "操作异常" + e.getStackTrace()), new HttpHeaders(),
					HttpStatus.OK);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/showhardwareinfo", method = RequestMethod.POST)
	@ApiOperation(value = "展示设备地址信息", notes = "展示设备地址信息")
	@ResponseBody
	public ResponseEntity<LoginModel> showhardwareinfo(@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "token", required = true) String token, HttpServletRequest request) {
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> ParamMap = new HashMap<String, Object>();
		try {
			ParamMap.put("userId", userId);
			List<Userbaseinfo> userBaseInfo2 = userbaseinfoMapper.selectUserbaseinfoByUserId(ParamMap);
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
			List<Useraddress> useraddress = useraddressMapper.selectUserAddressByUserId(ParamMap);
			if (useraddress.isEmpty()) {
				return new ResponseEntity<LoginModel>(new LoginModel(false, "请先填写收货地址信息！"), new HttpHeaders(),
						HttpStatus.OK);
			}
			List<Hardwareinfo> hardwareinfo = hardwareinfoMapper.selectHardwareinfoByStsatus();
			if (hardwareinfo.isEmpty()) {
				return new ResponseEntity<LoginModel>(new LoginModel(false, "设备已全部启用，无法再分配设备！"), new HttpHeaders(),
						HttpStatus.OK);
			}
			String mobile = useraddress.get(0).getContactnumber();
			
			String address = useraddress.get(0).getShippingaddress();
			String addressId = useraddress.get(0).getAddressid();
			String hardwareId = hardwareinfo.get(0).getHardwareid();
			String name = useraddress.get(0).getUsername();
			String hardwareName = hardwareinfo.get(0).getName();
			// List<Equipmentorder> equipmentorder = equipmentOrderMapper.selectAll();
			// BigDecimal price= equipmentorder.get(0).getPrice();
			// BigDecimal price = new BigDecimal(3000);
			// 生成价格
			List<Hardwareinfo> Hardwareinfo2 = hardwareinfoMapper.selectAll();
			BigDecimal price = Hardwareinfo2.get(0).getHardwareprice();
			List<Telecommunication> telecommunication2 = telecommunicationMapper.selectAll();
			BigDecimal price2 = telecommunication2.get(0).getTelecommunicationprice();
			data.put("price", price.add(price2));
			data.put("hardwareName", hardwareName);
			data.put("userName", name);
			data.put("address", address);
			data.put("addressId", addressId);
			data.put("hardwareId", hardwareId);
			data.put("mobile", mobile);

			// String basepath = request.getScheme()+"://"+request.getServerName()+":"+
			// request.getServerPort()+"/upload/1.jpg";

			//CustomSystemUtil cs = new CustomSystemUtil();
			//String addr = cs.getMyIP();
			String basepath = request.getScheme() + "://" + "116.62.240.253" + ":" + "8888" + "/upload/1.jpg";
			String avatar = basepath;
			data.put("avatar", avatar);
			return new ResponseEntity<LoginModel>(new LoginModel(true, "", data), new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<LoginModel>(new LoginModel(false, "操作异常" + e.getStackTrace()), new HttpHeaders(),
					HttpStatus.OK);
		}
	}

	/*
	 * 1.未付款 2.已付款 3.已退款 4.已取消 99.作废
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/displayOrder", method = RequestMethod.POST)
	@ApiOperation(value = "显示订单", notes = "显示订单")
	@ResponseBody
	public ResponseEntity<LoginModel> displayOrder(@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "token", required = true) String token,
			@RequestParam(value = "ordertype", required = true) String ordertype,
			@RequestParam(value = "currentPage", required = true) String courrentPage, HttpServletRequest request) {
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> ParamMap = new HashMap<String, Object>();
		try {
			ParamMap.put("userId", userId);
			List<Userbaseinfo> userBaseInfo2 = userbaseinfoMapper.selectUserbaseinfoByUserId(ParamMap);
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
			ParamMap.put("orderstatus", ordertype);
			int count = equipmentOrderMapper.selectCountOrder(ParamMap);
			// int size = count/10;
			int currentPage2 = Integer.parseInt(courrentPage);
			// 如果整除表示正好分N页，如果不能整除在N页的基础上+1页
			int totalPages = count % 10 == 0 ? count / 10 : (count / 10) + 1;
			String totalPage = String.valueOf(totalPages);
			// 总页数
			// this.last = totalPages;

			// 判断当前页是否越界,如果越界，我们就查最后一页
			if (currentPage2 > totalPages) {
				currentPage2 = totalPages;
				data.put("equipmentorder", "[]");
				data.put("totalPages", totalPage);
				return new ResponseEntity<LoginModel>(new LoginModel(true, "", data), new HttpHeaders(), HttpStatus.OK);
			} else {
				// currentPage2=currentPage2;
			}
			if (currentPage2 <= 0) {
				currentPage2 = 1;
			}

			// 计算start 1----0 2 ------ 5
			int start = (currentPage2 - 1) * 10;
			ParamMap.put("startRow", start);
			ParamMap.put("pageSize", currentPage2 * 10);
			String basepath = request.getScheme() + "://" + "116.62.240.253" + ":" + "8888" + "/upload/1.jpg";
			String avatar = basepath;
			List<Equipmentorder> equipmentorder = equipmentOrderMapper.selectEquipmentOrder(ParamMap);
			List list = new ArrayList();
			for (int i = 0; i < equipmentorder.size(); i++) {
				String hardwareid = equipmentorder.get(i).getHardwareid();
				Hardwareinfo hardware = hardwareinfoMapper.selectByPrimaryKey(hardwareid);
				String name = hardware.getName();
				String addressid = equipmentorder.get(i).getAddressid();
				Useraddress useraddress = useraddressMapper.selectByPrimaryKey(addressid);
				String addressName = useraddress.getShippingaddress();
				String userName = useraddress.getUsername();
				BigDecimal price = equipmentorder.get(i).getPrice();
				String price2 = String.valueOf(price);
				Map<String, Object> m1 = new HashMap<String, Object>();
				
				m1.put("userName", userName);
				m1.put("orderId", equipmentorder.get(i).getOrderid());
				m1.put("addressId", equipmentorder.get(i).getAddressid());
				m1.put("price", price2);
				m1.put("hardwareName", name);
				m1.put("avatar", avatar);
				m1.put("totalPages", totalPage);
				m1.put("payType", equipmentorder.get(i).getTransactiontype());// 1.支付宝 2.微信
				m1.put("addressName", addressName);
				m1.put("orderTime", equipmentorder.get(i).getCreationtime());
				m1.put("orderType", equipmentorder.get(i).getOrderstatus());
				//int orderStatus = equipmentorder.get(i).getOrderstatus();
				
					/*
					 * Date starttime = equipmentorder.get(i).getCreationtime(); String starttime2 =
					 * DateUtil.format(starttime); Date endtime = new Date(); SimpleDateFormat sdf =
					 * new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); Calendar cal =
					 * Calendar.getInstance(); cal.setTime(sdf.parse(starttime2));
					 * cal.add(Calendar.HOUR_OF_DAY, 4); Date starttime3 = cal.getTime(); String
					 * sdate = DateUtil.format(starttime3); String edate = DateUtil.format(endtime);
					 * int s = DateUtil.comparedate(sdate, edate); if(s==-1) {//4小时未付款订单状态取消
					 * Equipmentorder equipmentorderf = equipmentorder.get(i);
					 * equipmentorderf.setOrder_status(4);
					 * equipmentOrderMapper.updateByPrimaryKey(equipmentorderf); }
					 */
				
				list.add(m1);
			}
			data.put("list", list);
			return new ResponseEntity<LoginModel>(new LoginModel(true, "", data), new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<LoginModel>(new LoginModel(false, "操作异常" + e.getStackTrace()), new HttpHeaders(),
					HttpStatus.OK);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
	@ApiOperation(value = "取消订单", notes = "取消订单")
	@ResponseBody
	public ResponseEntity<LoginModel> cancelOrder(@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "token", required = true) String token,
			@RequestParam(value = "orderId", required = true) String orderId, HttpServletRequest request) {
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> ParamMap = new HashMap<String, Object>();
		try {
			ParamMap.put("userId", userId);
			List<Userbaseinfo> userBaseInfo2 = userbaseinfoMapper.selectUserbaseinfoByUserId(ParamMap);
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
			Equipmentorder equipmentorder = equipmentOrderMapper.selectByPrimaryKey(orderId);
			equipmentorder.setOrderstatus(4);
			equipmentOrderMapper.updateByPrimaryKey(equipmentorder);
			String hardwareid = equipmentorder.getHardwareid();
			Hardwareinfo hardware = hardwareinfoMapper.selectByPrimaryKey(hardwareid);
			Hardwareinfo hardwareinfo = new Hardwareinfo();
			hardwareinfo.setCardnumber(hardware.getCardnumber());
			hardwareinfo.setCode(hardware.getCode());
			hardwareinfo.setCreationtime(hardware.getCreationtime());
			hardwareinfo.setCreationuserid(hardware.getCreationuserid());
			hardwareinfo.setCreationusername(hardware.getCreationusername());
			hardwareinfo.setHardwareprice(hardware.getHardwareprice());
			hardwareinfo.setCurrentpassword(hardware.getCurrentpassword());
			hardwareinfo.setImei(hardware.getImei());
			hardwareinfo.setInitialpassword(hardware.getInitialpassword());
			hardwareinfo.setMac(hardware.getMac());
			hardwareinfo.setUserid(hardware.getUserid());
			hardwareinfo.setName(hardware.getName());
			hardwareinfo.setModifyusername(hardware.getModifyusername());
			hardwareinfo.setModifytime(new Date());
			hardwareinfo.setModifyuserid(hardware.getModifyuserid());
			hardwareinfo.setHardwareid(hardwareid);
			hardwareinfo.setStatus(1);
			hardwareinfoMapper.updateByPrimaryKeyHardwareinfo(hardwareinfo);
			return new ResponseEntity<LoginModel>(new LoginModel(true, "", data), new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {

			e.printStackTrace();
			return new ResponseEntity<LoginModel>(new LoginModel(false, "操作异常" + e.getStackTrace()), new HttpHeaders(),
					HttpStatus.OK);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/delOrder", method = RequestMethod.POST)
	@ApiOperation(value = "删除订单", notes = "删除订单")
	@ResponseBody
	public ResponseEntity<LoginModel> delOrder(@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "token", required = true) String token,
			@RequestParam(value = "orderId", required = true) String orderId, HttpServletRequest request) {
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> ParamMap = new HashMap<String, Object>();
		try {
			ParamMap.put("userId", userId);
			List<Userbaseinfo> userBaseInfo2 = userbaseinfoMapper.selectUserbaseinfoByUserId(ParamMap);
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
			Equipmentorder equipmentorder = equipmentOrderMapper.selectByPrimaryKey(orderId);
			equipmentorder.setOrderstatus(99);
			equipmentOrderMapper.updateByPrimaryKey(equipmentorder);
			return new ResponseEntity<LoginModel>(new LoginModel(true, "", data), new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {

			e.printStackTrace();
			return new ResponseEntity<LoginModel>(new LoginModel(false, "操作异常" + e.getStackTrace()), new HttpHeaders(),
					HttpStatus.OK);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/refundOrder", method = RequestMethod.POST)
	@ApiOperation(value = "退款", notes = "退款")
	@ResponseBody
	public ResponseEntity<LoginModel> refundOrder(@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "token", required = true) String token,
			@RequestParam(value = "orderId", required = true) String orderId, HttpServletRequest request) {
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> ParamMap = new HashMap<String, Object>();
		try {
			ParamMap.put("userId", userId);
			List<Userbaseinfo> userBaseInfo2 = userbaseinfoMapper.selectUserbaseinfoByUserId(ParamMap);
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
			Equipmentorder equipmentorder = equipmentOrderMapper.selectByPrimaryKey(orderId);
			Equipmentorder equipmentorder2 = new Equipmentorder();
			equipmentorder2.setOrderid(orderId);
			equipmentorder2.setAddressid(equipmentorder.getAddressid());
			equipmentorder2.setCreationtime(equipmentorder.getCreationtime());
			equipmentorder2.setCreationuserid(equipmentorder.getCreationuserid());
			equipmentorder2.setCreationusername(equipmentorder.getCreationusername());
			equipmentorder2.setHardwareid(equipmentorder.getHardwareid());
			equipmentorder2.setModifyuserid(equipmentorder.getModifyuserid());
			equipmentorder2.setPrice(equipmentorder.getPrice());
			equipmentorder2.setModifyusername(equipmentorder.getModifyusername());
			equipmentorder2.setStatus(equipmentorder.getStatus());
			equipmentorder2.setTransactionnumber(equipmentorder.getTransactionnumber());
			equipmentorder2.setTransactiontype(equipmentorder.getTransactiontype());
			equipmentorder2.setUserid(equipmentorder.getUserid());
			equipmentorder2.setOrderstatus(5);
			equipmentorder2.setModifytime2(new Date());
			equipmentOrderMapper.updateByPrimaryKeyEquipmentorder(equipmentorder2);
			return new ResponseEntity<LoginModel>(new LoginModel(true, "", data), new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {

			e.printStackTrace();
			return new ResponseEntity<LoginModel>(new LoginModel(false, "操作异常" + e.getStackTrace()), new HttpHeaders(),
					HttpStatus.OK);
		}
	}

}

package com.worksyun.api.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.worksyun.api.mapper.EquipmentorderMapper;
import com.worksyun.api.mapper.HardwareinfoMapper;
import com.worksyun.api.mapper.UserbaseinfoMapper;
import com.worksyun.api.model.Equipmentorder;
import com.worksyun.api.model.Hardwareinfo;
import com.worksyun.api.model.Userbaseinfo;
import com.worksyun.commons.model.LoginModel;
import com.worksyun.commons.wxutils.ConfigUtil;
import com.worksyun.commons.wxutils.PayCommonUtil;
import com.worksyun.commons.wxutils.XMLUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController("/WxPay")
@Api(description = "微信相关操作")
public class WxPayController {

	@Autowired
	private EquipmentorderMapper equipmentOrderMapper;
	
	@Autowired
	private UserbaseinfoMapper userbaseinfoMapper;
	
	@Autowired
	private HardwareinfoMapper hardwareinfoMapper;

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/wxPrePay")
	@ApiOperation(value = "微信加签操作", notes = "微信加签操作")
	public Map<String, Object> wxPrePay(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String orderId = request.getParameter("orderId");
		String price = request.getParameter("price");
		String hardwareName = request.getParameter("hardwareName");
		//price = "0.01";
		int price100 = new BigDecimal(price).multiply(new BigDecimal(100)).intValue();
		if (price100 <= 0) {
			resultMap.put("msg", "付款金额错误");
			resultMap.put("code", "500");
			return resultMap;
		}
		// 设置回调地址-获取当前的地址拼接回调地址
		// String url = request.getRequestURL().toString();
		// String domain = url.substring(0, url.length()-13);
		// 生产环境
		   String notify_url= "http://116.62.240.253:8888/wxNotify";
		   
		// 测试环境
		//String notify_url = "http://19420741zz.51mypc.cn/wxNotify";

		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		parameters.put("appid", ConfigUtil.APPID);
		parameters.put("mch_id", ConfigUtil.MCH_ID);
		parameters.put("nonce_str", PayCommonUtil.CreateNoncestr());
		parameters.put("body", hardwareName);
		parameters.put("out_trade_no", orderId); // 订单id
		parameters.put("fee_type", "CNY");
		parameters.put("total_fee", String.valueOf(price100));
		parameters.put("spbill_create_ip", request.getRemoteAddr());
		parameters.put("notify_url", notify_url);
		parameters.put("trade_type", "APP");
		// 设置签名
		String sign = PayCommonUtil.createSign("UTF-8", parameters);
		parameters.put("sign", sign);
		// 封装请求参数结束
		String requestXML = PayCommonUtil.getRequestXml(parameters);
		// 调用统一下单接口
		String result = PayCommonUtil.httpsRequest(ConfigUtil.UNIFIED_ORDER_URL, "POST", requestXML);
		//System.out.println("\n" + result);
		try {
			/**
			 * 统一下单接口返回正常的prepay_id，再按签名规范重新生成签名后，将数据传输给APP。参与签名的字段名为appId，partnerId，prepayId，nonceStr，timeStamp，package。注意：package的值格式为Sign=WXPay
			 **/
			Map<String, String> map = XMLUtil.doXMLParse(result);
			SortedMap<Object, Object> parameterMap2 = new TreeMap<Object, Object>();
			parameterMap2.put("appid", ConfigUtil.APPID);
			parameterMap2.put("partnerid", ConfigUtil.MCH_ID);
			parameterMap2.put("prepayid", map.get("prepay_id"));
			parameterMap2.put("package", "Sign=WXPay");
			parameterMap2.put("noncestr", map.get("nonce_str"));
			// 本来生成的时间戳是13位，但是ios必须是10位，所以截取了一下
			parameterMap2.put("timestamp", String.valueOf(System.currentTimeMillis()).toString().substring(0, 10));
			String sign2 = PayCommonUtil.createSign("UTF-8", parameterMap2);
			parameterMap2.put("sign", sign2);
			resultMap.put("code", "200");
			resultMap.put("data", parameterMap2);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	/**
	 * 微信异步通知
	 * 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@PostMapping(value = "/wxNotify")
	@ApiOperation(value = "微信订单异步验证", notes = "微信订单异步验证")
	public String wxNotify(HttpServletRequest request, HttpServletResponse response) throws IOException, JDOMException {
		// 读取参数
		InputStream inputStream;
		StringBuffer sb = new StringBuffer();
		inputStream = request.getInputStream();
		String s;
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		while ((s = in.readLine()) != null) {
			sb.append(s);
		}
		in.close();
		inputStream.close();
		// 解析xml成map
		Map<String, String> m = new HashMap<String, String>();
		m = XMLUtil.doXMLParse(sb.toString());
		for (Object keyValue : m.keySet()) {
			System.out.println(keyValue + "=" + m.get(keyValue));
		}
		// 过滤空 设置 TreeMap
		SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
		Iterator it = m.keySet().iterator();
		while (it.hasNext()) {
			String parameter = (String) it.next();
			String parameterValue = m.get(parameter);

			String v = "";
			if (null != parameterValue) {
				v = parameterValue.trim();
			}
			packageParams.put(parameter, v);
		}

		// 判断签名是否正确
		String resXml = "";
		if (PayCommonUtil.isTenpaySign("UTF-8", packageParams)) {
			if ("SUCCESS".equals((String) packageParams.get("result_code"))) {

				System.out.println("微信异步请求-------------------");
				String out_trade_no = m.get("out_trade_no");
				// 修改订单状态
				Equipmentorder equipmentorder = equipmentOrderMapper.selectByPrimaryKey(out_trade_no);
				String transaction_number = (String) m.get("transaction_id");
				equipmentorder.setTransactionnumber(transaction_number);
				equipmentorder.setTransactiontype(2);
				equipmentorder.setOrderstatus(2);
				equipmentOrderMapper.updateByPrimaryKey(equipmentorder);
				String userid = equipmentorder.getUserid();
				Userbaseinfo use = userbaseinfoMapper.selectByPrimaryKey(userid);
				use.setUsertype(1);
				userbaseinfoMapper.updateByPrimaryKey(use);
				System.out.println("改变微信订单状态"+",时间："+new Date());
				// 已分配设备未激活
				String hardwareId = equipmentorder.getHardwareid();
				Hardwareinfo hardwareinfo = hardwareinfoMapper.selectByPrimaryKey(hardwareId);
				if (hardwareinfo != null) {
					hardwareinfo.setStatus(2);
					hardwareinfoMapper.updateByPrimaryKey(hardwareinfo);
				}
			}
		}
		//System.out.println(packageParams);
		return "success";
	}

	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/wxPayRefund")
	@ApiOperation(value = "微信退款操作", notes = "微信退款操作")
	public ResponseEntity<LoginModel> wxPayRefund(HttpServletRequest request,
	 @RequestParam(value = "orderId", required = true) String orderId,
	 @RequestParam(value = "refundAmount", required = true) String refund_Amount
	) {
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			//String orderId = request.getParameter("orderId");
			//String refund_Amount = request.getParameter("refundAmount");
			SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
			packageParams.put("appid", ConfigUtil.APPID);// 应用id
			packageParams.put("mch_id", ConfigUtil.MCH_ID);// 商户号
			packageParams.put("nonce_str", PayCommonUtil.CreateNoncestr());// 随机字符串
			packageParams.put("out_trade_no", orderId);// 订单号
			Equipmentorder equipmentorder2 = equipmentOrderMapper.selectByPrimaryKey(orderId);
			String out_refund_no = equipmentorder2.getTransactionnumber();
			packageParams.put("out_refund_no", out_refund_no);// 退款单号
			packageParams.put("total_fee", refund_Amount);// 订单总金额Utils.getMoney()
			packageParams.put("refund_fee", refund_Amount);// 退款总金额
			packageParams.put("op_user_id", ConfigUtil.MCH_ID);// 商户号

			String sign = PayCommonUtil.createSign("UTF-8", packageParams);
			packageParams.put("sign", sign);
			String result = "FAIL";
			String msg = "";
			// logger.debug("--sign--="+sign);

			String createOrderURL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
			String xml = null;
			xml = PayCommonUtil.getRequestXml(packageParams);
			// logger.debug("--xml-="+xml);
			String retur = null;
			try {
				retur = PayCommonUtil.doRefund(createOrderURL, xml);

				//System.out.print(retur);
				System.out.println("微信订单退款时间："+new Date());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (!StringUtils.isEmpty(retur)) {
				Map map = PayCommonUtil.parseXmlToMap(retur);

				String returnCode = (String) map.get("return_code");
				if (returnCode.equals("SUCCESS")) {
					result = "SUCCESS";
					msg = "OK";
					int status = -1;
					String resultCode = (String) map.get("result_code");
					if (resultCode.equals("SUCCESS")) {
						status = 1;
					}
					if (status == 1) {
						String outtradeno = (String) map.get("out_trade_no"); // 订单号
						Equipmentorder equipmentorder = equipmentOrderMapper.selectByPrimaryKey(outtradeno);
						equipmentorder.setOrderstatus(3);
						equipmentorder.setModifytime2(new Date());
						equipmentOrderMapper.updateByPrimaryKey(equipmentorder);
						
						String userid = equipmentorder.getUserid();
						Userbaseinfo use = userbaseinfoMapper.selectByPrimaryKey(userid);
						use.setUsertype(2);
						userbaseinfoMapper.updateByPrimaryKey(use);
						
						  //未激活设备变为新建
						  String hardwareId = equipmentorder.getHardwareid();
						  Hardwareinfo  hardwareinfo= hardwareinfoMapper.selectByPrimaryKey(hardwareId);
						  if(hardwareinfo!=null) {
							  hardwareinfo.setStatus(1);
						      hardwareinfoMapper.updateByPrimaryKey(hardwareinfo);
						      }
						// 业务操作
					}
				}
				if (result.equals("FAIL")) {
					msg = (String) map.get("return_msg");
					// logger.info(" 微信退款失败 refundfail msg="+msg);
				}

			}
			// return msg;
			data.put("msg", msg);
			return new ResponseEntity<LoginModel>(new LoginModel(true, "", data), new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<LoginModel>(new LoginModel(false, "操作异常" + e.getStackTrace()), new HttpHeaders(),
					HttpStatus.OK);
		}
	}

}

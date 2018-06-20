package com.worksyun.api.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.worksyun.api.mapper.EquipmentorderMapper;
import com.worksyun.api.mapper.HardwareinfoMapper;
import com.worksyun.api.mapper.UserbaseinfoMapper;
import com.worksyun.api.model.Equipmentorder;
import com.worksyun.api.model.Hardwareinfo;
import com.worksyun.api.model.Userbaseinfo;
import com.worksyun.commons.model.AlipayRefundInfo;
import com.worksyun.commons.model.LoginModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController("/Alipay")
@Api(description = "支付宝相关操作")
public class AlipayController {

	@Autowired
	private EquipmentorderMapper equipmentOrderMapper;
	
	@Autowired
	private UserbaseinfoMapper userbaseinfoMapper;
	
	@Autowired
	private HardwareinfoMapper hardwareinfoMapper;


	private static final String alipaypublickey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAh7mXmm7p2jsoIJM"
			+ "UAZln82r73prPi8NH0Pbm93Md+mQAPnpVSYX1j+D1tbu3aGWqOUdqX+7+gXVJR8SG9G3q652SfKwZq91KXzEnmKqyZejSW"
			+ "LBo29CVzgEwbJSlp+Yg4MpkGg2tCrfRmNEj6jmkxVFF2n920BwaKlXCVTVNLQ/0jN+9Mh2czTR4KqD/4arpYVdjL1W0AKf"
			+ "9J8qASG578xm56M8gJSuKXrgvWOVpg40ZJSJFXbwubjSNG6cwrDP5tpokIBrNaTVf2qYeNQkugtlL5qMUP5+RkGhXl5uzA"
			+ "AkcsBKHsCv2rlvj6ZPobmt+zdyGUFJDE/Fw2mnfvRCMtQIDAQAB";

	private static final String appprivatekey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC4"
			+ "Xp2aPNCyp1FGkyFAdIZYONQUKsj/+drXPjIFWu9XykxhGZxNfYQoMBRCVhCTivTzBJVgxixSmd0uYODU21SYBJ"
			+ "I2LK4yGe3rwdFz9BEkyD/Sf39XPBZFsXK8R3JVYbEm93JSZC/O4VRrNUCoFiTiWvbfM66/PTPjCUtlYcomOCjB4"
			+ "Re6b6+ZkXAxx3+omE46dsqqs4QY2DL1ybmGFiaEL5zQzF+hgPKk/y3PynkIhhLhfDYu7/Cbdc8vlLw0eCXcgmURS"
			+ "fOEvrli1sIEpC0tWDdw+FZ/f5+hSI/XFOxIX9BGgp1oRIJ14h3fwfnuzNOM3HxyE7fqTgpfFTu/WEvjAgMBAAECggE"
			+ "ASGjIhwn5gSDbd9dTCOh61u1CxcTj+jyb6UtBA/9p3tXR15TiYUC5hbsUSdVZX0kE2Ojk7gQG5k4NoyhjRNX7TFUHQ4"
			+ "WayIyMd7pAU8T4crS+9CIk1hMAQFDn2fBJhCUBGWe3m4pDiIHQ0OYyRZGk3M2TBahbqjoURceht+iHJyjdTuDyCjTXZ"
			+ "DOpWHs+8ZShw23C9bgBe5tu4ySQt8HxdxV5nY6Nk9rCXyow47JXSfV7KBV5usO+YtV0eloGCvaZ2YZoWAEgSzJOk8RJTo"
			+ "XxuE1FNUIpk1eVNP1KKbyBBjHKtt7lZoAtAFGfACu0PAouvrvcERsd4F7FmaBvLZMOEQKBgQD1uapYHJDg0SODcN0VOPU"
			+ "nmwV8ckwpJmcyNUmp54Ug/6KUx7r0yzBks1uHHdTg1td/MB6Gx3t/vGmFLYYLEHTdZYnzZ/YNYb0S2lc8ICBNF5edjffm"
			+ "C/hwSbytziS5mNwcghzXOmATF2BY5X8rc+4JpMZx7jBxP7Cwreu5lb3s+QKBgQDAFC0mw91/tjpKKjpbgkU3q26n8sWr"
			+ "z9SHESC/+oVF6vcl8VXQinaQ2VDSAtaA4zrvBXP8Z1SujPSQljU9HF4ub08760t7UEPDiPT+lEnJzbGWaQpMIiiF/umm"
			+ "/DsIt2xhOAD1dWX3eac+JteD25Iv0Gia0m30B8ChDPQSD6RCuwKBgFmbckYdbKu/OH8nOkgR9DyUKIUAPUvISFuj2RAPc"
			+ "Jne/w/YYhIAws4GJft44nd3gTBNgORPfHbRY32AY2BHiQZvj8XmhHDO0p88ZhFz+bODRfPboKoQdXGlQhvzj6rq1mHDBP"
			+ "drvztXC/h/AEf9R7HWl9NWM+4odquHUO3lcFQpAoGAStSRcpTD6klYFCS3b4ck6DdFePC/ydvB9IzbsW8sFL0hZArNX91R"
			+ "q8S+E9M/Nh9QimYZda/b8dTAQsQCIbi3iGec+r8EPRpcDAWDTNsAlDoqgWqStG5qUymoZYHGjfLHqmhjFz2RaARg/3mqZ"
			+ "JhaL36GAisQ4w1SImwlehPV7pkCgYEAyV7InIh80Vq27AePx2PJwckTAvA6NcQK1XdgsKboXfEq8VcMgSFhgnhdeof8XuC"
			+ "ETCqUs+3RdWrCSz1EfWtwJ4gQYwWKfxohAUrhDb8guvXsvIDjkfSH7RfxqryZ8BJbBaikDutRBpHUpGvhU40Sg7WIu6m2"
			+ "65tin6wy3T3FxVM=";

	@PostMapping("/alipayOrder")
	@ApiOperation(value = "支付宝订单加签", notes = "支付宝订单加签")
	public ResponseEntity<LoginModel> testAlipay(HttpServletRequest hrequest) throws IOException {
		Map<String, Object> data = new HashMap<String, Object>();
		String orderId = hrequest.getParameter("orderId");
		String price = hrequest.getParameter("price");
		String hardwareName = hrequest.getParameter("hardwareName");
		String orderstring = "";
		// 实例化客户端
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", "2017120600401804",
				appprivatekey, "json", "utf-8", alipaypublickey, "RSA2");
		// 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
		// SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setBody("我是测试数据");
		model.setSubject(hardwareName);
		model.setOutTradeNo(orderId);
		model.setTimeoutExpress("30m");
		price = String.valueOf(price);
		model.setTotalAmount(price);
		model.setProductCode("QUICK_MSECURITY_PAY");
		request.setBizModel(model);
		//生产环境
		request.setNotifyUrl("http://116.62.240.253:8888/alipayNotify");
		//测试环境
		//request.setNotifyUrl("http://19420741zz.51mypc.cn/alipayNotify");
		try {
			// 这里和普通的接口调用不同，使用的是sdkExecute
			AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
			// System.out.println(response.getBody());//就是orderString
			// 可以直接给客户端请求，无需再做处理。
			orderstring = response.getBody();
			data.put("orderstring", orderstring);
		} catch (AlipayApiException e) {
			System.out.println(e.getErrMsg());
			Map<String, Object> data2 = new HashMap<String, Object>();
			return new ResponseEntity<LoginModel>(new LoginModel(false, "操作异常！" + e.getStackTrace(), data2),
					new HttpHeaders(), HttpStatus.OK);
		}
		return new ResponseEntity<LoginModel>(new LoginModel(true, "", data), new HttpHeaders(), HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	@PostMapping(value = "/alipayNotify")
	@ApiOperation(value = "支付宝订单异步验证", notes = "支付宝订单异步验证")
	public String alipayNotify(HttpServletRequest request) {
		String message = "fail";
		// 获取支付宝POST过来反馈信息
		//System.out.println("collecting signature data...");

		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		String logstr = "";
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
			logstr = logstr + "[" + name + "=" + valueStr + "]";
		}
		//System.out.println("parsing data:[" + logstr + "]");
		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		String out_trade_no = "";
		// 商户订单号
		String trade_no = "";
		try {
			out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
			trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String price = request.getParameter("total_amount");
		double price2 = Double.parseDouble(price);
		String gmtpayment = request.getParameter("gmt_payment");
		String signType = params.get("sign_type");
		// Date paydate = StrToDate(gmtpayment);
		//System.out.println("out_trade_no=" + out_trade_no);
		//System.out.println("trade_no=" + trade_no);
		// 支付宝交易号
		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		boolean signVerified = false;
		try {
			signVerified = AlipaySignature.rsaCheckV1(params, alipaypublickey, "utf-8", signType);
		} catch (AlipayApiException e1) {
			e1.printStackTrace();
		}
		//System.out.println(signVerified);
		if (signVerified) {// 验证成功
			try {
				//System.out.println("Signature verify OK!");
				// 交易状态
				String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
				//System.out.println("trade_status=" + trade_status);
				//////////////////////////////////////////////////////////////////////////////////////////
				// 请在这里加上商户的业务逻辑程序代码

				// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

				if (trade_status.equals("TRADE_FINISHED")) {
					// 判断该笔订单是否在商户网站中已经做过处理
					// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					// 如果有做过处理，不执行商户的业务程序
					System.out.println("TRADE_FINISHED!");

					// 注意：
					// 退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
					// 请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
				} else if (trade_status.equals("TRADE_SUCCESS")) {
					System.out.println("支付宝异步请求----------------------------");
					// 修改订单状态
					Equipmentorder equipmentorder = equipmentOrderMapper.selectByPrimaryKey(out_trade_no);
					equipmentorder.setOrderstatus(2);
					equipmentorder.setTransactionnumber(trade_no);
					equipmentorder.setTransactiontype(1);
					equipmentOrderMapper.updateByPrimaryKey(equipmentorder);
					String userid = equipmentorder.getUserid();
					Userbaseinfo use = userbaseinfoMapper.selectByPrimaryKey(userid);
					use.setUsertype(1);
					userbaseinfoMapper.updateByPrimaryKey(use);

					System.out.println("改变订单状态时间："+new Date());
					
					String hardwareId = equipmentorder.getHardwareid();
					// 已分配设备未激活
					Hardwareinfo hardwareinfo = hardwareinfoMapper.selectByPrimaryKey(hardwareId);
					if (hardwareinfo != null) {
						hardwareinfo.setStatus(2);
						hardwareinfoMapper.updateByPrimaryKey(hardwareinfo);
					}

					System.out.println("TRADE_SUCCESS!");

					// 注意：
					// 付款完成后，支付宝系统发送该交易状态通知
					// 请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
				}

				// ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

				message = "success"; // "success"
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				message = "fail"; //
			}

			//////////////////////////////////////////////////////////////////////////////////////////
		} else {// 验证失败
			System.out.println("Signature verify Fail!");
			message = "fail"; // "fail"
		}
		message = "success"; // "fail"
		return message;
	}

	@GetMapping("/alipayRefund")
	@ApiOperation(value = "支付宝退款", notes = "支付宝退款")
	public ResponseEntity<LoginModel> alipayRefundRequest(HttpServletRequest hrequest,
	 @RequestParam(value = "orderId", required = true) String out_trade_no,
	 @RequestParam(value = "refundAmount", required = true) String refund_amount
	) throws IOException {
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			//String out_trade_no = hrequest.getParameter("orderId");
			//String refund_amount = hrequest.getParameter("refundAmount");
			// 发送请求
			String strResponse = null;
			// String out_trade_no = hrequest.getParameter("out_trade_no");
			// String refund_amount = hrequest.getParameter("refund_amount");
			// String trade_no = hrequest.getParameter("trade_no");
			try {
				// 实例化客户端
				AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
						"2017120600401804", appprivatekey, "json", "utf-8", alipaypublickey, "RSA2");
				AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
				AlipayRefundInfo alidata = new AlipayRefundInfo();
				alidata.setOut_trade_no(out_trade_no);
				Equipmentorder equipmentorder2 = equipmentOrderMapper.selectByPrimaryKey(out_trade_no);
				String trade_no = equipmentorder2.getTransactionnumber();
				alidata.setRefund_amount(refund_amount);
				alidata.setTrade_no(trade_no);
				request.setBizContent(JSON.toJSONString(alidata));// JsonUtils.convertToString(alidata));
				AlipayTradeRefundResponse response = alipayClient.execute(request);
				strResponse = response.getCode();
				if ("10000".equals(response.getCode())) {
					strResponse = "退款成功";
					
					Equipmentorder equipmentorder = equipmentOrderMapper.selectByPrimaryKey(out_trade_no);
					equipmentorder.setOrderstatus(3);
					equipmentorder.setModifytime2(new Date());
					equipmentOrderMapper.updateByPrimaryKey(equipmentorder);
					
					String userid = equipmentorder.getUserid();
					Userbaseinfo use = userbaseinfoMapper.selectByPrimaryKey(userid);
					use.setUsertype(2);
					userbaseinfoMapper.updateByPrimaryKey(use);
					
					System.out.println("支付宝退款时间："+new Date());
					
					 //未激活设备变为新建
					  String hardwareId = equipmentorder.getHardwareid();
					  Hardwareinfo  hardwareinfo= hardwareinfoMapper.selectByPrimaryKey(hardwareId);
					      if(hardwareinfo!=null) { 
					    	  hardwareinfo.setStatus(1);
					          hardwareinfoMapper.updateByPrimaryKey(hardwareinfo); 
					          }
					 
				} else {
					strResponse = response.getSubMsg();
				}
			} catch (Exception e) {
				e.getStackTrace();
			}
			// return strResponse;
			data.put("msg", strResponse);
			return new ResponseEntity<LoginModel>(new LoginModel(true, "", data), new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<LoginModel>(new LoginModel(false, "操作异常" + e.getStackTrace()), new HttpHeaders(),
					HttpStatus.OK);
		}
	}
}

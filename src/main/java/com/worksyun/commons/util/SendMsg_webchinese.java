package com.worksyun.commons.util;

import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class SendMsg_webchinese {

	/*public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://www.qf106.com/sms.aspx?action=send"); //该第三方短信服务地址
		post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");//在头文件中设置转码
		NameValuePair[] data ={ new NameValuePair("userid", "15639"),new NameValuePair("account", "dd1705"),new NameValuePair("password","123456"),new NameValuePair("mobile","15921993059"),new NameValuePair("content","您的验证码是:8888,感谢您的使用!")};
		post.setRequestBody(data);

		client.executeMethod(post);
		Header[] headers = post.getResponseHeaders();
		int statusCode = post.getStatusCode();
		System.out.println("statusCode:"+statusCode);
		for(Header h : headers)
		{
		System.out.println(h.toString());
		}
		String result = new String(post.getResponseBodyAsString().getBytes("utf-8")); 
		System.out.println(result); //打印返回消息状态


		post.releaseConnection();
	}*/

}

package com.worksyun.commons.wxutils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.security.KeyStore;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.alibaba.fastjson.JSONObject;

public class PayCommonUtil {
	 public static String CreateNoncestr(int length) {
	        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	        String res = "";
	        for (int i = 0; i < length; i++) {
	            Random rd = new Random();
	            res += chars.indexOf(rd.nextInt(chars.length() - 1));
	        }
	        return res;
	    }

	    public static String CreateNoncestr() {
	        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	        String res = "";
	        for (int i = 0; i < 32; i++) {
	            Random rd = new Random();
	            res += chars.charAt(rd.nextInt(chars.length() - 1));
	        }
	        return res;
	    }
	    
	    
	    /** 
	     * 是否签名正确,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。 
	     * @return boolean 
	     */  
	    public static boolean isTenpaySign(String characterEncoding, SortedMap<Object, Object> packageParams) {  
	        StringBuffer sb = new StringBuffer();  
	        Set es = packageParams.entrySet();  
	        Iterator it = es.iterator();  
	        while(it.hasNext()) {  
	            Map.Entry entry = (Map.Entry)it.next();  
	            String k = (String)entry.getKey();  
	            String v = (String)entry.getValue();  
	            if(!"sign".equals(k) && null != v && !"".equals(v)) {  
	                sb.append(k + "=" + v + "&");  
	            }  
	        }  

	        sb.append("key=" + ConfigUtil.API_KEY);  

	        //算出摘要  
	        String mysign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toLowerCase();  
	        String tenpaySign = ((String)packageParams.get("sign")).toLowerCase();  

	        //System.out.println(tenpaySign + "    " + mysign);  
	        return tenpaySign.equals(mysign);  
	    }  
	    
	    /**
	     * @Description：sign签名
	     * @param characterEncoding 编码格式
	     * @param parameters 请求参数
	     * @return
	     */
	    public static String createSign(String characterEncoding,SortedMap<Object,Object> parameters){
	        StringBuffer sb = new StringBuffer();
	        Set es = parameters.entrySet();
	        Iterator it = es.iterator();
	        while(it.hasNext()) {
	            Map.Entry entry = (Map.Entry)it.next();
	            String k = (String)entry.getKey();
	            Object v = entry.getValue();
	            if(null != v && !"".equals(v) 
	                    && !"sign".equals(k) && !"key".equals(k)) {
	                sb.append(k + "=" + v + "&");
	            }
	        }
	        sb.append("key="+ConfigUtil.API_KEY);
	        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
	        return sign;
	    }
	    
	    /**
	     * @Description：将请求参数转换为xml格式的string
	     * @param parameters  请求参数
	     * @return
	     */
	    @SuppressWarnings("rawtypes")
		public static String getRequestXml(SortedMap<Object, Object> parameters) {    
	        StringBuffer sb = new StringBuffer();    
	        sb.append("<xml>");    
	        Set<?> es = parameters.entrySet();    
	        Iterator<?> it = es.iterator();    
	        while (it.hasNext()) {    
	            Map.Entry entry = (Map.Entry) it.next();    
	            String k = (String) entry.getKey();    
	            String v = (String) entry.getValue();    
	            if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "sign".equalsIgnoreCase(k)) {    
	                sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");    
	            } else {    
	                sb.append("<" + k + ">" + v + "</" + k + ">");    
	            }    
	        }    
	        sb.append("</xml>");    
	        return sb.toString();    
	    }  
	    
	    /**
	     * @Description：返回给微信的参数
	     * @param return_code 返回编码
	     * @param return_msg  返回信息
	     * @return
	     */
	    public static String setXML(String return_code, String return_msg) {
	        return "<xml><return_code><![CDATA[" + return_code
	                + "]]></return_code><return_msg><![CDATA[" + return_msg
	                + "]]></return_msg></xml>";
	    }
	    
	    /**
	     * 发送https请求
	     * @param requestUrl 请求地址
	     * @param requestMethod 请求方式（GET、POST）
	     * @param outputStr 提交的数据
	     * @return 返回微信服务器响应的信息
	     */
	    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
	        try {
	            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
	            TrustManager[] tm = { new MyX509TrustManager() };
	            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
	            sslContext.init(null, tm, new java.security.SecureRandom());
	            // 从上述SSLContext对象中得到SSLSocketFactory对象
	            SSLSocketFactory ssf = sslContext.getSocketFactory();
	            URL url = new URL(requestUrl);
	            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
	            //conn.setSSLSocketFactory(ssf);
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            conn.setUseCaches(false);
	            // 设置请求方式（GET/POST）
	            conn.setRequestMethod(requestMethod);
	            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded"); 
	            // 当outputStr不为null时向输出流写数据
	            if (null != outputStr) {
	                OutputStream outputStream = conn.getOutputStream();
	                // 注意编码格式
	                outputStream.write(outputStr.getBytes("UTF-8"));
	                outputStream.close();
	            }
	            // 从输入流读取返回内容
	            InputStream inputStream = conn.getInputStream();
	            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
	            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	            String str = null;
	            StringBuffer buffer = new StringBuffer();
	            while ((str = bufferedReader.readLine()) != null) {
	                buffer.append(str);
	            }
	            // 释放资源
	            bufferedReader.close();
	            inputStreamReader.close();
	            inputStream.close();
	            inputStream = null;
	            conn.disconnect();
	            return buffer.toString();
	        } catch (ConnectException ce) {
//	          log.error("连接超时：{}", ce);
	        } catch (Exception e) {
//	          log.error("https请求异常：{}", e);
	        }
	        return null;
	    }
	    
	    
	    
	    /**
	     * 发送https请求
	     * 
	     * @param requestUrl 请求地址
	     * @param requestMethod 请求方式（GET、POST）
	     * @param outputStr 提交的数据
	     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	     */
	     public static JSONObject httpsRequest(String requestUrl, String requestMethod) {
	            JSONObject jsonObject = null;
	            try {
	                    // 创建SSLContext对象，并使用我们指定的信任管理器初始化
	                    TrustManager[] tm = { new MyX509TrustManager() };
	                    SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
	                    sslContext.init(null, tm, new java.security.SecureRandom());
	                    // 从上述SSLContext对象中得到SSLSocketFactory对象
	                    SSLSocketFactory ssf = sslContext.getSocketFactory();
	                    URL url = new URL(requestUrl);
	                    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
	                    //conn.setSSLSocketFactory(ssf);
	                    conn.setDoOutput(true);
	                    conn.setDoInput(true);
	                    conn.setUseCaches(false);
	                    conn.setConnectTimeout(3000);
	                    // 设置请求方式（GET/POST）
	                    conn.setRequestMethod(requestMethod);
	                    //conn.setRequestProperty("content-type", "application/x-www-form-urlencoded"); 
	                    // 当outputStr不为null时向输出流写数据
	                    // 从输入流读取返回内容
	                    InputStream inputStream = conn.getInputStream();
	                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
	                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	                    String str = null;
	                    StringBuffer buffer = new StringBuffer();
	                    while ((str = bufferedReader.readLine()) != null) {
	                            buffer.append(str);
	                    }
	                    // 释放资源
	                    bufferedReader.close();
	                    inputStreamReader.close();
	                    inputStream.close();
	                    inputStream = null;
	                    conn.disconnect();
	                    jsonObject = JSONObject.parseObject(buffer.toString());
	            } catch (ConnectException ce) {
//	                    log.error("连接超时：{}", ce);
	            } catch (Exception e) {
	                    System.out.println(e);
//	                    log.error("https请求异常：{}", e);
	            }
	            return jsonObject;
	}

	     
	     public static String urlEncodeUTF8(String source){
	         String result = source;
	         try {
	             result = java.net.URLEncoder.encode(source,"utf-8");
	         } catch (UnsupportedEncodingException e) {
	             e.printStackTrace();
	         }
	         return result;
	     }
	     
	     
	     public static String doRefund(String url, String data) throws Exception {
	         KeyStore keyStore = KeyStore.getInstance("PKCS12");
	         FileInputStream is = new FileInputStream(new File("/opt/worksyun/Files/apiclient_cert.p12"));
	         try {
	             keyStore.load(is, "1494662882".toCharArray());// 这里写密码..默认是你的MCHID
	         } finally {
	             is.close();
	         }

	 // Trust own CA and all self-signed certs
	         SSLContext sslcontext = SSLContexts.custom()
	                 .loadKeyMaterial(keyStore, "1494662882".toCharArray())// 这里也是写密码的
	                 .build();
	 // Allow TLSv1 protocol only
	         SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
	                 sslcontext, new String[] { "TLSv1" }, null,
	                 SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
	         CloseableHttpClient httpclient = HttpClients.custom()
	                 .setSSLSocketFactory(sslsf).build();
	         try {
	             HttpPost httpost = new HttpPost(url); // 设置响应头信息
	             httpost.addHeader("Connection", "keep-alive");
	             httpost.addHeader("Accept", "*/*");
	             httpost.addHeader("Content-Type",
	                     "application/x-www-form-urlencoded; charset=UTF-8");
	             httpost.addHeader("Host", "api.mch.weixin.qq.com");
	             httpost.addHeader("X-Requested-With", "XMLHttpRequest");
	             httpost.addHeader("Cache-Control", "max-age=0");
	             httpost.addHeader("User-Agent",
	                     "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
	             httpost.setEntity(new StringEntity(data, "UTF-8"));
	             CloseableHttpResponse response = httpclient.execute(httpost);
	             try {
	                 HttpEntity entity = response.getEntity();

	                 String jsonStr = EntityUtils.toString(response.getEntity(),
	                         "UTF-8");
	                 EntityUtils.consume(entity);
	                 return jsonStr;
	             } finally {
	                 response.close();
	             }
	         } finally {
	             httpclient.close();
	         }
	     }
	     
	     public static Map parseXmlToMap(String xml) {
	    	  //  Map retMap = new HashMap();
	    	    SortedMap<String, String> retMap = new TreeMap<String, String>();
	    	    try {
	    	        StringReader read = new StringReader(xml);
	    	        // 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
	    	        InputSource source = new InputSource(read);
	    	        // 创建一个新的SAXBuilder
	    	        SAXBuilder sb = new SAXBuilder();
	    	        // 通过输入源构造一个Document
	    	        Document doc =  sb.build(source);
	    	        Element root = (Element) doc.getRootElement();// 指向根节点
	    	        List<Element> es = root.getChildren();
	    	        if (es != null && es.size() != 0) {
	    	            for (Element element : es) {
	    	                retMap.put(element.getName(), element.getValue());
	    	            }
	    	        }
	    	    } catch (Exception e) {
	    	        e.printStackTrace();
	    	    }
	    	    return retMap;
	    	}
}

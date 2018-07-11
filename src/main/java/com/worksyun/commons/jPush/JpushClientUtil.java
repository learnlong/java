package com.worksyun.commons.jPush;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.*;
 
 
public class JpushClientUtil {
 
    private final static String appKey = "532949d7d655f0b8ac928dc0";
 
    private final static String masterSecret = "28fad52cc7261a2755ebbea1";
 
    private static JPushClient jPushClient = new JPushClient(masterSecret,appKey);
 
    /**
     * 推送给设备标识参数的用户
     * @param registrationId 设备标识
     * @param notification_title 通知内容标题
     * @param msg_title 消息内容标题
     * @param msg_content 消息内容
     * @param extrasparam 扩展字段
     * @return 0推送失败，1推送成功
     */
    public static int sendToRegistrationId( String registrationId,String notification_title, String msg_title, String msg_content, String extrasparam) {
        int result = 0;
        try {
            PushPayload pushPayload= JpushClientUtil.buildPushObject_all_registrationId_alertWithTitle(registrationId,notification_title,msg_title,msg_content,extrasparam);
            System.out.println(pushPayload);
            PushResult pushResult=jPushClient.sendPush(pushPayload);
            System.out.println(pushResult);
            if(pushResult.getResponseCode()==200){
                result=1;
            }
        } catch (APIConnectionException e) {
            e.printStackTrace();
 
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
 
         return result;
    }
 
    /**
     * 发送给所有安卓用户
     * @param notification_title 通知内容标题
     * @param msg_title 消息内容标题
     * @param msg_content 消息内容
     * @param extrasparam 扩展字段
     * @return 0推送失败，1推送成功
     */
    public static int sendToAllAndroid( String notification_title, String msg_title, String msg_content, String extrasparam) {
        int result = 0;
        try {
            PushPayload pushPayload= JpushClientUtil.buildPushObject_android_all_alertWithTitle(notification_title,msg_title,msg_content,extrasparam);
            System.out.println(pushPayload);
            PushResult pushResult=jPushClient.sendPush(pushPayload);
            System.out.println(pushResult);
            if(pushResult.getResponseCode()==200){
                result=1;
            }
        } catch (Exception e) {
 
            e.printStackTrace();
        }
 
         return result;
    }
 
    /**
     * 发送给所有IOS用户
     * @param notification_title 通知内容标题
     * @param msg_title 消息内容标题
     * @param msg_content 消息内容
     * @param extrasparam 扩展字段
     * @return 0推送失败，1推送成功
     */
    public static int sendToAllIos(String notification_title, String msg_title, String msg_content, String extrasparam) {
        int result = 0;
        try {
            PushPayload pushPayload= JpushClientUtil.buildPushObject_ios_all_alertWithTitle(notification_title,msg_title,msg_content,extrasparam);
            System.out.println(pushPayload);
            PushResult pushResult=jPushClient.sendPush(pushPayload);
            System.out.println(pushResult);
            if(pushResult.getResponseCode()==200){
                result=1;
            }
        } catch (Exception e) {
 
            e.printStackTrace();
        }
 
         return result;
    }
 
    /**
     * 发送给所有用户
     * @param notification_title 通知内容标题
     * @param msg_title 消息内容标题
     * @param msg_content 消息内容
     * @param extrasparam 扩展字段
     * @return 0推送失败，1推送成功
     */
    public static int sendToAll( String notification_title, String msg_title, String msg_content, String extrasparam) {
        int result = 0;
        try {
            PushPayload pushPayload= JpushClientUtil.buildPushObject_android_and_ios(notification_title,msg_title,msg_content,extrasparam);
            System.out.println(pushPayload);
            PushResult pushResult=jPushClient.sendPush(pushPayload);
            System.out.println(pushResult);
            if(pushResult.getResponseCode()==200){
                result=1;
            }
        } catch (Exception e) {
 
            e.printStackTrace();
        }
 
        return result;
    }
 
 
 
    public static PushPayload buildPushObject_android_and_ios(String notification_title, String msg_title, String msg_content, String extrasparam) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .setAlert(notification_title)
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(notification_title)
                                .setTitle(notification_title)
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("androidNotification extras key",extrasparam)
                                .build()
                        )
                        .addPlatformNotification(IosNotification.newBuilder()
                                //传一个IosAlert对象，指定apns title、title、subtitle等
                                .setAlert(notification_title)
                                //直接传alert
                                //此项是指定此推送的badge自动加1
                                .incrBadge(1)
                                //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
                                .setSound("sound.caf")
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("iosNotification extras key",extrasparam)
                                //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
                                // .setContentAvailable(true)
 
                                .build()
                        )
                        .build()
                )
                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
                .setMessage(Message.newBuilder()
                        .setMsgContent(msg_content)
                        .setTitle(msg_title)
                        .addExtra("message extras key",extrasparam)
                        .build())
 
                .setOptions(Options.newBuilder()
                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                        .setApnsProduction(false)
                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
                        .setSendno(1)
                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
                        .setTimeToLive(86400)
                        .build()
                )
                .build();
    }
 
    private static PushPayload buildPushObject_all_registrationId_alertWithTitle(String registrationId,String notification_title, String msg_title, String msg_content, String extrasparam) {
 
        System.out.println("----------buildPushObject_all_all_alert");
        //创建一个IosAlert对象，可指定APNs的alert、title等字段
        //IosAlert iosAlert =  IosAlert.newBuilder().setTitleAndBody("title", "alert body").build();
 
        return PushPayload.newBuilder()
                //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
                .setPlatform(Platform.all())
                //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
                .setAudience(Audience.registrationId(registrationId))
                //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
                .setNotification(Notification.newBuilder()
                        //指定当前推送的android通知
                        .addPlatformNotification(AndroidNotification.newBuilder()
 
                                .setAlert(notification_title)
                                .setTitle(notification_title)
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("androidNotification extras key",extrasparam)
 
                                .build())
                        //指定当前推送的iOS通知
                        .addPlatformNotification(IosNotification.newBuilder()
                                //传一个IosAlert对象，指定apns title、title、subtitle等
                                .setAlert(notification_title)
                                //直接传alert
                                //此项是指定此推送的badge自动加1
                                .incrBadge(1)
                                //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
                                .setSound("sound.caf")
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("iosNotification extras key",extrasparam)
                                //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
                                //取消此注释，消息推送时ios将无法在锁屏情况接收
                                // .setContentAvailable(true)
 
                                .build())
 
 
                        .build())
                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
                .setMessage(Message.newBuilder()
 
                        .setMsgContent(msg_content)
 
                        .setTitle(msg_title)
 
                        .addExtra("message extras key",extrasparam)
 
                        .build())
 
                .setOptions(Options.newBuilder()
                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                        .setApnsProduction(false)
                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
                        .setSendno(1)
                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天；
                        .setTimeToLive(86400)
 
                        .build())
 
                .build();
 
    }
 
    private static PushPayload buildPushObject_android_all_alertWithTitle(String notification_title, String msg_title, String msg_content, String extrasparam) {
        System.out.println("----------buildPushObject_android_registrationId_alertWithTitle");
        return PushPayload.newBuilder()
                //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
                .setPlatform(Platform.android())
                //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
                .setAudience(Audience.all())
                //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
                .setNotification(Notification.newBuilder()
                        //指定当前推送的android通知
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(notification_title)
                                .setTitle(notification_title)
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("androidNotification extras key",extrasparam)
                                .build())
                        .build()
                )
                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
                .setMessage(Message.newBuilder()
                        .setMsgContent(msg_content)
                        .setTitle(msg_title)
                        .addExtra("message extras key",extrasparam)
                        .build())
 
                .setOptions(Options.newBuilder()
                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                        .setApnsProduction(false)
                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
                        .setSendno(1)
                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
                        .setTimeToLive(86400)
                        .build())
                .build();
    }
 
    private static PushPayload buildPushObject_ios_all_alertWithTitle( String notification_title, String msg_title, String msg_content, String extrasparam) {
        System.out.println("----------buildPushObject_ios_registrationId_alertWithTitle");
        return PushPayload.newBuilder()
                //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
                .setPlatform(Platform.ios())
                //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
                .setAudience(Audience.all())
                //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
                .setNotification(Notification.newBuilder()
                        //指定当前推送的android通知
                        .addPlatformNotification(IosNotification.newBuilder()
                                //传一个IosAlert对象，指定apns title、title、subtitle等
                                .setAlert(notification_title)
                                //直接传alert
                                //此项是指定此推送的badge自动加1
                                .incrBadge(1)
                                //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
                                .setSound("sound.caf")
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("iosNotification extras key",extrasparam)
                                //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
                               // .setContentAvailable(true)
 
                                .build())
                        .build()
                )
                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
                .setMessage(Message.newBuilder()
                        .setMsgContent(msg_content)
                        .setTitle(msg_title)
                        .addExtra("message extras key",extrasparam)
                        .build())
 
                .setOptions(Options.newBuilder()
                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                        .setApnsProduction(false)
                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
                        .setSendno(1)
                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
                        .setTimeToLive(86400)
                        .build())
                .build();
    }
    
    
    
    /**
	 * 客户端 给所有平台的一个或者一组用户发送信息
	 */
	public static void sendAlias(String message, List<String> aliasList)
	{
		JPushClient jpushClient = new JPushClient(masterSecret,
				appKey);
		Map<String, String> extras = new HashMap<String, String>();
		// 添加附加信息
		extras.put("extMessage", "我是额外的消息--sendAlias");
 
		PushPayload payload = allPlatformAndAlias(message, extras, aliasList);
		try
		{
			PushResult result = jpushClient.sendPush(payload);
			System.out.println(result);
		}
		catch (APIConnectionException e)
		{
			System.out.println(e);
		}
		catch (APIRequestException e)
		{
			System.out.println(e);
			System.out.println("Error response from JPush server. Should review and fix it. " + e);
			System.out.println("HTTP Status: " + e.getStatus());
			System.out.println("Error Code: " + e.getErrorCode());
			System.out.println("Error Message: " + e.getErrorMessage());
			System.out.println("Msg ID: " + e.getMsgId());
		}
	}
    
    /**
	 * 极光推送：生成向一个或者一组用户发送的消息。
	 */
	@SuppressWarnings("unused")
	private static PushPayload allPlatformAndAlias(String alert, Map<String, String> extras,
			List<String> aliasList)
	{
 
		return PushPayload
				.newBuilder()
				.setPlatform(Platform.all())
				.setAudience(Audience.alias(aliasList))
				.setNotification(
						Notification
								.newBuilder()
								.setAlert(alert)
								.addPlatformNotification(
										AndroidNotification.newBuilder().addExtras(extras).build())
								.addPlatformNotification(
										IosNotification.newBuilder().addExtras(extras).build())
								.build())
				.setOptions(Options.newBuilder().setApnsProduction(true).build()).build();
	}
 
    public static void main(String[] args){
        /*if(JpushClientUtil.sendToAllIos("testIos","testIos","this is a ios Dev test","")==1){
            System.out.println("success");
        }*/
        /*if(JpushClientUtil.sendToAllAndroid("testAndriod", "testAndroid", "this is a android Dev test", "")==1) {
        	System.out.println("success");
        }*/
        
        List<String> sendAlias = new ArrayList<>();
		sendAlias.add("123456");
		sendAlias.add("12345678");
		JpushClientUtil.sendAlias("这是分组发送2", sendAlias);
    }
}
 
 
 
/* class Jdpush2 {  
     //protected static final Logger LOG = LoggerFactory.getLogger(Jdpush.class);  
  
     // demo App defined in resources/jpush-api.conf   
  
    public static final String TITLE = "申通快递";  
    public static final String ALERT = "祝大家新春快乐";  
    public static final String MSG_CONTENT = "申通快递祝新老客户新春快乐";  
    public static final String REGISTRATION_ID = "0900e8d85ef";  
    public static final String TAG = "tag_api";  
      
    public  static JPushClient jpushClient=null;  
      
    public static void testSendPush(String appKey ,String masterSecret) {  
          
          
          
         jpushClient = new JPushClient(masterSecret, appKey, 3);  
          
        // HttpProxy proxy = new HttpProxy("localhost", 3128);  
        // Can use this https proxy: https://github.com/Exa-Networks/exaproxy  
         
          
        // For push, all you need do is to build PushPayload object.  
        //PushPayload payload = buildPushObject_all_all_alert();  
         //生成推送的内容，这里我们先测试全部推送  
        PushPayload payload=buildPushObject_all_alias_alert();  
          
          
        try {  
            System.out.println(payload.toString());  
            PushResult result = jpushClient.sendPush(payload);  
            System.out.println(result+"................................");  
              
            //LOG.info("Got result - " + result);  
              
        } catch (APIConnectionException e) {  
            //LOG.error("Connection error. Should retry later. ", e);  
              
        } catch (APIRequestException e) {  
            LOG.error("Error response from JPush server. Should review and fix it. ", e);  
            LOG.info("HTTP Status: " + e.getStatus());  
            LOG.info("Error Code: " + e.getErrorCode());  
            LOG.info("Error Message: " + e.getErrorMessage());  
            LOG.info("Msg ID: " + e.getMsgId());  
        }  
    }  
      
    public static PushPayload buildPushObject_all_all_alert() {  
        return PushPayload.alertAll(ALERT);  
    }  
      
    public static PushPayload buildPushObject_all_alias_alert() {  
        return PushPayload.newBuilder()  
                .setPlatform(Platform.all())//设置接受的平台  
                .setAudience(Audience.all())//Audience设置为all，说明采用广播方式推送，所有用户都可以接收到  
                .setNotification(Notification.alert(ALERT))  
                .build();  
    }  
      
    public static PushPayload buildPushObject_android_tag_alertWithTitle() {  
        return PushPayload.newBuilder()  
                .setPlatform(Platform.android())  
                .setAudience(Audience.all())  
                .setNotification(Notification.android(ALERT, TITLE, null))  
                .build();  
    }  
      
    public static PushPayload buildPushObject_android_and_ios() {  
        return PushPayload.newBuilder()  
                .setPlatform(Platform.android_ios())  
                .setAudience(Audience.tag("tag1"))  
                .setNotification(Notification.newBuilder()  
                        .setAlert("alert content")  
                        .addPlatformNotification(AndroidNotification.newBuilder()  
                                .setTitle("Android Title").build())  
                        .addPlatformNotification(IosNotification.newBuilder()  
                                .incrBadge(1)  
                                .addExtra("extra_key", "extra_value").build())  
                        .build())  
                .build();  
    }  
      
    public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage() {  
        return PushPayload.newBuilder()  
                .setPlatform(Platform.ios())  
                .setAudience(Audience.tag_and("tag1", "tag_all"))  
                .setNotification(Notification.newBuilder()  
                        .addPlatformNotification(IosNotification.newBuilder()  
                                .setAlert(ALERT)  
                                .setBadge(5)  
                                .setSound("happy")  
                                .addExtra("from", "JPush")  
                                .build())  
                        .build())  
                 .setMessage(Message.content(MSG_CONTENT))  
                 .setOptions(Options.newBuilder()  
                         .setApnsProduction(true)  
                         .build())  
                 .build();  
    }  
      
    public static PushPayload buildPushObject_ios_audienceMore_messageWithExtras() {  
        return PushPayload.newBuilder()  
                .setPlatform(Platform.android_ios())  
                .setAudience(Audience.newBuilder()  
                        .addAudienceTarget(AudienceTarget.tag("tag1", "tag2"))  
                        .addAudienceTarget(AudienceTarget.alias("alias1", "alias2"))  
                        .build())  
                .setMessage(Message.newBuilder()  
                        .setMsgContent(MSG_CONTENT)  
                        .addExtra("from", "JPush")  
                        .build())  
                .build();  
    }  
}*/
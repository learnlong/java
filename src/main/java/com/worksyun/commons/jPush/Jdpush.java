package com.worksyun.commons.jPush;

import java.util.ArrayList;

/**
 * 
 * 
 * @author changyafei
 * @date 2018-07-10
 */
 
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
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
 
/**
 * @author WangMeng
 * @date 2017年1月13日
 */
public class Jdpush
{
	private final static String MasterSecret = "28fad52cc7261a2755ebbea1";
	private final static String AppKey = "532949d7d655f0b8ac928dc0";
	/**
	 * 给所有平台的所有用户发通知
	 */
	public static void sendAllsetNotification(String message)
	{
		JPushClient jpushClient = new JPushClient(MasterSecret,
				AppKey);
		//JPushClient jpushClient = new JPushClient(masterSecret, appKey);//第一个参数是masterSecret 第二个是appKey
		Map<String, String> extras = new HashMap<String, String>();
		// 添加附加信息
		extras.put("extMessage", "我是额外的通知");
		PushPayload payload = buildPushObject_all_alias_alert(message, extras);
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
	 * 给所有平台的所有用户发消息
	 * 
	 * @param message
	 * @author WangMeng
	 * @date 2017年1月13日
	 */
	public static void sendAllMessage(String message)
	{
		JPushClient jpushClient = new JPushClient(MasterSecret,
				AppKey);
		Map<String, String> extras = new HashMap<String, String>();
		// 添加附加信息
		extras.put("extMessage", "我是额外透传的消息");
		PushPayload payload = buildPushObject_all_alias_Message(message, extras);
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
	 * 发送通知
	 * 
	 * @param message
	 * @param extras
	 * @return
	 * @author WangMeng
	 * @date 2017年1月13日
	 */
	private static PushPayload buildPushObject_all_alias_alert(String message,
			Map<String, String> extras)
	{
		return PushPayload.newBuilder()
				.setPlatform(Platform.all())
				// 设置平台
				.setAudience(Audience.all())
				// 按什么发送 tag alia
				.setNotification(
						Notification
								.newBuilder()
								.setAlert(message)
								.addPlatformNotification(
										AndroidNotification.newBuilder().addExtras(extras).build())
								.addPlatformNotification(
										IosNotification.newBuilder().addExtras(extras).build())
								.build())
				// 发送消息
				.setOptions(Options.newBuilder().setApnsProduction(true).build()).build();
				//设置ios平台环境  True 表示推送生产环境，False 表示要推送开发环境   默认是开发  
	}
	/**
	 * 发送透传消息
	 * 
	 * @param message
	 * @param extras
	 * @return
	 * @author WangMeng
	 * @date 2017年1月13日
	 */
	private static PushPayload buildPushObject_all_alias_Message(String message,
			Map<String, String> extras)
	{
		return PushPayload.newBuilder().setPlatform(Platform.all())
		// 设置平台
		.setAudience(Audience.all())
			// 按什么发送 tag alia
			.setMessage(Message.newBuilder().setMsgContent(message).addExtras(extras).build())
			// 发送通知
			.setOptions(Options.newBuilder().setApnsProduction(false).build()).build();
		//设置ios平台环境  True 表示推送生产环境，False 表示要推送开发环境   默认是开发  
	}
 
	/**
	 * 客户端 给所有平台的一个或者一组用户发送信息
	 */
	public static void sendAlias(String message, List<String> aliasList)
	{
		JPushClient jpushClient = new JPushClient(MasterSecret,
				AppKey);
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
	/**
	 * 客户端 给平台的一个或者一组标签发送消息。
	 */
	public static void sendTag(String message, String messageId, String type, List<String> tagsList)
	{
		JPushClient jpushClient = new JPushClient(MasterSecret,
				AppKey);
		// 附加字段
		Map<String, String> extras = new HashMap<String, String>();
		extras.put("messageId", messageId);
		extras.put("typeId", type);
 
		PushPayload payload = allPlatformAndTag(message, extras, tagsList);
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
	 * 极光推送：生成向一组标签进行推送的消息。
	 */
	private static PushPayload allPlatformAndTag(String alert, Map<String, String> extras,
			List<String> tagsList)
	{
 
		return PushPayload
				.newBuilder()
				.setPlatform(Platform.android_ios())
				.setAudience(Audience.tag(tagsList))
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
 
	public static void main(String[] args)
	{
		// new PushUtil().sendAll("这是java后台发送的一个通知。。。。");
		List<String> sendAlias = new ArrayList<>();
		sendAlias.add("1001");
		new Jdpush().sendAlias("这是分组发送。。。", sendAlias);
		
		//new Jdpush().sendAllMessage("这是后台发送的透传消息");
		//new Jdpush().sendAllsetNotification("这是后台发送的通知");
	}
}


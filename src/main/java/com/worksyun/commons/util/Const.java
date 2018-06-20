package com.worksyun.commons.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Const {
	public static Date MESSAGE_CODE_TIME = new Date();
	
	public static  String MESSAGE_CODE = "000000";
	
	public static final String SESSION_USERINFO_KEY="userInfo";//会话中存放用户信息的key
    
    public static final Map<String, Integer> USER_TYPE=new HashMap<String, Integer>();//用户类型
    static{
    	USER_TYPE.put("USERTYPE_1", 1);//需求方
    	USER_TYPE.put("USERTYPE_2", 2);//实施方
    	USER_TYPE.put("USERTYPE_3", 3);//第三方服务商
    	USER_TYPE.put("USERTYPE_9", 9);//系统管理员
    }
    
    public static final Map<String, Integer> USER_STATUS=new HashMap<String, Integer>();//用户类型
    static{
    	USER_STATUS.put("USERSTATUS_1", 1);//有效
    	USER_STATUS.put("USERSTATUS_2", 2);//禁用
    	USER_STATUS.put("USERSTATUS_99", 3);//作废
    }
	 public static final Map<String, Integer> BaseStatus=new HashMap<String, Integer>();//基础状态
	    static{
	    	BaseStatus.put("BaseStatus_1", 1);//有效
	    	BaseStatus.put("BaseStatus_99", 99);//作废
	    }
	
    public static final Map<String, Integer> SubscribeCategory=new HashMap<String, Integer>();//订阅类别
    static{
    	SubscribeCategory.put("SubscribeCategory_1", 1);//政策
    	SubscribeCategory.put("SubscribeCategory_2", 2);//情报
    	SubscribeCategory.put("SubscribeCategory_3", 3);//咨询
    	SubscribeCategory.put("SubscribeCategory_4", 4);//报告
    }
    
    public static final Map<String, Integer> ProjectStatus=new HashMap<String, Integer>();//需求状态
    static{
    	ProjectStatus.put("Valid", 1);//有效
    	ProjectStatus.put("Successful", 2);//成功
    	ProjectStatus.put("Failure", 3);//失败
    	ProjectStatus.put("Overdue", 4);//过期
    	ProjectStatus.put("Invalid", 5);//作废
    }
    
    public static final Map<String, String> RequireStageName=new HashMap<String, String>();//需求状态
    static{
    	RequireStageName.put("RequireStageName_1", "有效");//有效
    	RequireStageName.put("RequireStageName_2", "成功");//成功
    	RequireStageName.put("RequireStageName_3", "失败");//失败
    	RequireStageName.put("RequireStageName_4", "过期");//过期
    	RequireStageName.put("RequireStageName_5", "作废");//作废
    }
    
    public static final Map<String, Integer> JoinMode=new HashMap<String, Integer>();//参与方式
    static{
    	JoinMode.put("JoinMode_1", 1);//需求方
    	JoinMode.put("JoinMode_2", 2);//实施方
    }
    
    public static final Map<String, Integer> TaskState=new HashMap<String, Integer>();//任务状态
    static{
    	TaskState.put("TaskState_1", 1);//进行中
    	TaskState.put("TaskState_2", 2);//完成
    	TaskState.put("TaskState_3", 3);//撤销
    }
    
    public static final Map<String, Integer> ResourcesCollectionCategory=new HashMap<String, Integer>();//收藏资源类别
    static{
    	ResourcesCollectionCategory.put("ResourcesCollectionCategory_3", 3);//资讯
    	ResourcesCollectionCategory.put("ResourcesCollectionCategory_4", 4);//报告
    	ResourcesCollectionCategory.put("ResourcesCollectionCategory_2", 2);//活动
    	ResourcesCollectionCategory.put("ResourcesCollectionCategory_1", 1);//政策
    }

    public static final Map<String, Integer> Source=new HashMap<String, Integer>();//来源
    static{
    	Source.put("Source_1", 1);//PC
    	Source.put("Source_2", 2);//APP
    }

    public static final Map<String, String> ConsultationStatus=new HashMap<String, String>();//咨询状态
    static{
    	ConsultationStatus.put("ConsultationStatus_1", "新咨询");//新咨询
    	ConsultationStatus.put("ConsultationStatus_2", "已回复");//已回复
    	ConsultationStatus.put("ConsultationStatus_99", "作废");//作废
    }

    public static final Map<String, Integer> CustomReportType=new HashMap<String, Integer>();//定制报告类型
    static{
    	CustomReportType.put("CustomReportType_1", 1);//不可定制报告
    	CustomReportType.put("CustomReportType_2", 2);//可部分定制报告 
    	CustomReportType.put("CustomReportType_3", 3);//可个性化定制报告
    }

    public static final Map<String, String> CustomReportTypeName=new HashMap<String, String>();//定制报告类型
    static{
    	CustomReportTypeName.put("CustomReportTypeName_1", "不可定制报告");//不可定制报告
    	CustomReportTypeName.put("CustomReportTypeName_2", "可部分定制报告 ");//可部分定制报告
    	CustomReportTypeName.put("CustomReportTypeName_3", "可个性化定制报告");//可个性化定制报告
    }

	public static final Map<String, Integer> MemberOrderStatus=new HashMap<String, Integer>();//订单状态
	static{
		MemberOrderStatus.put("MemberOrderStatus_1", 1);//新建
		MemberOrderStatus.put("MemberOrderStatus_2", 2);//已支付
		MemberOrderStatus.put("MemberOrderStatus_3", 3);//过期
		MemberOrderStatus.put("MemberOrderStatus_4", 4);//作废
	}

}

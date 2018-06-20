package com.worksyun.commons.model;

import java.util.Map;

public class LoginModel{
	
	private boolean success;
	private String errorMsg;
	private Map<String,Object> data;
	public LoginModel() {
		super();
	}

	public LoginModel(boolean success, String errorMsg) {
		super();
		this.success = success;
		this.errorMsg = errorMsg;
	}
	
	public LoginModel(boolean success,Map<String,Object> data) {
		super();
		this.success = success;
		this.data=data;
	}
	
	
	public LoginModel(boolean success, String errorMsg,Map<String,Object> data) {
		super();
		this.success = success;
		this.errorMsg = errorMsg;
		this.data=data;
	}
	
	public Map<String,Object> getData() {
		return data;
	}

	public void setData(Map<String,Object> data) {
		this.data = data;
	}

	

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}



}

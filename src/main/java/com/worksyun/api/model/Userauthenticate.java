package com.worksyun.api.model;

import javax.persistence.Table;

import com.worksyun.commons.model.BaseModel;

@SuppressWarnings("serial")
@Table(name = "userauthenticate")
public class Userauthenticate extends BaseModel{
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getIccid() {
		return iccid;
	}
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	private String mobile;
	private String idcard;
	private String iccid;
	private String addr;
	private String company;
}

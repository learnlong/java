package com.worksyun.api.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.worksyun.commons.model.BaseModel;

@SuppressWarnings("serial")
@Table(name = "mififlowstatistics")
public class Mififlowstatistics extends BaseModel{
	private int id;
	
	private String mac;
	
	private String imei;
	
	@Column(name = "createTime")
	private Date createTime;
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "resetTime")
	private Date reset_time;
	
	private int up;
	
	public Date getReset_time() {
		return reset_time;
	}

	public void setReset_time(Date reset_time) {
		this.reset_time = reset_time;
	}

	private int down;
	
	private String ext;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public int getUp() {
		return up;
	}

	public void setUp(int up) {
		this.up = up;
	}

	public int getDown() {
		return down;
	}

	public void setDown(int down) {
		this.down = down;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}
}

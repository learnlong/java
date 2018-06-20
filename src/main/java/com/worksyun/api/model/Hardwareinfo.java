package com.worksyun.api.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

import com.worksyun.commons.model.BaseModel;

@SuppressWarnings("serial")
@Table(name = "hardwareinfo")
public class Hardwareinfo extends BaseModel{
    @Id
    @Column(name = "hardwareId")
    private String hardwareid;

    private String code;

    @Column(name = "cardNumber")
    private String cardnumber;

    @Column(name = "initialPassword")
    private String initialpassword;

    @Column(name = "currentPassword")
    private String currentpassword;

    @Column(name = "userId")
    private String userid;
    
    private String mac;
    
    private String imei;

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

	/**
     * 1-�½�,2-����,3-ͣ��,99-����
     */
    private Integer status;

    @Column(name = "creationUserName")
    private String creationusername;

    @Column(name = "creationTime")
    private Date creationtime;

    @Column(name = "creationUserId")
    private String creationuserid;

    @Column(name = "modifyUserName")
    private String modifyusername;

    @Column(name = "modifyUserId")
    private String modifyuserid;

    @Column(name = "modifyTime")
    private Date modifytime;

    private String name;

    @Column(name = "hardwareprice")
    private BigDecimal hardwareprice;

    /**
     * @return hardwareId
     */
    public String getHardwareid() {
        return hardwareid;
    }

    /**
     * @param hardwareid
     */
    public void setHardwareid(String hardwareid) {
        this.hardwareid = hardwareid;
    }

    /**
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return cardNumber
     */
    public String getCardnumber() {
        return cardnumber;
    }

    /**
     * @param cardnumber
     */
    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    /**
     * @return initialPassword
     */
    public String getInitialpassword() {
        return initialpassword;
    }

    /**
     * @param initialpassword
     */
    public void setInitialpassword(String initialpassword) {
        this.initialpassword = initialpassword;
    }

    /**
     * @return currentPassword
     */
    public String getCurrentpassword() {
        return currentpassword;
    }

    /**
     * @param currentpassword
     */
    public void setCurrentpassword(String currentpassword) {
        this.currentpassword = currentpassword;
    }

    /**
     * @return userId
     */
    public String getUserid() {
        return userid;
    }

    /**
     * @param userid
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * ��ȡ1-�½�,2-����,3-ͣ��,99-����
     *
     * @return status - 1-�½�,2-����,3-ͣ��,99-����
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * ����1-�½�,2-����,3-ͣ��,99-����
     *
     * @param status 1-�½�,2-����,3-ͣ��,99-����
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return creationUserName
     */
    public String getCreationusername() {
        return creationusername;
    }

    /**
     * @param creationusername
     */
    public void setCreationusername(String creationusername) {
        this.creationusername = creationusername;
    }

    /**
     * @return creationTime
     */
    public Date getCreationtime() {
        return creationtime;
    }

    /**
     * @param creationtime
     */
    public void setCreationtime(Date creationtime) {
        this.creationtime = creationtime;
    }

    /**
     * @return creationUserId
     */
    public String getCreationuserid() {
        return creationuserid;
    }

    /**
     * @param creationuserid
     */
    public void setCreationuserid(String creationuserid) {
        this.creationuserid = creationuserid;
    }

    /**
     * @return modifyUserName
     */
    public String getModifyusername() {
        return modifyusername;
    }

    /**
     * @param modifyusername
     */
    public void setModifyusername(String modifyusername) {
        this.modifyusername = modifyusername;
    }

    /**
     * @return modifyUserId
     */
    public String getModifyuserid() {
        return modifyuserid;
    }

    /**
     * @param modifyuserid
     */
    public void setModifyuserid(String modifyuserid) {
        this.modifyuserid = modifyuserid;
    }

    /**
     * @return modifyTime
     */
    public Date getModifytime() {
        return modifytime;
    }

    /**
     * @param modifytime
     */
    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

	public BigDecimal getHardwareprice() {
		return hardwareprice;
	}

	public void setHardwareprice(BigDecimal hardwareprice) {
		this.hardwareprice = hardwareprice;
	}

}
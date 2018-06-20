package com.worksyun.api.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

import com.worksyun.commons.model.BaseModel;

@SuppressWarnings("serial")
@Table(name = "equipmentorder")
public class Equipmentorder extends BaseModel{
    @Id
    @Column(name = "orderId")
    private String orderid;

    @Column(name = "userId")
    private String userid;

    @Column(name = "hardwareId")
    private String hardwareid;

    @Column(name = "addressId")
    private String addressid;

    /**
     * 1-δ����,2-�ѷ���,3-ȷ���ջ�,4-����
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

    @Column(name = "modifyTime2")
    private Date modifytime2;

    @Column(name = "orderstatus")
    private Integer orderstatus;

    private BigDecimal price;

    @Column(name = "transactionnumber")
    private String transactionnumber;

    @Column(name = "transactiontype")
    private Integer transactiontype;

    /**
     * @return orderId
     */
    public String getOrderid() {
        return orderid;
    }

    /**
     * @param orderid
     */
    public void setOrderid(String orderid) {
        this.orderid = orderid;
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
     * @return addressId
     */
    public String getAddressid() {
        return addressid;
    }

    /**
     * @param addressid
     */
    public void setAddressid(String addressid) {
        this.addressid = addressid;
    }

    /**
     * ��ȡ1-δ����,2-�ѷ���,3-ȷ���ջ�,4-����
     *
     * @return status - 1-δ����,2-�ѷ���,3-ȷ���ջ�,4-����
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * ����1-δ����,2-�ѷ���,3-ȷ���ջ�,4-����
     *
     * @param status 1-δ����,2-�ѷ���,3-ȷ���ջ�,4-����
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
     * @return modifyTime2
     */
    public Date getModifytime2() {
        return modifytime2;
    }

    /**
     * @param modifytime2
     */
    public void setModifytime2(Date modifytime2) {
        this.modifytime2 = modifytime2;
    }


    /**
     * @return price
     */
    public BigDecimal getPrice() {
        return price;
    }

    public Integer getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(Integer orderstatus) {
		this.orderstatus = orderstatus;
	}

	/**
     * @param price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }



	public String getTransactionnumber() {
		return transactionnumber;
	}

	public void setTransactionnumber(String transactionnumber) {
		this.transactionnumber = transactionnumber;
	}

	public Integer getTransactiontype() {
		return transactiontype;
	}

	public void setTransactiontype(Integer transactiontype) {
		this.transactiontype = transactiontype;
	}

}
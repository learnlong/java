package com.worksyun.api.model;

import java.util.Date;
import javax.persistence.*;

import com.worksyun.commons.model.BaseModel;

@SuppressWarnings("serial")
@Table(name = "useraddress")
public class Useraddress extends BaseModel{
    @Id
    @Column(name = "addressId")
    private String addressid;

    @Column(name = "userId")
    private String userid;

    @Column(name = "userName")
    private String username;

    @Column(name = "contactNumber")
    private String contactnumber;

    @Column(name = "shippingAddress")
    private String shippingaddress;

    /**
     * 1-����,2-�޸�,99-����
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

    public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	@Column(name = "isDefault")
    private Boolean isdefault;
    
    @Column(name = "postCode")
    private String postCode;
    

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
     * @return userName
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return contactNumber
     */
    public String getContactnumber() {
        return contactnumber;
    }

    /**
     * @param contactnumber
     */
    public void setContactnumber(String contactnumber) {
        this.contactnumber = contactnumber;
    }

    /**
     * @return shippingAddress
     */
    public String getShippingaddress() {
        return shippingaddress;
    }

    /**
     * @param shippingaddress
     */
    public void setShippingaddress(String shippingaddress) {
        this.shippingaddress = shippingaddress;
    }

    /**
     * ��ȡ1-����,2-�޸�,99-����
     *
     * @return status - 1-����,2-�޸�,99-����
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * ����1-����,2-�޸�,99-����
     *
     * @param status 1-����,2-�޸�,99-����
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
     * @return isDefault
     */
    public Boolean getIsdefault() {
        return isdefault;
    }

    /**
     * @param isdefault
     */
    public void setIsdefault(Boolean isdefault) {
        this.isdefault = isdefault;
    }
}
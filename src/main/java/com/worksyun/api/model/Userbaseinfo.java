package com.worksyun.api.model;

import java.util.Date;
import javax.persistence.*;

import com.worksyun.commons.model.BaseModel;

@SuppressWarnings("serial")
@Table(name = "userbaseinfo")
public class Userbaseinfo extends BaseModel{
    @Id
    @Column(name = "userId")
    private String userid;

    @Column(name = "certificationId")
    private String certificationid;

    private String mobile;

    private String password;

    @Column(name = "nickName")
    private String nickname;

    private String name;

    @Column(name = "idCard")
    private String idcard;

    private String cardpicture1;

    private String cardpicture2;

    /**
     * 1-����,2-��ͨ�û�
     */
    @Column(name = "userType")
    private Integer usertype;

    @Column(name = "creationTime")
    private Date creationtime;

    /**
     * 1-�½�,2-����, 99-����
     */
    private Integer status;

    private String avatar;

    private Integer integral;

    private String area;

    @Column(name = "modifyUserId")
    private String modifyuserid;

    @Column(name = "modifyUserName")
    private String modifyusername;

    @Column(name = "modifyTime")
    private Date modifytime;

    @Column(name = "creationUserName")
    private String creationusername;

    @Column(name = "creationUserId")
    private String creationuserid;

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
     * @return certificationId
     */
    public String getCertificationid() {
        return certificationid;
    }

    /**
     * @param certificationid
     */
    public void setCertificationid(String certificationid) {
        this.certificationid = certificationid;
    }

    /**
     * @return mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return nickName
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    /**
     * @return idCard
     */
    public String getIdcard() {
        return idcard;
    }

    /**
     * @param idcard
     */
    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    /**
     * @return cardpicture1
     */
    public String getCardpicture1() {
        return cardpicture1;
    }

    /**
     * @param cardpicture1
     */
    public void setCardpicture1(String cardpicture1) {
        this.cardpicture1 = cardpicture1;
    }

    /**
     * @return cardpicture2
     */
    public String getCardpicture2() {
        return cardpicture2;
    }

    /**
     * @param cardpicture2
     */
    public void setCardpicture2(String cardpicture2) {
        this.cardpicture2 = cardpicture2;
    }

    /**
     * ��ȡ1-����,2-��ͨ�û�
     *
     * @return userType - 1-����,2-��ͨ�û�
     */
    public Integer getUsertype() {
        return usertype;
    }

    /**
     * ����1-����,2-��ͨ�û�
     *
     * @param usertype 1-����,2-��ͨ�û�
     */
    public void setUsertype(Integer usertype) {
        this.usertype = usertype;
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
     * ��ȡ1-�½�,2-����, 99-����
     *
     * @return status - 1-�½�,2-����, 99-����
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * ����1-�½�,2-����, 99-����
     *
     * @param status 1-�½�,2-����, 99-����
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * @param avatar
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * @return integral
     */
    public Integer getIntegral() {
        return integral;
    }

    /**
     * @param integral
     */
    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    /**
     * @return area
     */
    public String getArea() {
        return area;
    }

    /**
     * @param area
     */
    public void setArea(String area) {
        this.area = area;
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
}
package com.worksyun.api.model;

import java.util.Date;
import javax.persistence.*;

import com.worksyun.commons.model.BaseModel;

@SuppressWarnings("serial")
@Table(name = "operatingstaff")
public class Operatingstaff extends BaseModel{
    @Id
    @Column(name = "userLoginName")
    private String userloginname;

    @Column(name = "userId")
    private String userid;

    @Column(name = "userName")
    private String username;

    private String password;

    @Column(name = "contactNumber")
    private String contactnumber;

    private String email;

    private Integer status;

    private Integer type;

    @Column(name = "creationTime")
    private Date creationtime;

    @Column(name = "modifyUserId")
    private String modifyuserid;

    @Column(name = "modifyTime")
    private Date modifytime;

    @Column(name = "modifyUserName")
    private String modifyusername;

    @Column(name = "creationUserId")
    private String creationuserid;

    @Column(name = "creationUserName")
    private String creationusername;

    /**
     * @return userLoginName
     */
    public String getUserloginname() {
        return userloginname;
    }

    /**
     * @param userloginname
     */
    public void setUserloginname(String userloginname) {
        this.userloginname = userloginname;
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
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(Integer type) {
        this.type = type;
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
}
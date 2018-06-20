package com.worksyun.api.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

import com.worksyun.commons.model.BaseModel;

@SuppressWarnings("serial")
@Table(name = "telecommunication")
public class Telecommunication extends BaseModel{
    @Id
    @Column(name = "TelecommunicationId")
    private String telecommunicationid;

    private String name;

    @Column(name = "TelecommunicationPrice")
    private BigDecimal telecommunicationprice;

    private Integer status;

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

    @Column(name = "creationTime")
    private Date creationtime;

    /**
     * @return TelecommunicationId
     */
    public String getTelecommunicationid() {
        return telecommunicationid;
    }

    /**
     * @param telecommunicationid
     */
    public void setTelecommunicationid(String telecommunicationid) {
        this.telecommunicationid = telecommunicationid;
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
     * @return TelecommunicationPrice
     */
    public BigDecimal getTelecommunicationprice() {
        return telecommunicationprice;
    }

    /**
     * @param telecommunicationprice
     */
    public void setTelecommunicationprice(BigDecimal telecommunicationprice) {
        this.telecommunicationprice = telecommunicationprice;
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
}
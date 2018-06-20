package com.worksyun.api.model;

import java.util.Date;
import javax.persistence.*;

import com.worksyun.commons.model.BaseModel;

@SuppressWarnings("serial")
@Table(name = "certification")
public class Certification extends BaseModel{
    @Id
    @Column(name = "certificationId")
    private String certificationid;

    /**
     * 1-�����,2-���ͨ��,3-��˲�ͨ��,99-����
     */
    @Column(name = "localhostStatus")
    private Integer localhoststatus;

    /**
     * 1-����֤,2-δ��֤
     */
    @Column(name = "telecomStatus")
    private Integer telecomstatus;

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

    @Column(name = "activation_status")
    private Integer activationStatus;

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
     * ��ȡ1-�����,2-���ͨ��,3-��˲�ͨ��,99-����
     *
     * @return localhostStatus - 1-�����,2-���ͨ��,3-��˲�ͨ��,99-����
     */
    public Integer getLocalhoststatus() {
        return localhoststatus;
    }

    /**
     * ����1-�����,2-���ͨ��,3-��˲�ͨ��,99-����
     *
     * @param localhoststatus 1-�����,2-���ͨ��,3-��˲�ͨ��,99-����
     */
    public void setLocalhoststatus(Integer localhoststatus) {
        this.localhoststatus = localhoststatus;
    }

    /**
     * ��ȡ1-����֤,2-δ��֤
     *
     * @return telecomStatus - 1-����֤,2-δ��֤
     */
    public Integer getTelecomstatus() {
        return telecomstatus;
    }

    /**
     * ����1-����֤,2-δ��֤
     *
     * @param telecomstatus 1-����֤,2-δ��֤
     */
    public void setTelecomstatus(Integer telecomstatus) {
        this.telecomstatus = telecomstatus;
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
     * @return activation_status
     */
    public Integer getActivationStatus() {
        return activationStatus;
    }

    /**
     * @param activationStatus
     */
    public void setActivationStatus(Integer activationStatus) {
        this.activationStatus = activationStatus;
    }
}
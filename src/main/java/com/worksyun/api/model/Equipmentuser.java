package com.worksyun.api.model;

import java.util.Date;
import javax.persistence.*;

import com.worksyun.commons.model.BaseModel;

@SuppressWarnings("serial")
@Table(name = "equipmentuser")
public class Equipmentuser extends BaseModel{
    @Id
    @Column(name = "equipmentId")
    private String equipmentid;

    @Column(name = "userId")
    private String userid;

    @Column(name = "getTime")
    private Date gettime;

    @Column(name = "returnTime")
    private Date returntime;

    /**
     * 1-�Ѹ�,2-δ��
     */
    @Column(name = "depositStatus")
    private Integer depositstatus;

    /**
     * 1-����,2-�ر�
     */
    @Column(name = "shareStatus")
    private Integer sharestatus;

    @Column(name = "sharedNumber")
    private Integer sharednumber;

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

    private Integer status;

    /**
     * @return equipmentId
     */
    public String getEquipmentid() {
        return equipmentid;
    }

    /**
     * @param equipmentid
     */
    public void setEquipmentid(String equipmentid) {
        this.equipmentid = equipmentid;
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
     * @return getTime
     */
    public Date getGettime() {
        return gettime;
    }

    /**
     * @param gettime
     */
    public void setGettime(Date gettime) {
        this.gettime = gettime;
    }

    /**
     * @return returnTime
     */
    public Date getReturntime() {
        return returntime;
    }

    /**
     * @param returntime
     */
    public void setReturntime(Date returntime) {
        this.returntime = returntime;
    }

    /**
     * ��ȡ1-�Ѹ�,2-δ��
     *
     * @return depositStatus - 1-�Ѹ�,2-δ��
     */
    public Integer getDepositstatus() {
        return depositstatus;
    }

    /**
     * ����1-�Ѹ�,2-δ��
     *
     * @param depositstatus 1-�Ѹ�,2-δ��
     */
    public void setDepositstatus(Integer depositstatus) {
        this.depositstatus = depositstatus;
    }

    /**
     * ��ȡ1-����,2-�ر�
     *
     * @return shareStatus - 1-����,2-�ر�
     */
    public Integer getSharestatus() {
        return sharestatus;
    }

    /**
     * ����1-����,2-�ر�
     *
     * @param sharestatus 1-����,2-�ر�
     */
    public void setSharestatus(Integer sharestatus) {
        this.sharestatus = sharestatus;
    }

    /**
     * @return sharedNumber
     */
    public Integer getSharednumber() {
        return sharednumber;
    }

    /**
     * @param sharednumber
     */
    public void setSharednumber(Integer sharednumber) {
        this.sharednumber = sharednumber;
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
}
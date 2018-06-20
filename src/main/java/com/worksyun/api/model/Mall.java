package com.worksyun.api.model;

import java.util.Date;
import javax.persistence.*;

import com.worksyun.commons.model.BaseModel;

@SuppressWarnings("serial")
@Table(name = "mall")
public class Mall extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    @Column(name = "addTime")
    private Date addtime;

    private String shelves;

    private Double prices;

    /**
     * 1-�½�,99-����
     */
    private Integer status;

    private Integer type;

    @Column(name = "discountPrice")
    private Double discountprice;

    @Column(name = "picturePath")
    private String picturepath;

    @Column(name = "videoPath")
    private String videopath;

    @Column(name = "creationUserId")
    private String creationuserid;

    @Column(name = "modifyUserName")
    private String modifyusername;

    @Column(name = "modifyUserId")
    private String modifyuserid;

    @Column(name = "modifyTime")
    private Date modifytime;

    @Column(name = "creationUserName")
    private String creationusername;

    @Column(name = "creationTime")
    private Date creationtime;

    @Column(name = "creationUserId2")
    private String creationuserid2;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
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
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return addTime
     */
    public Date getAddtime() {
        return addtime;
    }

    /**
     * @param addtime
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    /**
     * @return shelves
     */
    public String getShelves() {
        return shelves;
    }

    /**
     * @param shelves
     */
    public void setShelves(String shelves) {
        this.shelves = shelves;
    }

    /**
     * @return prices
     */
    public Double getPrices() {
        return prices;
    }

    /**
     * @param prices
     */
    public void setPrices(Double prices) {
        this.prices = prices;
    }

    /**
     * ��ȡ1-�½�,99-����
     *
     * @return status - 1-�½�,99-����
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * ����1-�½�,99-����
     *
     * @param status 1-�½�,99-����
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
     * @return discountPrice
     */
    public Double getDiscountprice() {
        return discountprice;
    }

    /**
     * @param discountprice
     */
    public void setDiscountprice(Double discountprice) {
        this.discountprice = discountprice;
    }

    /**
     * @return picturePath
     */
    public String getPicturepath() {
        return picturepath;
    }

    /**
     * @param picturepath
     */
    public void setPicturepath(String picturepath) {
        this.picturepath = picturepath;
    }

    /**
     * @return videoPath
     */
    public String getVideopath() {
        return videopath;
    }

    /**
     * @param videopath
     */
    public void setVideopath(String videopath) {
        this.videopath = videopath;
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
     * @return creationUserId2
     */
    public String getCreationuserid2() {
        return creationuserid2;
    }

    /**
     * @param creationuserid2
     */
    public void setCreationuserid2(String creationuserid2) {
        this.creationuserid2 = creationuserid2;
    }
}
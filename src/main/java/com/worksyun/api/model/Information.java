package com.worksyun.api.model;

import java.util.Date;
import javax.persistence.*;

import com.worksyun.commons.model.BaseModel;

@SuppressWarnings("serial")
@Table(name = "information")
public class Information extends BaseModel{
    @Id
    @Column(name = "newsId")
    private String newsid;

    private String title;

    private String remark;

    @Column(name = "creationTime")
    private Date creationtime;

    private Integer type;

    /**
     * 1-�½�,2-����
     */
    private Integer status;

    @Column(name = "modifyTime")
    private Date modifytime;

    @Column(name = "creationUserName")
    private String creationusername;

    @Column(name = "modifyUserName")
    private String modifyusername;

    private String source;

    private String tag;

    @Column(name = "videoPath")
    private String videopath;

    @Column(name = "picturePath")
    private String picturepath;

    @Column(name = "creationUserId")
    private String creationuserid;

    @Column(name = "modifyUserId")
    private String modifyuserid;

    private String content;

    /**
     * @return newsId
     */
    public String getNewsid() {
        return newsid;
    }

    /**
     * @param newsid
     */
    public void setNewsid(String newsid) {
        this.newsid = newsid;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
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
     * ��ȡ1-�½�,2-����
     *
     * @return status - 1-�½�,2-����
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * ����1-�½�,2-����
     *
     * @param status 1-�½�,2-����
     */
    public void setStatus(Integer status) {
        this.status = status;
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
     * @return source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * @param tag
     */
    public void setTag(String tag) {
        this.tag = tag;
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
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }
}
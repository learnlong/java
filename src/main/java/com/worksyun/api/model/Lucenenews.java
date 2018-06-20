package com.worksyun.api.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.worksyun.commons.model.BaseModel;

@SuppressWarnings("serial")
@Table(name = "lucenenews")
public class Lucenenews extends BaseModel{
	private int id;
	
	private String url;
	
	private String content;
	
	private String title;
	
	private String type;
	
	private int imgsize;
	
	public int getImgsize() {
		return imgsize;
	}

	public void setImgsize(int imgsize) {
		this.imgsize = imgsize;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	private String img;
	
	private String video;
	
	@Column(name = "createDate")
	private Date create_date;
	
	

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	@Column(name = "newsId")
	private int news_id;
	
	private String author;
	
	@Column(name = "PublicDate")
	private Date public_date;
	
	public Date getPublic_date() {
		return public_date;
	}

	public void setPublic_date(Date public_date) {
		this.public_date = public_date;
	}
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}

	public int getNews_id() {
		return news_id;
	}

	public void setNews_id(int news_id) {
		this.news_id = news_id;
	}
	
}

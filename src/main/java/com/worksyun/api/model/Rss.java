package com.worksyun.api.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.worksyun.commons.model.BaseModel;

@SuppressWarnings("serial")
@Table(name = "rss")
public class Rss extends BaseModel{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	
	private Integer type;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	private String Title;
	
	private String Headlines;
	
	private String Author;
	
	private String Link;
	
	@Column(name = "PublicDate")
	private Date public_date;
	
	public Date getPublic_date() {
		return public_date;
	}

	public void setPublic_date(Date public_date) {
		this.public_date = public_date;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	@Column(name = "ImageUrl")
	private String image_url;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getHeadlines() {
		return Headlines;
	}

	public void setHeadlines(String headlines) {
		Headlines = headlines;
	}

	public String getAuthor() {
		return Author;
	}

	public void setAuthor(String author) {
		Author = author;
	}

	public String getLink() {
		return Link;
	}

	public void setLink(String link) {
		Link = link;
	}


}

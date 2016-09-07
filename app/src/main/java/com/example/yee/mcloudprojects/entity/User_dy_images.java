package com.example.yee.mcloudprojects.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class User_dy_images implements Serializable{

	private long id;
	private long dynamic_id;
	private String url;
	private Timestamp time;
	
	public User_dy_images(){}
	
	
	public User_dy_images(long dynamic_id, String url) {
		super();
		this.dynamic_id = dynamic_id;
		this.url = url;
	}
	
	public User_dy_images(long id, long dynamic_id, String url) {
		super();
		this.id = id;
		this.dynamic_id = dynamic_id;
		this.url = url;
	}

	
	public User_dy_images(long id, long dynamic_id, String url, Timestamp time) {
		super();
		this.id = id;
		this.dynamic_id = dynamic_id;
		this.url = url;
		this.time = time;
	}


	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getDynamic_id() {
		return dynamic_id;
	}
	public void setDynamic_id(long dynamic_id) {
		this.dynamic_id = dynamic_id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "User_dy_images [id=" + id + ", dynamic_id=" + dynamic_id
				+ ", url=" + url + ", time=" + time + "]";
	}
	
	
}

package com.example.yee.mcloudprojects.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class Album implements Serializable{
	private int id;
	private int photo_id;
	private String url;
	private Timestamp time;
	public Album(){}

	public Album(int id, int photo_id, String url, Timestamp time) {
		super();
		this.id = id;
		this.photo_id = photo_id;
		this.url = url;
		this.time = time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPhoto_id() {
		return photo_id;
	}

	public void setPhoto_id(int photo_id) {
		this.photo_id = photo_id;
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
		return "Album [id=" + id + ", photo_id=" + photo_id + ", url=" + url
				+ ", time=" + time + "]";
	}


}

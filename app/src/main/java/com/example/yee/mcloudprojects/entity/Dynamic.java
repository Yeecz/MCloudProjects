package com.example.yee.mcloudprojects.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class Dynamic implements Serializable {
	private long id; 
	private int user_id; 
	private String text;
	private int image_c;
	private	int atm_c;
	private int comment_c;
	private	int good_c;
	private int relay_c;
	private	Timestamp time;
	private String gps;
	private String images;
	private String name;
	private String headportrait;
	private int zan;
	
	public Dynamic(){}
	
	public Dynamic(int user_id, String text, int image_c, String gps) {
		super();
		this.user_id = user_id;
		this.text = text;
		this.image_c = image_c;
		this.gps = gps;
	}
		


	public Dynamic(long id, int user_id, String text, int image_c, int atm_c,
			int comment_c, int good_c, int relay_c, Timestamp time, String gps,String images,String name) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.text = text;
		this.image_c = image_c;
		this.atm_c = atm_c;
		this.comment_c = comment_c;
		this.good_c = good_c;
		this.relay_c = relay_c;
		this.time = time;
		this.gps = gps;
		this.images = images;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getImage_c() {
		return image_c;
	}
	public void setImage_c(int album_id) {
		this.image_c = album_id;
	}
	public int getAtm_c() {
		return atm_c;
	}
	public void setAtm_c(int atm_c) {
		this.atm_c = atm_c;
	}
	public int getComment_c() {
		return comment_c;
	}
	public void setComment_c(int comment_c) {
		this.comment_c = comment_c;
	}
	public int getGood_c() {
		return good_c;
	}
	public void setGood_c(int good_c) {
		this.good_c = good_c;
	}
	public int getRelay_c() {
		return relay_c;
	}
	public void setRelay_c(int relay_c) {
		this.relay_c = relay_c;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public String getGps() {
		return gps;
	}
	public void setGps(String gps) {
		this.gps = gps;
	}
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeadportrait() {
		return headportrait;
	}

	public void setHeadportrait(String headportrait) {
		this.headportrait = headportrait;
	}

	public int getZan() {
		return zan;
	}

	public void setZan(int zan) {
		this.zan = zan;
	}

	@Override
	public String toString() {
		return "Dynamic{" +
				"id=" + id +
				", user_id=" + user_id +
				", text='" + text + '\'' +
				", image_c=" + image_c +
				", atm_c=" + atm_c +
				", comment_c=" + comment_c +
				", good_c=" + good_c +
				", relay_c=" + relay_c +
				", time=" + time +
				", gps='" + gps + '\'' +
				", images='" + images + '\'' +
				", name='" + name + '\'' +
				", headportrait='" + headportrait + '\'' +
				", zan=" + zan +
				'}';
	}
}

package com.example.yee.mcloudprojects.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class MCloudMsg implements Serializable {
	public MCloudMsg(){}
	private int id;
	private int res;
	private int user_id;
	private String	text1;
	private String text2;
	private Timestamp time;
	private String name;
	private String head;
	
	
	public MCloudMsg(int id, int res, int user_id, String text1, String text2,
					 Timestamp time) {
		super();
		this.id = id;
		this.res = res;
		this.user_id = user_id;
		this.text1 = text1;
		this.text2 = text2;
		this.time = time;
	}
	public int getRes() {
		return res;
	}
	public void setRes(int res) {
		this.res = res;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getText1() {
		return text1;
	}
	public void setText1(String text1) {
		this.text1 = text1;
	}
	public String getText2() {
		return text2;
	}
	public void setText2(String text2) {
		this.text2 = text2;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	@Override
	public String toString() {
		return "MCloudMsg{" +
				"id=" + id +
				", res=" + res +
				", user_id=" + user_id +
				", text1='" + text1 + '\'' +
				", text2='" + text2 + '\'' +
				", time=" + time +
				", name='" + name + '\'' +
				", head='" + head + '\'' +
				'}';
	}
}

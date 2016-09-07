package com.example.yee.mcloudprojects.entity;

import java.io.Serializable;

public class Friend implements Serializable {

//	private    long id;
	private    int	user_id;
	private    int	friend_id;
	private    String name;
	private    String headportrait;
	private	    String remarks;
	private 	int ver;
	private 	String sign;

	public Friend(){}


	public Friend(int user_id, int friend_id, String name,
				  String headportrait, String remarks, int ver,String sign) {
		super();
//		this.id = id;
		this.user_id = user_id;
		this.friend_id = friend_id;
		this.name = name;
		this.headportrait = headportrait;
		this.remarks = remarks;
		this.ver = ver;
		this.sign=sign;
	}


	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getFriend_id() {
		return friend_id;
	}
	public void setFriend_id(int friend_id) {
		this.friend_id = friend_id;
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getVer() {
		return ver;
	}
	public void setVer(int ver) {
		this.ver = ver;
	}

	public String getSign() {
		return sign;
	}


	public void setSign(String sign) {
		this.sign = sign;
	}


	@Override
	public String toString() {
		return "Friend{" +
				"user_id=" + user_id +
				", friend_id=" + friend_id +
				", name='" + name + '\'' +
				", headportrait='" + headportrait + '\'' +
				", remarks='" + remarks + '\'' +
				", ver=" + ver +
				", sign='" + sign + '\'' +
				'}';
	}
}
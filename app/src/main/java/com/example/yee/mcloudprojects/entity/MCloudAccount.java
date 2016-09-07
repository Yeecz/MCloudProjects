package com.example.yee.mcloudprojects.entity;

import java.io.Serializable;
public class MCloudAccount implements Serializable {
	public MCloudAccount(){}

	private int id;
	private int number;
	private long phone;
	private String password;
	private String mail;
	private int state;
	private int key;
	private int ver;

	public MCloudAccount(int number,String password) {
		this.number = number;
		this.password = password;
	}

	public MCloudAccount(int number,String password, String mail,int key) {
		super();
		this.number = number;
		this.password = password;
		this.mail = mail;
		this.key = key;
	}

	public MCloudAccount( int number, long phone, String password) {
		this.number = number;
		this.phone = phone;
		this.password = password;
	}

	public MCloudAccount( int number, long phone, String password, int state) {
		this.number = number;
		this.phone = phone;
		this.password = password;
		this.state = state;
	}

	public MCloudAccount(int id, int number, long phone, String password,
				   String mail, int state, int key,int ver) {
		super();
		this.id = id;
		this.number = number;
		this.phone = phone;
		this.password = password;
		this.mail = mail;
		this.state = state;
		this.key = key;
		this.ver = ver;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}


	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}


	public int getVer() {
		return ver;
	}

	public void setVer(int ver) {
		this.ver = ver;
	}

	@Override
	public String toString() {
		return "MCloudAccount [id=" + id + ", number=" + number + ", phone=" + phone
				+ ", password=" + password + ", mail=" + mail + ", state="
				+ state + ", key=" + key + ", ver=" + ver + "]";
	}



}
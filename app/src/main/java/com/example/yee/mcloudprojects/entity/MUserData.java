package com.example.yee.mcloudprojects.entity;


import java.io.Serializable;
import java.sql.Date;

public class MUserData implements Serializable{
	public MUserData(){}

	private int id;
	private String name;
	private String headportrait;//头像
	private	 int sex;
	private	 Date birthday;
	private long phone;
	private int ver;
	private String sign;  //签名

	public MUserData(String name, int sex) {
		super();
		this.name = name;
		this.sex = sex;
	}

	public MUserData(int id, String name, String headportrait, int sex,
				Date birthday, long phone,int ver,String sign) {
		super();
		this.id = id;
		this.name = name;
		this.headportrait = headportrait;
		this.sex = sex;
		this.birthday = birthday;
		this.phone = phone;
		this.ver = ver;
		this.sign=sign;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
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


	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public String toString() {
		return "MUserData [id=" + id + ", name=" + name + ", headportrait="
				+ headportrait + ", sex=" + sex + ", birthday=" + birthday
				+ ", phone=" + phone + ", ver=" + ver + ", sign=" + sign + "]";
	}



}

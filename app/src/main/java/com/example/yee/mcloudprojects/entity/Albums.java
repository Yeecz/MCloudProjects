package com.example.yee.mcloudprojects.entity;

import java.io.Serializable;

public class Albums implements Serializable {

	private int id; 
	private String name;
	private int  user_id;
	private String background;
	private String introduce;
	private int power; 
	private int count;
	
	
	public Albums(){}


	public Albums(int id, String name, int user_id,
				  String introduce,  int count) {
		super();
		this.id = id;
		this.name = name;
		this.user_id = user_id;
		this.introduce = introduce;
		this.count = count;
	}

	public Albums(int id, String name, int user_id, String background,
				  String introduce, int power, int count) {
		super();
		this.id = id;
		this.name = name;
		this.user_id = user_id;
		this.background = background;
		this.introduce = introduce;
		this.power = power;
		this.count = count;
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




	public int getUser_id() {
		return user_id;
	}




	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}




	public String getBackground() {
		return background;
	}




	public void setBackground(String background) {
		this.background = background;
	}




	public String getIntroduce() {
		return introduce;
	}




	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}




	public int getPower() {
		return power;
	}




	public void setPower(int power) {
		this.power = power;
	}




	public int getCount() {
		return count;
	}




	public void setCount(int count) {
		this.count = count;
	}




	@Override
	public String toString() {
		return "Albums [id=" + id + ", name=" + name + ", user_id="
				+ user_id + ", background=" + background + ", introduce="
				+ introduce + ", power=" + power + ", count=" + count + "]";
	}
	
	
	
}

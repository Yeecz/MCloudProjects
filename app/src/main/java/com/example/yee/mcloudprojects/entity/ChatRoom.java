package com.example.yee.mcloudprojects.entity;

import java.io.Serializable;

public class ChatRoom implements Serializable {

	private int id; 
	private String number;
	private	String name;
	private int user_id;
	public ChatRoom(){}
	
	
	public ChatRoom(int id, String number, String name, int user_id) {
		super();
		this.id = id;
		this.number = number;
		this.name = name;
		this.user_id = user_id;
	}


	public ChatRoom(int id, String number, String name) {
		super();
		this.id = id;
		this.number = number;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
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

	@Override
	public String toString() {
		return "ChatRoom [id=" + id + ", number=" + number + ", name=" + name
				+ "]";
	}
	
	
	
}

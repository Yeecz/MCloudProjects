package com.example.yee.mcloudprojects.entity;

import java.io.Serializable;

public class RongToken implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3560262035600782161L;
	private int code;
	private String userId;
	private String token;
	
	public RongToken(){}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMtoken() {
		return token;
	}

	public void setMtoken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "RongToken [code=" + code + ", userId=" + userId + ", token=" + token + "]";
	}
	
	
}

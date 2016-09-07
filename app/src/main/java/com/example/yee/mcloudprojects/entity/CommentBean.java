package com.example.yee.mcloudprojects.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class CommentBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private long id;//主键
	private long dynamic_id;//外键：帖子主键
	private	int user_id;//外键：用户主键
	private String text;//评论内容
	private Timestamp time;//评论时间
	private int level;//伪外键，若为0，则为一级评论，否则为被评论记录的主键
	private int author_id;//回复者
	private	int responser_id;//被回复者
	private	String author_name;
	private	String responser_name;
	private	String author_head;
	private	String responser_head;

	public CommentBean(){}

	//1级评论
	public CommentBean(long dynamic_id, int user_id, String text, int level, int author_id) {
		this.dynamic_id = dynamic_id;
		this.user_id = user_id;
		this.text = text;
		this.level = level;
		this.author_id = author_id;
	}

	//2级评论
	public CommentBean(long id, long dynamic_id, int user_id, String text, Timestamp time, int level, int author_id, int responser_id, String author_name, String responser_name, String author_head, String responser_head) {
		this.id = id;
		this.dynamic_id = dynamic_id;
		this.user_id = user_id;
		this.text = text;
		this.time = time;
		this.level = level;
		this.author_id = author_id;
		this.responser_id = responser_id;
		this.author_name = author_name;
		this.responser_name = responser_name;
		this.author_head = author_head;
		this.responser_head = responser_head;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
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

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getAuthor_id() {
		return author_id;
	}

	public void setAuthor_id(int author_id) {
		this.author_id = author_id;
	}

	public int getResponser_id() {
		return responser_id;
	}

	public void setResponser_id(int responser_id) {
		this.responser_id = responser_id;
	}

	public String getAuthor_name() {
		return author_name;
	}

	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}

	public String getResponser_name() {
		return responser_name;
	}

	public void setResponser_name(String responser_name) {
		this.responser_name = responser_name;
	}

	public String getAuthor_head() {
		return author_head;
	}

	public void setAuthor_head(String author_head) {
		this.author_head = author_head;
	}

	public String getResponser_head() {
		return responser_head;
	}

	public void setResponser_head(String responser_head) {
		this.responser_head = responser_head;
	}
}

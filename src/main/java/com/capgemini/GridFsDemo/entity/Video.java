package com.capgemini.GridFsDemo.entity;

import java.io.File;

public class Video {
	private String filePath;
	private String description;
	private String fileName;
	private String userName;
	private File fp;
	public Video() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Video(String filePath, String description, String fileName, String userName, File fp) {
		super();
		this.filePath = filePath;
		this.description = description;
		this.fileName = fileName;
		this.userName = userName;
		this.fp = fp;
	}
	public File getFp() {
		return fp;
	}
	public void setFp(File fp) {
		this.fp = fp;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
}

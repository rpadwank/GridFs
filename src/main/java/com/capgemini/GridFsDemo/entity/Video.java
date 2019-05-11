package com.capgemini.GridFsDemo.entity;

import java.io.File;

public class Video {
	private File filePath;
	private String description;
	private String fileName;
	private String userName;
	public Video() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Video(File filePath, String description, String fileName, String userName) {
		super();
		this.filePath = filePath;
		this.description = description;
		this.fileName = fileName;
		this.userName = userName;
	}
	public File getFilePath() {
		return filePath;
	}
	public void setFilePath(File filePath) {
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

package com.capgemini.GridFsDemo.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.gridfs.GridFSDBFile;

@RestController
public class GridFsController {

	@Autowired
	private GridFsOperations gridFsOperations;
	
	String fileId="";
	
	@PostMapping("/save")
	public String saveFile() throws FileNotFoundException {
		
		//define metadata
		DBObject metaData = new BasicDBObject();
		
		//put organization in metadata
		metaData.put("organization", "Fun video");
		InputStream inputStream = new FileInputStream("C:/Users/rpadwank/Desktop/logo.png");
		metaData.put("type", "image");
		
		//store image file to mongodb
		fileId = gridFsOperations.store(inputStream, "logo.png", "image/png", metaData).getId().toString();
		
		return "File Stored Successfully";
	}
	
	@GetMapping("/save")
	public String retrieveImageFile() throws IOException {
		GridFSDBFile dbFile = gridFsOperations.findOne(new Query(Criteria.where("_id").is("5cd3c37b3ff9da403c2f01b3")));
		//dbFile.writeTo("C:\\Ruchir Padwankar\\images");
		return dbFile.toString();
		
	}
}

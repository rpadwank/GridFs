package com.capgemini.GridFsDemo.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.capgemini.GridFsDemo.entity.Video;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.gridfs.GridFSDBFile;
@CrossOrigin("*")
@RestController
public class GridFsController {

	@Autowired
	private GridFsOperations gridFsOperations;
	
	String fileId="";
	
	@PostMapping("/upload/{userName}")
	public ResponseEntity<Video> saveFile(@RequestParam("filePath") MultipartFile file, @PathVariable String userName) throws IOException {
		
		if(!file.isEmpty()) {
			System.out.println(file.getInputStream());
			System.out.println(userName);
			//System.out.println(userName.getName());
		}
		
		//define metadata
		DBObject metaData = new BasicDBObject();
		//System.out.println(video.getFileName());
		//put organization in metadata
		metaData.put("organization", "Fun video");
		metaData.put("userName", userName);
		//System.out.println("fp:"+video.getFp());
		
		  InputStream inputStream = file.getInputStream();
		  metaData.put("type", "image");
		 
		
		//store image file to mongodb
		fileId = gridFsOperations.store(inputStream, file.getOriginalFilename(), "image/png", metaData).getId().toString();
		//return null;
		return new ResponseEntity<Video>(HttpStatus.OK);
	}
	
	
	  @GetMapping("/save/{username}") 
	  public ResponseEntity<byte[]> retrieveImageFile(@PathVariable String username) throws IOException {
		/*
		 * GridFSDBFile dbFile = gridFsOperations.findOne(new
		 * Query(Criteria.where("_id").is("5cd3c37b3ff9da403c2f01b3")));
		 */
		  GridFSDBFile dbFile = gridFsOperations.findOne(new
				  Query(Criteria.where("metadata.userName").is(username)));
	  System.out.println(dbFile.toString());
	  return new ResponseEntity<byte[]>(IOUtils.toByteArray(dbFile.getInputStream()), HttpStatus.OK);
	  
	  }
	 
}

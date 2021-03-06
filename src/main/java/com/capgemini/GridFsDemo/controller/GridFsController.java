package com.capgemini.GridFsDemo.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
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

import com.capgemini.GridFsDemo.entity.FileResource;
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
	public ResponseEntity<Video> saveFile(@RequestParam("filePath") MultipartFile file, @PathVariable String userName,@RequestParam("title") String title, @RequestParam("description") String description ,@RequestParam("category") String category) throws IOException {
	
		if(!file.isEmpty()) {
			System.out.println(file.getInputStream());
			System.out.println(userName);
			System.out.println(title);
			System.out.println(description);
			System.out.println(category);
			//System.out.println(userName.getName());
		}
		
		//define metadata
		DBObject metaData = new BasicDBObject();
		//System.out.println(video.getFileName());
		//put organization in metadata
		//metaData.put("organization", "Fun video");
		metaData.put("userName", userName);
		metaData.put("title", title);
		metaData.put("description", description);
		metaData.put("category", category);
		//System.out.println("fp:"+video.getFp());
		
		  InputStream inputStream = file.getInputStream();
		  metaData.put("type", "video");
		 
		
		//store image file to mongodb
		fileId = gridFsOperations.store(inputStream, file.getOriginalFilename(), metaData).getId().toString();
		//return null;
		return new ResponseEntity<Video>(HttpStatus.OK);
	}
	
	
	  @GetMapping("/save/{username}") 
	  public ResponseEntity retrieveSingleVideoFileUsingUsername(@PathVariable String username) throws IOException {
		/*
		 * GridFSDBFile dbFile = gridFsOperations.findOne(new
		 * Query(Criteria.where("_id").is("5cd3c37b3ff9da403c2f01b3")));
		 */
		  GridFSDBFile dbFile = gridFsOperations.findOne(new
				  Query(Criteria.where("metadata.userName").is(username)));
	  InputStreamResource inputStreamResource = new InputStreamResource(dbFile.getInputStream());
	  //byte[] b = IOUtils.toByteArray(dbFile.getInputStream());
	  System.out.println(inputStreamResource);
	  return new ResponseEntity(inputStreamResource, HttpStatus.OK);
	  
	  }
	  
	  @GetMapping("/retrieve/{id}")
	  public ResponseEntity retrieveVideoUsingId(@PathVariable String id) {
		  GridFSDBFile dbFile = gridFsOperations.findOne(new
				  Query(Criteria.where("_id").is(id)));
		  InputStreamResource inputStreamResource = new InputStreamResource(dbFile.getInputStream());
		  return new ResponseEntity(inputStreamResource, HttpStatus.OK);
		  
	  }
	  
	  @GetMapping("/save")
	  public ResponseEntity<List<FileResource>> retrieveVideoFileForHomepage() throws IOException {
		  List<GridFSDBFile> dbFileList = gridFsOperations.find(null);
		  List<FileResource> fileResource = new ArrayList<>();
		  //List<InputStreamResource> inputStreamResources = new ArrayList<>();
		  //List<byte[]> byteFiles = new ArrayList<byte[]>();
		  int index = 0;
		  for(GridFSDBFile dbFL: dbFileList )
		  {
			  FileResource fr = new FileResource(dbFL.getId().toString());
			  fileResource.add(fr);
			  //byteFiles.add(IOUtils.toByteArray(dbFL.getInputStream()));
			  System.out.println(dbFL.getInputStream());
			  //inputStreamResources.add(new InputStreamResource(dbFL.getInputStream()));
		  }
		  //for(int i=0;i<inputStreamResources.size();i++)
		//	  System.out.println(inputStreamResources.get(i));
		  return new ResponseEntity<List<FileResource>>(fileResource, HttpStatus.OK);
	  }
	 
}

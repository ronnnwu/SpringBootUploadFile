package com.javasampleapproach.uploadfile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javasampleapproach.uploadfile.storage.StorageService;

@Controller
public class UploadController {

	@Autowired
	StorageService storageService;

	public static String filename = "v1.csv";

	@GetMapping("/")
	public String listUploadedFiles() {
		return "index";
	}

	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
		Resource file = storageService.loadFile(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@GetMapping("/file")
	@ResponseBody
	public String getFilename() {
		return filename;
	}

}
package com.Controller.AdminController;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Bean.EnroleSubjectFilesBean;
import com.Service.SubjectFileService;

@RestController
@RequestMapping("/subjectfile")
public class SubjectFileController {

	@Autowired
	SubjectFileService fileService;

	@PostMapping("/add")
	public Object sendToUser(@RequestBody EnroleSubjectFilesBean filesBean) throws Exception {
		return ResponseEntity.ok(fileService.sendToUser(filesBean));
	}

	@GetMapping("/get/{userId}")
	public Object getAllFileForUser(@PathVariable("userId") Integer userId) throws IOException {
		return ResponseEntity.ok(fileService.getAllFileForUser(userId));
	}

	@GetMapping("/getfile/{subjectfileId}")
	public Object getFile(@PathVariable("subjectfileId") Integer subjectfileId) throws IOException {
		return ResponseEntity.ok(fileService.getFile(subjectfileId));
	}
}

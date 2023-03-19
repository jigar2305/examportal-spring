package com.Controller.AdminController;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Bean.Getsubjectfile;
import com.Service.SubjectService;

@RequestMapping("/subject")
@RestController
public class SubjectController {

	@Autowired
	SubjectService subjectService;

	@PostMapping("/add")
	public Object addsubject2(@RequestBody Getsubjectfile subjectfile) throws IOException {
		return ResponseEntity.ok(subjectService.addSubject(subjectfile));
	}

	@PostMapping("/update")
	public Object updatesubject(@RequestBody Getsubjectfile subjectfile) throws IOException {
		return ResponseEntity.ok(subjectService.updateSubject(subjectfile));
	}

	@GetMapping("/list")
	public Object listsubject() {
		return ResponseEntity.ok(subjectService.listSubject());
	}

	@DeleteMapping("/delete/{subjectId}")
	public Object deletesubject(@PathVariable("subjectId") Integer subjectId) {
		return ResponseEntity.ok(subjectService.deleteSubject(subjectId));
	}

	@GetMapping("/get/{subjectId}")
	public Object getsubjectbyid(@PathVariable("subjectId") Integer subjectId) {
		return ResponseEntity.ok(subjectService.findSubjectById(subjectId));
	}

	@GetMapping("/child/{subjectId}")
	public Object getquebysubject(@PathVariable("subjectId") Integer subjectId) {
		return ResponseEntity.ok(subjectService.checkForDelete(subjectId));
	}

	@GetMapping("/getfiles/{subjectId}")
	public Object getfiles(@PathVariable("subjectId") Integer subjectId) {
		return ResponseEntity.ok(subjectService.getSubjectFile(subjectId));
	}

	@DeleteMapping("/deletefile/{subjectfileId}")
	public Object deletesubjectfile(@PathVariable("subjectfileId") Integer subjectfileId) {
		return ResponseEntity.ok(subjectService.deleteFile(subjectfileId));
	}

}

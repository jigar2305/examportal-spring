package com.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bean.ResponseBean;
import com.bean.forms.QuestionBean;
import com.bean.forms.SubjectBean;
import com.repository.QuestionRepository;
import com.repository.SubjectRepository;
import com.service.SubjectFileService;

@CrossOrigin
@RestController
@RequestMapping("/subject")
public class SubjectController {
	
	@Autowired
	SubjectRepository subjectRepo;
	
	@Autowired
	QuestionRepository questionRepo;
	
	@Autowired
	SubjectFileService subjectFileService;
	
	@PostMapping("/add")
	public ResponseEntity<?> addsubject(@RequestBody SubjectBean subject) {
		SubjectBean subjectBean = subjectRepo.findBySubjectName(subject.getSubjectName());
		ResponseBean<SubjectBean> res = new ResponseBean<>();
		if (subjectBean == null) {
			subjectRepo.save(subject);
			res.setData(subjectBean);
			res.setMsg("subject added..");
			return ResponseEntity.ok(res);
		} else {
			res.setData(subject);
			res.setMsg("subject exist..");
			return ResponseEntity.ok(res);
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/add2", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST)
	public ResponseEntity<?> addsubject2(@RequestParam("subject") SubjectBean subject,@RequestParam(value = "file", required = true) MultipartFile[] files) {
		
		SubjectBean subjectBean = subjectRepo.findBySubjectName(subject.getSubjectName());
		
		ResponseBean<SubjectBean> res = new ResponseBean<>();
		if (subjectBean == null) {
			
			SubjectBean subjectres = subjectRepo.save(subject);
			if(subjectres != null) {
				subjectFileService.addfiles(files,subjectres);
				res.setData(subjectres);
				res.setMsg("subject added..");
				return ResponseEntity.ok(res);
			}else {
				
			}
			res.setData(subjectBean);
			res.setMsg("something went wrong..");
			return ResponseEntity.ok(res);
		} else {
			res.setData(subjectBean);
			res.setMsg("subject exist..");
			return ResponseEntity.ok(res);
		}
	}
	
	
	@GetMapping("/list")
	public ResponseEntity<?> listsubject() {
		List<SubjectBean> subjects = (List<SubjectBean>) subjectRepo.findAll();
		ResponseBean<List<SubjectBean>> res = new ResponseBean<>();
		res.setData(subjects);
		res.setMsg("list successfully");
		return ResponseEntity.ok(res);
	}
	
	@DeleteMapping("/delete/{subjectId}")
	public ResponseEntity<?> deletesubject(@PathVariable("subjectId") Integer subjectId) {
		Optional<SubjectBean> subject = subjectRepo.findById(subjectId);
		ResponseBean<Object> res = new ResponseBean<>();
		if(subject.isEmpty()) {
			res.setData(subjectId);
			res.setMsg("subjects not found ");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}else {
			
		List<QuestionBean> questions = questionRepo.findBySubject(subject);
		if(questions != null) {
			questionRepo.deleteAll(questions);
		}
		subjectRepo.deleteById(subjectId);
		res.setData(subjectId);
		res.setMsg("deleted successfully");
		return ResponseEntity.ok(res);
		}
	}
	
	@GetMapping("/subjectbyId/{subjectId}")
	public ResponseEntity<?> getcoursebyid(@PathVariable("subjectId") Integer subjectId) {
		Optional<SubjectBean> subject = subjectRepo.findById(subjectId);
		ResponseBean<Optional<SubjectBean>> res = new ResponseBean<>();
		if (subject.isEmpty()) {
			res.setData(subject);
			res.setMsg("subject not exist.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		} else {
			res.setData(subject);
			res.setMsg("get subject successfully");
			return ResponseEntity.ok(res);
		}
	}
	

}

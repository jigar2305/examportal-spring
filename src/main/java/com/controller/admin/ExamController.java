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
import org.springframework.web.bind.annotation.RestController;

import com.bean.ResponseBean;
import com.bean.forms.ExamBean;
import com.repository.ExamRepository;

@CrossOrigin
@RestController
@RequestMapping("/exam")
public class ExamController {
	@Autowired
	ExamRepository examRepo;

	@PostMapping("/add")
	public ResponseEntity<?> addexam(@RequestBody ExamBean exam) {
		ExamBean examBean = examRepo.findByExamName(exam.getExamName());
		ResponseBean<ExamBean> res = new ResponseBean<>();
		if (examBean == null) {
			examRepo.save(exam);
			res.setData(exam);
			res.setMsg("exam added sussessfully...");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(res);
		} else {
			res.setData(examBean);
			res.setMsg("exam alredy added...");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}
	}
	
	@GetMapping("/list")
	public ResponseEntity<?> list() {
		List<ExamBean> exams = (List<ExamBean>) examRepo.findAll();
		ResponseBean<List<ExamBean>> res = new ResponseBean<>();
		res.setData(exams);
		res.setMsg("sussessfully");
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(res);
	}
	
	@DeleteMapping("/delete/{examId}")
	public ResponseEntity<?> deleteexam(@PathVariable("examId") Integer examId) {
		Optional<ExamBean> examBean = examRepo.findById(examId);
		ResponseBean<Object> res = new ResponseBean<>();
		if (examBean.isEmpty()) {
			res.setData(examId);
			res.setMsg("exam not found");
			return ResponseEntity.ok(res);
		} else {
			examRepo.deleteById(examId);
			res.setData(examId);
			res.setMsg("deleted successfully");
			return ResponseEntity.ok(res);
		}
	}
	@GetMapping("/get/{examId}")
	public ResponseEntity<?> getquestionbyId(@PathVariable("examId") Integer examId) {
		Optional<ExamBean> examBean = examRepo.findById(examId);
		if (examBean.isEmpty()) {
			ResponseBean<Object> res = new ResponseBean<>();
			res.setData(examId);
			res.setMsg("exam not found");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		} else {
			ResponseBean<Optional<ExamBean>> res = new ResponseBean<>();
			res.setData(examBean);
			res.setMsg("fetch successfully");
			return ResponseEntity.ok(res);
		}
	}

}

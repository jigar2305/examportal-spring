package com.Controller.AdminController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Bean.EnroleexamBean;
import com.Bean.ExamMSubjectBean;
import com.Service.ExamService;

@CrossOrigin
@RestController
@RequestMapping("/exam")
public class ExamController {

	@Autowired
	ExamService examService;

	@PostMapping("/add")
	public ResponseEntity<?> addexamandquestion(@RequestBody ExamMSubjectBean examsubject) {
		return ResponseEntity.ok(examService.addExamAndQuestion(examsubject));
	}

	@GetMapping("/list")
	public ResponseEntity<?> listExams() {
		return ResponseEntity.ok(examService.listExams());
	}

	@GetMapping("/list/{userId}")
	public ResponseEntity<?> getExamByUserId(@PathVariable("userId") Integer userId) {
		return ResponseEntity.ok(examService.getExamByUserId(userId));
	}

	@DeleteMapping("/delete/{examId}")
	public ResponseEntity<?> deleteExam(@PathVariable("examId") Integer examId) {
		return ResponseEntity.ok(examService.deleteExam(examId));
	}

	@GetMapping("/child/{examId}")
	public ResponseEntity<?> isEnroll(@PathVariable("examId") Integer examId) {
		return ResponseEntity.ok(examService.isEnroll(examId));
	}

	@GetMapping("/get/{examId}")
	public ResponseEntity<?> getQuestionById(@PathVariable("examId") Integer examId) {
		return ResponseEntity.ok(examService.getQuestionById(examId));
	}

	@GetMapping("/statusofexam/{examId}")
	public ResponseEntity<?> getStatusOfExam(@PathVariable("examId") Integer examId) {
		return ResponseEntity.ok(examService.getStatusOfExam(examId));
	}
	@PostMapping("/enrollExam")
	public ResponseEntity<?> enrollExam(@RequestBody EnroleexamBean enroleexam) {
		return null;
	}
	

}

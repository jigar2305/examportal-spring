package com.Controller.AdminController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/exam")
public class ExamController {

	@Autowired
	ExamService examService;

	@PostMapping("/add")
	public Object addexamandquestion(@RequestBody ExamMSubjectBean examsubject) throws Exception {
		return ResponseEntity.ok(examService.addExamAndQuestion(examsubject));
	}

	@GetMapping("/list")
	public Object listExams() throws Exception {
		return ResponseEntity.ok(examService.listExams());
	}

	@GetMapping("/list/{userId}")
	public Object getExamByUserId(@PathVariable("userId") Integer userId) throws Exception {
		return ResponseEntity.ok(examService.getExamByUserId(userId));
	}

	@DeleteMapping("/delete/{examId}")
	public Object deleteExam(@PathVariable("examId") Integer examId) throws Exception {
		return ResponseEntity.ok(examService.deleteExam(examId));
	}

	@GetMapping("/child/{examId}")
	public Object isEnroll(@PathVariable("examId") Integer examId) throws Exception {
		return ResponseEntity.ok(examService.isEnroll(examId));
	}

	@GetMapping("/get/{examId}")
	public Object getQuestionById(@PathVariable("examId") Integer examId) throws Exception {
		return ResponseEntity.ok(examService.getQuestionById(examId));
	}

	@GetMapping("/statusofexam/{examId}")
	public Object getStatusOfExam(@PathVariable("examId") Integer examId) throws Exception {
		return ResponseEntity.ok(examService.getStatusOfExam(examId));
	}
	@PostMapping("/enrollExam")
	public Object enrollExam(@RequestBody EnroleexamBean enroleexam) throws Exception {
		return ResponseEntity.ok(examService.enrollExam(enroleexam));
	}
	

}

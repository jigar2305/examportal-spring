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

import com.Bean.CheckquestionanswerBean;
import com.Service.ExamquestionService;

@CrossOrigin
@RequestMapping("/eqc")
@RestController
public class ExamquestionController {
	
	@Autowired
	ExamquestionService examquestionService;

	@GetMapping("/get/{examId}")
	public ResponseEntity<?> getQuestions(@PathVariable("examId") Integer examId) {
		return ResponseEntity.ok(examquestionService.getQuestions(examId));
	}

	@GetMapping("/getque/{examId}")
	public ResponseEntity<?> getExamQuestion(@PathVariable("examId") Integer examId) {
		return ResponseEntity.ok(examquestionService.getExamQuestion(examId));
	}

	@PostMapping("/checkanswer")
	public ResponseEntity<?> checkanswer(@RequestBody CheckquestionanswerBean questions) {
		return ResponseEntity.ok(examquestionService.checkanswer(questions));

	}

	@DeleteMapping("/delete/{examId}")
	public ResponseEntity<?> deleteExamQuestios(@PathVariable("examId") Integer examId) {
		return ResponseEntity.ok(examquestionService.deleteExamQuestios(examId));

	}

}

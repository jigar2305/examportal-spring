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

import com.Bean.CheckquestionanswerBean;
import com.Service.ExamquestionService;

@RequestMapping("/eqc")
@RestController
public class ExamquestionController {
	
	@Autowired
	ExamquestionService examquestionService;

	@GetMapping("/get/{examId}")
	public Object getQuestions(@PathVariable("examId") Integer examId) throws Exception {
		return ResponseEntity.ok(examquestionService.getQuestions(examId));
	}

	@GetMapping("/getque/{examId}")
	public Object getExamQuestion(@PathVariable("examId") Integer examId) throws Exception {
		return ResponseEntity.ok(examquestionService.getExamQuestion(examId));
	}
	
	@GetMapping("/getquestion/{examId}")
	public Object getExamQuestionWithImage(@PathVariable("examId") Integer examId) throws Exception {
		return ResponseEntity.ok(examquestionService.getExamQuestionWithImage(examId));
	}

	@PostMapping("/checkanswer")
	public Object checkanswer(@RequestBody CheckquestionanswerBean questions) throws Exception {
		return ResponseEntity.ok(examquestionService.checkanswer(questions));

	}

	@DeleteMapping("/delete/{examId}")
	public Object deleteExamQuestios(@PathVariable("examId") Integer examId) throws Exception {
		return ResponseEntity.ok(examquestionService.deleteExamQuestios(examId));

	}

}

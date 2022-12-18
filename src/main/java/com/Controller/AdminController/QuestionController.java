package com.Controller.AdminController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Entity.QuestionBean;
import com.Service.QuestionService;

@RequestMapping("/que")
@RestController
public class QuestionController {

	@Autowired
	QuestionService questionService;

	@PostMapping("/add")
	public ResponseEntity<?> addQuestions(@RequestBody List<QuestionBean> questions) {
		return ResponseEntity.ok(questionService.addQuestions(questions));
	}
	
	@PostMapping("/single-add")
	public ResponseEntity<?> addQuestion(@RequestBody QuestionBean question) {
		return ResponseEntity.ok(questionService.addQuestion(question));
	}

	@GetMapping("/list")
	public ResponseEntity<?> getAllQuestions() {
		return ResponseEntity.ok(questionService.getAllQuestions());
	}

	@GetMapping("/child/{questionId}")
	public ResponseEntity<?> checkfordelete(@PathVariable("questionId") Integer questionId) {
		return ResponseEntity.ok(questionService.checkForDelete(questionId));

	}

	@DeleteMapping("/delete/{questionId}")
	public ResponseEntity<?> deleteQuestion(@PathVariable("questionId") Integer questionId) {
		return ResponseEntity.ok(questionService.deleteQuestion(questionId));
	}

	@GetMapping("/get/{questionId}")
	public ResponseEntity<?> getQuestionById(@PathVariable("questionId") Integer questionId) {
		return ResponseEntity.ok(questionService.getQuestionById(questionId));

	}

	@PutMapping("/update")
	public ResponseEntity<?> updateQuestion(@RequestBody QuestionBean question) {
		return ResponseEntity.ok(questionService.updateQuestion(question));

	}

	@GetMapping("/question/{number}")
	public ResponseEntity<?> sendquestion(@PathVariable("number") Integer number) {
		return ResponseEntity.ok(questionService.sendQuestion(number));

	}

	@PostMapping("/excel")
	public ResponseEntity<?> fileread(@RequestParam("file") MultipartFile excel) {
		return ResponseEntity.ok(questionService.fileRead(excel));
	}

}

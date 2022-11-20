package com.Controller.UserController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Service.ResultService;

@CrossOrigin
@RestController
@RequestMapping("/result")
public class ResultController {

	@Autowired
	ResultService resultService;

	@GetMapping("/list/{userId}")
	public ResponseEntity<?> getResultsById(@PathVariable("userId") Integer userId) {
		return ResponseEntity.ok(resultService.getResultsById(userId));
	}

	@GetMapping("/get/{resultId}")
	public ResponseEntity<?> getResultById(@PathVariable("resultId") Integer resultId) {
		return ResponseEntity.ok(resultService.getResultById(resultId));
	}

	@GetMapping("/listresult/{userId}/{examId}")
	public ResponseEntity<?> getallquestionbyexamIdanduserId(@PathVariable("userId") Integer userId, @PathVariable("examId") Integer examId) {
		return ResponseEntity.ok(resultService.getAllQuestionByExamIdAndUserId(userId, examId));
	}

	@GetMapping("/listresult/{examId}")
	public ResponseEntity<?> getallresultbyexamId(@PathVariable("examId") Integer examId) {
		return ResponseEntity.ok(resultService.getAllResultByExamId(examId));

	}

}

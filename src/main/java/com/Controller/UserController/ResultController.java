package com.Controller.UserController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Service.ResultService;

@RestController
@RequestMapping("/result")
public class ResultController {

	@Autowired
	ResultService resultService;

	@GetMapping("/list/{userId}")
	public Object getResultsById(@PathVariable("userId") Integer userId) throws Exception {
		return ResponseEntity.ok(resultService.getResultsById(userId));
	}

	@GetMapping("/get/{resultId}")
	public Object getResultById(@PathVariable("resultId") Integer resultId) throws Exception {
		return ResponseEntity.ok(resultService.getResultById(resultId));
	}

	@GetMapping("/listresult/{userId}/{examId}")
	public Object getallquestionbyexamIdanduserId(@PathVariable("userId") Integer userId, @PathVariable("examId") Integer examId) throws Exception {
		return ResponseEntity.ok(resultService.getAllQuestionByExamIdAndUserId(userId, examId));
	}

	@GetMapping("/listresult/{examId}")
	public Object getallresultbyexamId(@PathVariable("examId") Integer examId) throws Exception {
		return ResponseEntity.ok(resultService.getAllResultByExamId(examId));

	}

}

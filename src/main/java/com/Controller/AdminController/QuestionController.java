package com.Controller.AdminController;

import java.io.IOException;
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

import com.Bean.ResponseBean;
import com.Entity.QuestionBean;
import com.Service.QuestionService;
import com.Service.SubjectFileService;

@RequestMapping("/que")
@RestController
public class QuestionController {

	@Autowired
	QuestionService questionService;

	@Autowired
	SubjectFileService subjectFileService;

	
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
	
	@GetMapping("/getImage/{questionId}")
	public ResponseEntity<?> getImageByQurstionId(@PathVariable("questionId") Integer questionId) {
		return ResponseEntity.ok(questionService.getQuestionImage(questionId));

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
	
	@PostMapping("/getImage")
	public ResponseEntity<?> getImage(@RequestBody String URL) throws IOException {
		byte[] Image = subjectFileService.getImage(URL);
		ResponseBean<byte[]> res = new ResponseBean<>();
		res.setApicode(200);
		res.setData(Image);
		return ResponseEntity.ok(res);
	}

}

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
	public Object addQuestions(@RequestBody List<QuestionBean> questions) throws Exception {
		return ResponseEntity.ok(questionService.addQuestions(questions));
	}
	
	@PostMapping("/single-add")
	public Object addQuestion(@RequestBody QuestionBean question) throws Exception {
		return ResponseEntity.ok(questionService.addQuestion(question));
	}

	@GetMapping("/list")
	public Object getAllQuestions() throws Exception {
		return ResponseEntity.ok(questionService.getAllQuestions());
	}

	@GetMapping("/child/{questionId}")
	public Object checkfordelete(@PathVariable("questionId") Integer questionId) throws Exception {
		return ResponseEntity.ok(questionService.checkForDelete(questionId));

	}

	@DeleteMapping("/delete/{questionId}")
	public Object deleteQuestion(@PathVariable("questionId") Integer questionId) throws Exception {
		return ResponseEntity.ok(questionService.deleteQuestion(questionId));
	}

	@GetMapping("/get/{questionId}")
	public Object getQuestionById(@PathVariable("questionId") Integer questionId) throws Exception {
		return ResponseEntity.ok(questionService.getQuestionById(questionId));

	}
	
	@GetMapping("/getImage/{questionId}")
	public Object getImageByQurstionId(@PathVariable("questionId") Integer questionId) throws Exception {
		return ResponseEntity.ok(questionService.getQuestionImage(questionId));

	}

	@PutMapping("/update")
	public Object updateQuestion(@RequestBody QuestionBean question) throws Exception {
		return ResponseEntity.ok(questionService.updateQuestion(question));

	}

	@GetMapping("/question/{number}")
	public Object sendquestion(@PathVariable("number") Integer number) throws Exception {
		return ResponseEntity.ok(questionService.sendQuestion(number));

	}

	@PostMapping("/excel")
	public Object fileread(@RequestParam("file") MultipartFile excel) throws Exception {
		return ResponseEntity.ok(questionService.fileRead(excel));
	}
	
	@PostMapping("/getImage")
	public Object getImage(@RequestBody String URL) throws Exception {
		byte[] Image = subjectFileService.getImage(URL);
		return new ResponseBean(Image,"", 200);
	}

}

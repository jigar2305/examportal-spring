package com.controller.admin;

import java.util.ArrayList;
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

import com.bean.AddquestionBean;
import com.bean.ExamMSubjectBean;
import com.bean.ResponseBean;
import com.bean.forms.CheckquestionanswerBean;
import com.bean.forms.ExamBean;
import com.bean.forms.ExamquestionBean;
import com.bean.forms.QuestionBean;
import com.bean.forms.ResultBean;
import com.repository.ExamRepository;
import com.repository.ExamquestionRepository;
import com.service.QuestionService;

@CrossOrigin
@RequestMapping("/eqc")
@RestController
public class ExamquestionController {

	@Autowired
	ExamquestionRepository examquestionRepo;

	@Autowired
	ExamRepository examRepo;

	@Autowired
	QuestionService questionService;

	@PostMapping("/add")
	public ResponseEntity<?> addequestions(@RequestBody AddquestionBean addquestion) {
		List<ExamquestionBean> examque = examquestionRepo.findByExam(addquestion.getExam());
		ResponseBean<List<ExamquestionBean>> res = new ResponseBean<>();
		if (examque.isEmpty()) {
			List<ExamquestionBean> equestions = questionService.randomquestion(addquestion);
			examquestionRepo.saveAll(equestions);
			res.setData(equestions);
			res.setMsg("added sussessfully");
			return ResponseEntity.status(HttpStatus.OK).body(res);
		} else {
			res.setData(examque);
			res.setMsg("this exam is already exist");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}
	}
	
	@PostMapping("/add/many")
	public ResponseEntity<?> addequestionsbymultiplesubject(@RequestBody ExamMSubjectBean addquestion) {
		List<ExamquestionBean> examque = examquestionRepo.findByExam(addquestion.getExam());
		ResponseBean<List<ExamquestionBean>> res = new ResponseBean<>();
		if (examque.isEmpty()) {
			List<ExamquestionBean> equestions = questionService.randomquestionbymultiplesubject(addquestion);
			res.setData(equestions);
			res.setMsg("added sussessfully");
			return ResponseEntity.status(HttpStatus.OK).body(res);
		} else {
			res.setData(examque);
			res.setMsg("this exam is already exist");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}
	}

	@GetMapping("/get/{examId}")
	public ResponseEntity<?> listequesrions(@PathVariable("examId") Integer examId) {
		Optional<ExamBean> exam = examRepo.findById(examId);
		if (exam.isEmpty()) {
			ResponseBean<Object> res = new ResponseBean<>();
			res.setData(examId);
			res.setMsg("data not found");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);

		} else {
			ResponseBean<List<ExamquestionBean>> res = new ResponseBean<>();
			List<ExamquestionBean> examquestion = examquestionRepo.findByExam(exam);
			res.setData(examquestion);
			res.setMsg("get successfully");
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}
	}
	@GetMapping("/getque/{examId}")
	public ResponseEntity<?> listequesrion(@PathVariable("examId") Integer examId) {
		Optional<ExamBean> exam = examRepo.findById(examId);
		if (exam.isEmpty()) {
			ResponseBean<Object> res = new ResponseBean<>();
			res.setData(examId);
			res.setMsg("data not found");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);

		} else {
			ResponseBean<List<QuestionBean>> res = new ResponseBean<>();
			List<ExamquestionBean> examquestion = examquestionRepo.findByExam(exam);
			List<QuestionBean> questions = new ArrayList<>(); 
			for (int i = 0; i < examquestion.size(); i++) {
				questions.add(examquestion.get(i).getQuestion());
			}
			res.setData(questions);
			res.setMsg("get successfully");
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}
	}

	@PostMapping("/checkanswer")
	public ResponseEntity<?> checkanswer(@RequestBody CheckquestionanswerBean questions){
		ResultBean result =  questionService.checkanswer(questions);
		ResponseBean<ResultBean> res = new ResponseBean<>();
		res.setData(result);
		res.setMsg(" your result ");
		return ResponseEntity.status(HttpStatus.OK).body(res);
		
	}
	
	
	
	@DeleteMapping("/delete/{examId}")
	public ResponseEntity<?> deleteequesrions(@PathVariable("examId") Integer examId) {
		Optional<ExamBean> exam = examRepo.findById(examId);
		ResponseBean<Object> res = new ResponseBean<>();
		if (exam.isEmpty()) {
			res.setData(examId);
			res.setMsg("data not found");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		} else {
			examquestionRepo.deleteAllByExam(exam);
			res.setData(examId);
			res.setMsg("get successfully");
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}
	}

}

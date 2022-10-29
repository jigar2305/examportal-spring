package com.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bean.ResponseBean;
import com.bean.UserBean;
import com.bean.forms.ExamBean;
import com.bean.forms.ResultBean;
import com.bean.forms.UserquestionanswerBean;
import com.repository.ExamRepository;
import com.repository.ResultRepository;
import com.repository.UserRepository;
import com.repository.UserquestionanswerRepository;

@CrossOrigin
@RestController
@RequestMapping("/result")
public class ResultController {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	ResultRepository resultRepo;
	
	@Autowired
	UserquestionanswerRepository userquestionanswerRepo;
	
	@Autowired
	ExamRepository examRepo;
	
	@GetMapping("/list/{userId}")
	public ResponseEntity<?> list(@PathVariable("userId") Integer userId) {
		UserBean user  = userRepo.findByUserId(userId);
		List<ResultBean> results = resultRepo.findByUser(user);
		ResponseBean<List<ResultBean>> res = new ResponseBean<>();
		res.setData(results);
		res.setMsg("sussessfully");
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(res);
	}
	
	@GetMapping("/get/{resultId}")
	public ResponseEntity<?> getbyid(@PathVariable("resultId") Integer resultId) {
		ResultBean result = resultRepo.findByResultId(resultId);
		if(result == null) {
			ResponseBean<Integer> res = new ResponseBean<>();
			res.setData(resultId);
			res.setMsg("Data not found");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}else {
			
		ResponseBean<ResultBean> res = new ResponseBean<>();
		res.setData(result);
		res.setMsg("sussessfully");
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(res);
		}
	}
	
	@GetMapping("/listresult/{userId}/{examId}")
	public ResponseEntity<?> getallquestionbyexamIdanduserId(@PathVariable("userId") Integer userId,@PathVariable("examId") Integer examId) {
		List<UserquestionanswerBean> userquestionanswerBean = userquestionanswerRepo.findByExamAndUser(examId,userId);
		ResponseBean<List<UserquestionanswerBean>> res = new ResponseBean<>();
		res.setData(userquestionanswerBean);
		res.setMsg("sussessfully");
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(res);
	}
	@GetMapping("/listresult/{examId}")
	public ResponseEntity<?> getallresultbyexamId(@PathVariable("examId") Integer examId) {
		ExamBean exam = examRepo.findByExamId(examId);
		if(exam == null) {
			ResponseBean<ExamBean> res = new ResponseBean<>();
			res.setData(exam);
			res.setMsg("sussessfully");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}else {			
			List<ResultBean> results = resultRepo.findByExam(exam);
			ResponseBean<List<ResultBean>> res = new ResponseBean<>();
			res.setData(results);
			res.setMsg("sussessfully");
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}
	}

}

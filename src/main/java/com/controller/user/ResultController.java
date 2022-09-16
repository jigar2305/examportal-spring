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
import com.criteria.CriteriaApi;
import com.criteria.CriteriaRepository;
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
	@GetMapping("/listresult/{userId}/{examId}")
	public ResponseEntity<?> getallquestionbyexamId(@PathVariable("userId") Integer userId,@PathVariable("examId") Integer examId) {
		UserBean user  = userRepo.findByUserId(userId);
		ExamBean exam =  examRepo.findByExamId(examId);
//		List<ResultBean> results = resultRepo.findByUser(user);
		CriteriaApi criteriaRepo = new CriteriaApi();
		List<UserquestionanswerBean> userquestionanswerBean = criteriaRepo.getquestion(user,exam);
//		Map<String, List<Object>> results1 = new HashMap();
//		results1.put("result",results);
		ResponseBean<List<UserquestionanswerBean>> res = new ResponseBean<>();
		res.setData(userquestionanswerBean);
		res.setMsg("sussessfully");
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(res);
	}

}

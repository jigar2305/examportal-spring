package com.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bean.EnroleexamBean;
import com.bean.ResponseBean;
import com.bean.UserBean;
import com.bean.forms.ExamBean;
import com.repository.ExamRepository;
import com.repository.ResultRepository;
import com.repository.UserRepository;

@CrossOrigin
@RequestMapping("/er")
@RestController
public class EnroleExamController {

	@Autowired
	ExamRepository examRepo;

	@Autowired
	UserRepository userRepo;
	
	

	@PostMapping("/add")
	public ResponseEntity<?> add(@RequestBody EnroleexamBean enroleexam) {
		ExamBean exam = examRepo.findByExamId(enroleexam.getExamId());
		ExamBean exam2 = new ExamBean();
		if (exam != null) {
			for (int i = 0; i < enroleexam.getUserId().size(); i++) {
				UserBean user = userRepo.findByUserId(enroleexam.getUserId().get(i));
				if (user != null) {
					exam.getUsers().add(user);
					exam2 = examRepo.save(exam);
				}
			}
		}
		if(exam2 == null) {
			ResponseBean<ExamBean> res = new ResponseBean<>();
			res.setData(exam);
			res.setMsg("Technical error ouccured");
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}else {			
			ResponseBean<ExamBean> res = new ResponseBean<>();
			res.setData(exam2);
			res.setMsg("exam send to user");
			return ResponseEntity.status(HttpStatus.OK).body(res);
		}
	}

}

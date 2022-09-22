package com.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bean.EnroleexamBean;
import com.bean.ResponseBean;
import com.bean.UserBean;
import com.bean.forms.ExamBean;
import com.repository.ExamRepository;
import com.repository.UserRepository;

@RequestMapping("/er")
@RestController
public class EnroleExamController {

	@Autowired
	ExamRepository examRepo;

	@Autowired
	UserRepository userRepo;

	@PostMapping("/add")
	public ResponseEntity<?> add(@RequestBody EnroleexamBean enroleexam) {
		System.out.println(enroleexam.getExamId());
		ResponseBean<ExamBean> res = new ResponseBean<>();
		ExamBean exam = examRepo.findByExamId(enroleexam.getExamId());
		if (exam != null) {
			for (int i = 0; i < enroleexam.getUserId().size();i++) {
				UserBean user = userRepo.findByUserId(enroleexam.getUserId().get(i));
				if (user != null) {
					exam.getUsers().add(user);
					 examRepo.save(exam);
				}
			}
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(res);
	}
}

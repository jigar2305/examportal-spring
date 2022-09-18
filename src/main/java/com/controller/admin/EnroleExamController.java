package com.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bean.EnroleexamBean;
import com.bean.forms.ExamBean;
import com.repository.ExamRepository;


@RequestMapping("/er")
@RestController
public class EnroleExamController {
	
	
	@Autowired
	ExamRepository examRepo;
	
	@PostMapping("/add")
	public void add(@RequestBody EnroleexamBean enroleexam) {
		System.out.println("hello");
		ExamBean exam =enroleexam.getExam(); 
//		exam.setUserId(enroleexam.getUserId());
//		System.out.println(exam.getUserId());
		examRepo.save(exam);
	}

}

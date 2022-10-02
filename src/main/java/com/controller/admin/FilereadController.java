package com.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bean.ResponseBean;
import com.bean.forms.QuestionBean;
import com.service.QuestionService;

@CrossOrigin
@RestController
@RequestMapping("file")
public class FilereadController {

	@Autowired
	QuestionService questionService;

	@PostMapping("excel")
	public ResponseEntity<?> fileread(@RequestParam("file") MultipartFile excel) {

		List<QuestionBean> questions;
		try {
			questions = questionService.addquestion(excel);
			ResponseBean<List<QuestionBean>> res = new ResponseBean<>();
			res.setData(questions);
			res.setMsg("question added successfully..");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(res);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResponseBean<Object> res = new ResponseBean<>();
		res.setData(null);
		res.setMsg("something went wrong");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);

	}

}

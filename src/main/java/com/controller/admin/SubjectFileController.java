package com.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bean.forms.SubjectBean;
import com.service.SubjectFileService;

@RestController
@RequestMapping("/file")
public class SubjectFileController {
	
	@Autowired
	SubjectFileService subjectFileService;
	
	@PostMapping("/get")
	public void getallfile(@RequestBody List<SubjectBean> subjectBeans) {
		subjectFileService.getsubjectfiles(subjectBeans);
	}

}

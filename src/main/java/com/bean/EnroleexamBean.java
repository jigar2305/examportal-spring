package com.bean;


import java.util.List;

import com.bean.forms.ExamBean;

import lombok.Data;

@Data
public class EnroleexamBean {
	
	private ExamBean exam;
	
	private List<Integer> userId;

}

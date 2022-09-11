package com.bean.forms;

import java.util.List;

import lombok.Data;

@Data
public class CheckquestionanswerBean {
	
	String email;
	List<QuestionanswerBean> questions;
	ExamBean exam;
}

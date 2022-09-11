package com.bean.forms;


import lombok.Data;

@Data
public class QuestionanswerBean {


	private Integer questionId;
	
	private String question;
	private String a;
	private String b;
	private String c;
	private String d;
	private String correctAnswer;

	private String selected;
	
}

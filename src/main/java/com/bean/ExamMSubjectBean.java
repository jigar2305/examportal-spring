package com.bean;

import java.util.List;

import com.bean.forms.ExamBean;

import lombok.Data;

@Data
public class ExamMSubjectBean {
	
	private ExamBean exam;
	
	private List<SNNLBean>  subjects;

}

package com.bean;

import java.util.List;

import com.bean.forms.ExamBean;

import lombok.Data;

@Data
public class ExamMSubjectBean {
//
//	private ExamBean exam;
	private String examName;

	private Integer time;

	private Boolean isshow;

	private String level;

	private List<SNNLBean> subjects;

}

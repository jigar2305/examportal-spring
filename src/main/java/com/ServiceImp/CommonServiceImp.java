package com.ServiceImp;

import org.springframework.stereotype.Service;

import com.Entity.ExamBean;
import com.Entity.ExamquestionBean;
import com.Entity.QuestionBean;
import com.Service.CommonService;

@Service
public class CommonServiceImp implements CommonService {

	@Override
	public ExamquestionBean setExamQue(QuestionBean question, ExamBean exam) throws Exception {
		ExamquestionBean examquestionBean = new ExamquestionBean();
		examquestionBean.setQuestion(question.getQuestion());
		examquestionBean.setA(question.getA());
		examquestionBean.setB(question.getB());
		examquestionBean.setC(question.getC());
		examquestionBean.setD(question.getD());
		examquestionBean.setCorrectAnswer(question.getCorrectAnswer());
		examquestionBean.setUrl(question.getUrl());
		examquestionBean.setLevel(question.getLevel());
		examquestionBean.setExam(exam);
		return examquestionBean;
	}

}

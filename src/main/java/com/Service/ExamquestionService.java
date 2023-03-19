package com.Service;

import org.springframework.stereotype.Service;

import com.Bean.CheckquestionanswerBean;

@Service
public interface ExamquestionService {

	public Object getQuestions(Integer examId) throws Exception;

	public Object getExamQuestion(Integer examId) throws Exception;

	public Object getExamQuestionWithImage(Integer examId) throws Exception;

	public Object checkanswer(CheckquestionanswerBean questions) throws Exception;

	public Object deleteExamQuestios(Integer examId) throws Exception;

}

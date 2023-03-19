package com.Service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.Bean.EnroleexamBean;
import com.Bean.ExamMSubjectBean;

@Service
public interface ExamService {

	@Transactional
	public Object addExamAndQuestion(ExamMSubjectBean examsubject) throws Exception;

	public Object listExams() throws Exception;

	public Object getExamByUserId(Integer userId) throws Exception;

	public Object deleteExam(Integer examId) throws Exception;

	public Object isEnroll(Integer examId) throws Exception;

	public Object getQuestionById(Integer examId) throws Exception;

	public Object getStatusOfExam(Integer examId) throws Exception;

	public Object enrollExam(@RequestBody EnroleexamBean enroleexam) throws Exception;

}

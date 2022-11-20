package com.Service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.Bean.EnroleexamBean;
import com.Bean.ExamMSubjectBean;
import com.Bean.ResponseBean;

@Service
public interface ExamService {

	@Transactional
	public ResponseBean<?> addExamAndQuestion(ExamMSubjectBean examsubject);

	public ResponseBean<?> listExams();

	public ResponseBean<?> getExamByUserId(Integer userId);

	public ResponseBean<?> deleteExam(Integer examId);

	public ResponseBean<?> isEnroll(Integer examId);

	public ResponseBean<?> getQuestionById(Integer examId);

	public ResponseBean<?> getStatusOfExam(Integer examId);

	public ResponseBean<?> enrollExam(@RequestBody EnroleexamBean enroleexam);

}

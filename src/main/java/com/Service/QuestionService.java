package com.Service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Bean.CheckquestionanswerBean;
import com.Bean.ExamMSubjectBean;
import com.Entity.ExamquestionBean;
import com.Entity.QuestionBean;
import com.Entity.ResultBean;

@Service
public interface QuestionService {

	List<ExamquestionBean> addRendomQuestionByManySubject(ExamMSubjectBean addquestion)  throws Exception;

	ResultBean checkAnswer(CheckquestionanswerBean questions)  throws Exception;

	List<QuestionBean> addquestion(MultipartFile excel) throws IOException, NullPointerException;

	public Object addQuestions(List<QuestionBean> questions)  throws Exception;
	
	public Object addQuestion(QuestionBean question)  throws Exception;

	public Object getAllQuestions()  throws Exception;

	public Object checkForDelete(Integer questionId)  throws Exception;

	public Object deleteQuestion(Integer questionId)  throws Exception;

	public Object getQuestionById(Integer questionId)  throws Exception;

	public Object updateQuestion(QuestionBean question)  throws Exception;

	public Object sendQuestion(Integer number)  throws Exception;
	
	public Object fileRead(MultipartFile excel)  throws Exception;

	public Object getQuestionImage(Integer questionId)  throws Exception;

}

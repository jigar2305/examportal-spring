package com.Service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Bean.CheckquestionanswerBean;
import com.Bean.ExamMSubjectBean;
import com.Bean.ResponseBean;
import com.Entity.ExamquestionBean;
import com.Entity.QuestionBean;
import com.Entity.ResultBean;

@Service
public interface QuestionService {

	List<ExamquestionBean> addRendomQuestionByManySubject(ExamMSubjectBean addquestion);

	ResultBean checkAnswer(CheckquestionanswerBean questions);

	List<QuestionBean> addquestion(MultipartFile excel) throws IOException, NullPointerException;

	public ResponseBean<?> addQuestions(List<QuestionBean> questions);

	public ResponseBean<?> getAllQuestions();

	public ResponseBean<?> checkForDelete(Integer questionId);

	public ResponseBean<?> deleteQuestion(Integer questionId);

	public ResponseBean<?> getQuestionById(Integer questionId);

	public ResponseBean<?> updateQuestion(QuestionBean question);

	public ResponseBean<?> sendQuestion(Integer number);
	
	public ResponseBean<?> fileRead(MultipartFile excel);

}

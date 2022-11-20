package com.Service;

import org.springframework.stereotype.Service;

import com.Bean.CheckquestionanswerBean;
import com.Bean.ResponseBean;

@Service
public interface ExamquestionService {

	public ResponseBean<?> getQuestions(Integer examId);

	public ResponseBean<?> getExamQuestion(Integer examId);

	public ResponseBean<?> checkanswer(CheckquestionanswerBean questions);

	public ResponseBean<?> deleteExamQuestios(Integer examId);

}

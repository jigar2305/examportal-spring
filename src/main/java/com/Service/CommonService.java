package com.Service;

import org.springframework.stereotype.Service;

import com.Entity.ExamBean;
import com.Entity.ExamquestionBean;
import com.Entity.QuestionBean;

@Service
public interface CommonService {
	public ExamquestionBean setExamQue(QuestionBean question,ExamBean exam);
}

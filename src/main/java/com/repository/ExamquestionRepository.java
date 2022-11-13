package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bean.forms.ExamBean;
import com.bean.forms.ExamquestionBean;
import com.bean.forms.QuestionBean;

@Repository
public interface ExamquestionRepository extends JpaRepository<ExamquestionBean, Integer>{
	List<ExamquestionBean> findByExam(ExamBean examBean);
	List<ExamquestionBean> findByExam(Optional<ExamBean> exam);
	 void deleteAllByExam(Optional<ExamBean> exam);
	 List<ExamquestionBean> findByQuestion(QuestionBean question);
	 void deleteAllByExam(ExamBean exam);
}

package com.Repositoy;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Entity.ExamBean;
import com.Entity.ExamquestionBean;
import com.Entity.QuestionBean;


@Repository
public interface ExamquestionRepository extends JpaRepository<ExamquestionBean, Integer>{
	List<ExamquestionBean> findByExam(ExamBean examBean);
	List<ExamquestionBean> findByExam(Optional<ExamBean> exam);
	 void deleteAllByExam(Optional<ExamBean> exam);
	 List<ExamquestionBean> findByQuestion(QuestionBean question);
	 void deleteAllByExam(ExamBean exam);
}

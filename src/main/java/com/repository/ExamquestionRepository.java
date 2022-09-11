package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bean.forms.ExamBean;
import com.bean.forms.ExamquestionBean;

@Repository
public interface ExamquestionRepository extends CrudRepository<ExamquestionBean, Integer>{
	List<ExamquestionBean> findByExam(ExamBean examBean);
	List<ExamquestionBean> findByExam(Optional<ExamBean> exam);
	 void deleteAllByExam(Optional<ExamBean> exam);

}

package com.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bean.forms.ExamBean;
@Repository
public interface ExamRepository extends CrudRepository<ExamBean, Integer>{
	ExamBean findByExamName(String examName);
	ExamBean findByExamId(Integer examId);

}

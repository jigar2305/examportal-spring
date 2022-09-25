package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bean.UserBean;
import com.bean.forms.ExamBean;
@Repository
public interface ExamRepository extends CrudRepository<ExamBean, Integer>{
	ExamBean findByExamName(String examName);
	ExamBean findByExamId(Integer examId);
	List<ExamBean> findByUsers(UserBean user);

}

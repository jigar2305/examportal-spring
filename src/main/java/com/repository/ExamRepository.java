package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.bean.UserBean;
import com.bean.forms.ExamBean;
@Repository
public interface ExamRepository extends JpaRepository<ExamBean, Integer>{
	ExamBean findByExamName(String examName);
	ExamBean findByExamId(Integer examId);
	List<ExamBean> findByUsers(UserBean user);

}

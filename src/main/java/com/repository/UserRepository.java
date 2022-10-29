package com.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bean.UserBean;
import com.bean.forms.ExamBean;

public interface UserRepository extends CrudRepository<UserBean, Integer> {
	UserBean findByEmail(String email);
	UserBean findByUserId(Integer userId);
	List<UserBean> findByExams(ExamBean exams);

}

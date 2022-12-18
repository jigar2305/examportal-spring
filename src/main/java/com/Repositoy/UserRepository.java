package com.Repositoy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Entity.ExamBean;
import com.Entity.UserBean;


public interface UserRepository extends JpaRepository<UserBean, Integer> {
	UserBean findByEmail(String email);
	UserBean findByUserId(Integer userId);
	List<UserBean> findByExams(ExamBean exams);
    UserBean findByAuthToken(String authToken);
    UserBean findByAuthTokenAndEmail(String authToken,String email);
    
}

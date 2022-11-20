package com.Repositoy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Entity.UserquestionanswerBean;


public interface UserquestionanswerRepository extends JpaRepository<UserquestionanswerBean, Integer> {

	@Query(value = "select * FROM Userquestionanswer u WHERE u.exam_Id = ?1 and u.user_Id = ?2", nativeQuery = true)
	List<UserquestionanswerBean> findByExamAndUser(Integer exam_Id, Integer user_Id);
}

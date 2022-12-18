package com.Repositoy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Entity.ExamBean;
import com.Entity.UserBean;

@Repository
public interface ExamRepository extends JpaRepository<ExamBean, Integer>{
	ExamBean findByExamName(String examName);
	ExamBean findByExamId(Integer examId);
	List<ExamBean> findByUsers(UserBean user);
	
	@Modifying
	@Query(value = "delete FROM enroleexam WHERE exam_id = ?1 and user_id = ?2", nativeQuery = true)
	void deleteenroleexam(Integer exam_Id, Integer user_Id);
	
	@Modifying
	@Query(value = "delete FROM enroleexam WHERE exam_id = ?1", nativeQuery = true)
	void deleteenroleexamAll(Integer exam_Id);
}

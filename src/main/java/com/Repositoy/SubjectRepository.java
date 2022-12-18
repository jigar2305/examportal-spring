package com.Repositoy;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.Entity.CourseBean;
import com.Entity.SubjectBean;
import com.Entity.UserBean;

@Repository
public interface SubjectRepository extends CrudRepository<SubjectBean, Integer>{
	SubjectBean findBySubjectName(String subjectName);
	List<SubjectBean> findByCourse(Optional<CourseBean> courseBean);
	SubjectBean findBySubjectId(Integer subjectId);
	List<SubjectBean> findByUsers(UserBean users);
	
	@Modifying
	@Query(value = "delete  FROM enrolesubject e WHERE e.subject_id = ?1 and e.user_id = ?2", nativeQuery = true)
	void deleteenrolesubject(Integer subject_id, Integer user_Id);
	
	@Modifying
	@Query(value = "delete FROM enrolesubject e WHERE e.subject_id = ?1", nativeQuery = true)
	void deleteenrolesubjectAll(Integer subject_id);
	
	@Modifying
	@Query(value = "delete FROM enrolesubject e WHERE e.user_id = ?1", nativeQuery = true)
	void deleteenrolesubjectuser(Integer user_id);
}

package com.Repositoy;

import java.util.List;
import java.util.Optional;

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
}

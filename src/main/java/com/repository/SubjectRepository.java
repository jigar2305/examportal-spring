package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bean.forms.CourseBean;
import com.bean.forms.SubjectBean;

@Repository
public interface SubjectRepository extends CrudRepository<SubjectBean, Integer>{
	SubjectBean findBySubjectName(String subjectName);
	List<SubjectBean> findByCourse(Optional<CourseBean> courseBean);
}

package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bean.forms.SubjectBean;
import com.bean.forms.SubjectFileBean;

public interface SubjectFileRepository extends JpaRepository<SubjectFileBean, Integer>{
	SubjectFileBean findByFileName(String fileName);
	List<SubjectFileBean> findBySubject(SubjectBean subjectBean);
	List<SubjectFileBean> findBySubject(Optional<SubjectBean> subjectBean);
	SubjectFileBean findBySubjectfileId(Integer subjectfileId);
	SubjectFileBean findByFileNameAndSubject(String fileName,SubjectBean subject);

}

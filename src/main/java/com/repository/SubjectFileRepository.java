package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bean.forms.SubjectBean;
import com.bean.forms.SubjectFileBean;

public interface SubjectFileRepository extends JpaRepository<SubjectFileBean, Integer>{
	SubjectFileBean findByFileName(String fileName);
	List<SubjectFileBean> findBySubject(SubjectBean subject);

}

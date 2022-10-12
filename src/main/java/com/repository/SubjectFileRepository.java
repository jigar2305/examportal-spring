package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bean.forms.SubjectFileBean;

public interface SubjectFileRepository extends JpaRepository<SubjectFileBean, Integer>{
	SubjectFileBean findByFileName(String fileName);

}

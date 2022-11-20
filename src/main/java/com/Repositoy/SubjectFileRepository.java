package com.Repositoy;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Entity.SubjectBean;
import com.Entity.SubjectFileBean;

@Repository
public interface SubjectFileRepository extends JpaRepository<SubjectFileBean, Integer>{
	SubjectFileBean findByFileName(String fileName);
	List<SubjectFileBean> findBySubject(SubjectBean subjectBean);
	List<SubjectFileBean> findBySubject(Optional<SubjectBean> subjectBean);
	SubjectFileBean findBySubjectfileId(Integer subjectfileId);
	SubjectFileBean findByFileNameAndSubject(String fileName,SubjectBean subject);

}

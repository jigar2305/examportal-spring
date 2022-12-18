package com.Repositoy;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.Entity.QuestionBean;
import com.Entity.SubjectBean;

@Repository
public interface QuestionRepository extends CrudRepository<QuestionBean, Integer>{
	QuestionBean findByQuestion(String question);
	List<QuestionBean> findBySubject(SubjectBean subjectBean);
	QuestionBean findByQuestionId(Integer questionId);
	List<QuestionBean> findBySubject(Optional<SubjectBean> subject);
	List<QuestionBean> findBySubjectAndLevel(SubjectBean subject,String level);
}

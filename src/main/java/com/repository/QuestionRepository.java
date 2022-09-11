package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bean.forms.QuestionBean;
import com.bean.forms.SubjectBean;

@Repository
public interface QuestionRepository extends CrudRepository<QuestionBean, Integer>{
	QuestionBean findByQuestion(String question);
	List<QuestionBean> findBySubject(Optional<SubjectBean> subject);
	QuestionBean findByQuestionId(Integer questionId);
	

}

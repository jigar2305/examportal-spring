package com.repository;

import org.springframework.data.repository.CrudRepository;


import com.bean.forms.UserquestionanswerBean;
import com.criteria.CriteriaRepository;

public interface UserquestionanswerRepository extends CrudRepository<UserquestionanswerBean, Integer> {

}

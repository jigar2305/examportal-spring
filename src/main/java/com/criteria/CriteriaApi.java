package com.criteria;

import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;

import com.bean.UserBean;
import com.bean.forms.ExamBean;
import com.bean.forms.UserquestionanswerBean;

public class CriteriaApi implements CriteriaRepository{

	@Autowired
	EntityManager entityManager;

	@Override
	public List<UserquestionanswerBean> getquestion(UserBean user, ExamBean exam) {
		System.out.println(entityManager);
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<UserquestionanswerBean> criteriaQuery = criteriaBuilder.createQuery(UserquestionanswerBean.class);
		
		Root<UserquestionanswerBean> questionRoot = criteriaQuery.from(UserquestionanswerBean.class);

        Predicate userpredicate=criteriaBuilder.equal(questionRoot.get("user"),user);
        Predicate exampredicate=criteriaBuilder.equal(questionRoot.get("exam"),exam);
	

        criteriaQuery.where(userpredicate,exampredicate);

		TypedQuery<UserquestionanswerBean> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}


}

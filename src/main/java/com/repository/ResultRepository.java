package com.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bean.UserBean;
import com.bean.forms.ExamBean;
import com.bean.forms.ResultBean;

@Repository
public interface ResultRepository extends CrudRepository<ResultBean, Integer>{
	
	List<ResultBean> findByUser(UserBean user);
	ResultBean findByResultId(Integer resultId);
	ResultBean findByExamAndUser(ExamBean exam,UserBean user);

}

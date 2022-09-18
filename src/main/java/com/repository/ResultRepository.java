package com.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bean.UserBean;
import com.bean.forms.ResultBean;

public interface ResultRepository extends CrudRepository<ResultBean, Integer>{
	
	List<ResultBean> findByUser(UserBean user);
	ResultBean findByResultId(Integer resultId);

}

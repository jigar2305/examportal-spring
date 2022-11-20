package com.Repositoy;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.Entity.ExamBean;
import com.Entity.ResultBean;
import com.Entity.UserBean;

@Repository
public interface ResultRepository extends CrudRepository<ResultBean, Integer>{
	
	List<ResultBean> findByUser(UserBean user);
	ResultBean findByResultId(Integer resultId);
	ResultBean findByExamAndUser(ExamBean exam,UserBean user);
	List<ResultBean> findByExam(ExamBean exam);
	void deleteAllByExam(ExamBean examBean);
}

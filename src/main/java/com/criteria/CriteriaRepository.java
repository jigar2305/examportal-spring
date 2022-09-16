package com.criteria;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bean.UserBean;
import com.bean.forms.ExamBean;
import com.bean.forms.UserquestionanswerBean;

@Repository
public interface CriteriaRepository {
	List<UserquestionanswerBean> getquestion(UserBean user,ExamBean exam);

}

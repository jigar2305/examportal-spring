package com.Service;

import org.springframework.stereotype.Service;

import com.Bean.LoginBean;
import com.Bean.forgotpasswordBean;
import com.Entity.UserBean;

@Service
public interface SessionService {
	Object signup(UserBean user) throws Exception;

	Object login(LoginBean login) throws Exception;

	Object sendotp(LoginBean login) throws Exception;

	Object forgot(forgotpasswordBean forgotpassword) throws Exception;

	Object updatepassword(LoginBean login) throws Exception;

	void logout(Integer userId) throws Exception;
}

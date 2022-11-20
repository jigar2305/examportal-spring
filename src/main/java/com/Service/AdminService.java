package com.Service;

import org.springframework.stereotype.Service;

import com.Bean.ResponseBean;

@Service
public interface AdminService {

	ResponseBean<?> listUser();

	ResponseBean<?> deleteUser(Integer userID);

	ResponseBean<?> checkForDelete(Integer userId);

	ResponseBean<?> isActive(Integer userId);

	ResponseBean<?> findUserById(Integer userId);

}

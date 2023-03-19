package com.Service;

import org.springframework.stereotype.Service;

@Service
public interface AdminService {

	Object listUser() throws Exception;

	Object deleteUser(Integer userID) throws Exception;

	Object checkForDelete(Integer userId) throws Exception;

	Object isActive(Integer userId) throws Exception;

	Object findUserById(Integer userId) throws Exception;

}

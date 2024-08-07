package com.seed;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.bean.RoleBean;
import com.bean.UserBean;
//import com.bean.RoleBean;
import com.repository.RoleRepository;
import com.repository.UserRepository;

@Component
public class Roleseed {
	@Autowired
	RoleRepository roleRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	BCryptPasswordEncoder bcrypt;


	@PostConstruct
	void createroles() {
		RoleBean role = roleRepo.findByRoleName("admin");
		if (role == null) {
			RoleBean role1 = new RoleBean();
			RoleBean role2 = new RoleBean();
			role1.setRoleName("admin");
			role2.setRoleName("student");
			roleRepo.save(role1);
			roleRepo.save(role2);
		}
		UserBean user = userRepo.findByEmail("admin@gmail.com");
		if (user == null) {
			RoleBean role2 = roleRepo.findByRoleName("admin");
			UserBean userBean = new UserBean();
			userBean.setRole(role2);
			userBean.setEmail("admin@gmail.com");
			String encpassword = bcrypt.encode("admin@123");
			userBean.setPassword(encpassword);
			userBean.setFirstName("admin");
			userBean.setLastName("super");
			userBean.setActive(true);
			userRepo.save(userBean);

		}
	}
	

}

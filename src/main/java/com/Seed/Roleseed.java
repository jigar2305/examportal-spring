package com.Seed;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.Entity.RoleBean;
import com.Entity.UserBean;
import com.Repositoy.RoleRepository;
import com.Repositoy.UserRepository;

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
			roleRepo.save(new RoleBean("student"));
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

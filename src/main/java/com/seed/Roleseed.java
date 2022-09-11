package com.seed;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//import com.bean.RoleBean;
import com.repository.RoleRepository;

@Component
public class Roleseed {
	@Autowired
	RoleRepository roleRepo;
	
	@PostConstruct 
	void createroles() {
//		RoleBean role = roleRepo.findByRoleName("admin");
//		if(role==null) {
//			RoleBean role1 = new RoleBean();
//			RoleBean role2 = new RoleBean();
//			role1.setRoleName("admin");
//			role2.setRoleName("student");
//			roleRepo.save(role1);
//			roleRepo.save(role2);
//		}
	}
	

}

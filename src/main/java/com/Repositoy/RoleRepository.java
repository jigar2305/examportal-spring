package com.Repositoy;

import org.springframework.data.repository.CrudRepository;

import com.Entity.RoleBean;


public interface RoleRepository extends CrudRepository<RoleBean, Integer> {
	RoleBean findByRoleName(String roleName);
}

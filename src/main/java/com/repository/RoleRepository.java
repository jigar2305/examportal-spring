package com.repository;

import org.springframework.data.repository.CrudRepository;

import com.bean.RoleBean;

public interface RoleRepository extends CrudRepository<RoleBean, Integer> {
	RoleBean findByRoleName(String roleName);
}

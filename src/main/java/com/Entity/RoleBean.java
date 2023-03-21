package com.Entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Table(name = "role")
@Entity
public class RoleBean {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer roleId;

	private String roleName;
	
	public RoleBean() {
	}

	public RoleBean(String roleName) {
		this.roleName = roleName;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "role")
	Set<UserBean> users;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Set<UserBean> getUsers() {
		return users;
	}

	public void setUsers(Set<UserBean> users) {
		this.users = users;
	}

}

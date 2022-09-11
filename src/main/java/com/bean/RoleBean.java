package com.bean;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Table(name = "role")
@Entity
public class RoleBean {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer roleId;
	
	private String roleName;
	
	@JsonIgnore
	@OneToMany(mappedBy = "role")
	Set<UserBean> users; 

}

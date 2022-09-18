package com.bean;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bean.forms.ResultBean;
import com.bean.forms.UserquestionanswerBean;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Table(name = "users")
@Entity
public class UserBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer userId;
	
	private String firstName;

	private String lastName;
	
	private String email;
	
	private String password;

	private String gender;
	
	private Boolean active;
	
	private Integer otp;
	
	@ManyToOne()
	@JoinColumn(name = "roleId",nullable = false)
	private RoleBean role;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	Set<ResultBean> results;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	Set<UserquestionanswerBean> userquestionanswers; 

}

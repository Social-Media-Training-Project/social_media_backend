package com.smb.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class UserEntity {
	@Id
	private String id;
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String role;
//	private List<String> following = new ArrayList<>();
//	private List<String> follower = new ArrayList<>();
	
	
	
	public String getFirstName() {
		return firstName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public UserEntity(String id, String firstName, String lastName, String userName, String password) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
	}
	public UserEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}

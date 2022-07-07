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
	private List<String> gods = new ArrayList<>();
	private List<String> fans = new ArrayList<>();
	private	List<String> userFeed = new ArrayList<>();

	
	
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
	
	public List<String> getGods() {
		return gods;
	}
	public void setGods(List<String> gods) {
		this.gods = gods;
	}
	public List<String> getFans() {
		return fans;
	}
	public void setFans(List<String> fans) {
		this.fans = fans;
	}

	public List<String> getUserFeed() {
		return userFeed;
	}

	public void setUserFeed(List<String> userFeed) {
		this.userFeed = userFeed;
	}

	public UserEntity(String id, String firstName, String lastName, String userName, String password, String role, List<String> gods, List<String> fans, List<String> userfeed) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.role = role;
		this.gods = gods;
		this.fans = fans;
		this.userFeed = userFeed;
	}

	public UserEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

}

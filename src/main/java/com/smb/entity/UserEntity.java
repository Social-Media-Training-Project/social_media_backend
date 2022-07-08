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
	private String dob;
	private String city;
	private String description;
	private String profileImageUrl;
	private String createdAt;
	private List<String> hobbies;
	private List<String> gods = new ArrayList<>();
	private List<String> fans = new ArrayList<>();
	private	List<String> userFeed = new ArrayList<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
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

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public List<String> getHobbies() {
		return hobbies;
	}

	public void setHobbies(List<String> hobbies) {
		this.hobbies = hobbies;
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

	public UserEntity(String id, String firstName, String lastName, String userName, String password, String dob, String city, String description, String profileImageUrl, String createdAt, List<String> hobbies, List<String> gods, List<String> fans, List<String> userFeed) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.dob = dob;
		this.city = city;
		this.description = description;
		this.profileImageUrl = profileImageUrl;
		this.createdAt = createdAt;
		this.hobbies = hobbies;
		this.gods = gods;
		this.fans = fans;
		this.userFeed = userFeed;
	}

	public UserEntity() {
		super();
	}
}
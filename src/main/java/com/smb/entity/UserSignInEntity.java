package com.smb.entity;

public class UserSignInEntity {
    private String username;
    private String password;

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public UserSignInEntity(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public UserSignInEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    

}

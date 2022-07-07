package com.smb.entity;


public class AuthEntity {
    private UserEntity user;
    private String token;
    
    public UserEntity getUser() {
        return user;
    }

    public AuthEntity(UserEntity user, String token) {
		super();
		this.user = user;
		this.token = token;
	}

	public void setUser(UserEntity user) {
        this.user = user;
    }

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	

}

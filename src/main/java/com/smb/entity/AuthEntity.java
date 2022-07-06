package com.smb.entity;


public class AuthEntity {
    private UserEntity user;

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

    private String token;
}

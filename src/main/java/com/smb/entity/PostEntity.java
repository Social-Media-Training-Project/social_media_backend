package com.smb.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "post")
public class PostEntity {
	@Id
	private String id;

    private String userId;

    private String originalUserId;

    private String content;

    private String image;

    private Instant createdAt;

    List<String> love = new ArrayList<>();

    List<String> share = new ArrayList<>();
    
    List<CommentEntity> comment = new ArrayList<>();
    
    

	public PostEntity(String id, String userId, String originalUserId, String content, String image, Instant createdAt,
			List<String> love, List<String> share) {
		super();
		this.id = id;
		this.userId = userId;
		this.originalUserId = originalUserId;
		this.content = content;
		this.image = image;
		this.createdAt = createdAt;
		this.love = love;
		this.share = share;
		this.comment=comment;
	}



	public PostEntity() {
		super();
		// TODO Auto-generated constructor stub
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
	}



	public String getOriginalUserId() {
		return originalUserId;
	}



	public void setOriginalUserId(String originalUserId) {
		this.originalUserId = originalUserId;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}



	public String getImage() {
		return image;
	}



	public void setImage(String image) {
		this.image = image;
	}



	public Instant getCreatedAt() {
		return createdAt;
	}



	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}



	public List<String> getLove() {
		return love;
	}



	public void setLove(List<String> love) {
		this.love = love;
	}



	public List<String> getShare() {
		return share;
	}



	public void setShare(List<String> share) {
		this.share = share;
	}



	public List<CommentEntity> getComment() {
		return comment;
	}



	public void setComment(List<CommentEntity> comment) {
		this.comment = comment;
	}
	

        
	
}

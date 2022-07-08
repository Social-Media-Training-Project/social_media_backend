package com.smb.entity;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="comments")
public class CommentEntity {
	@Id
    private String id;
    private String userId;
	private String postId;
    private String content;
    private String createdAt;

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

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public CommentEntity(String id, String userId, String postId, String content, String createdAt) {
		this.id = id;
		this.userId = userId;
		this.postId = postId;
		this.content = content;
		this.createdAt = createdAt;
	}

	public CommentEntity() {
		super();
	}
}
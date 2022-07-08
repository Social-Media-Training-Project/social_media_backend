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
    private String content;
    private String image;
    private String createdAt;
    private List<String> loves = new ArrayList<>();
    private List<CommentEntity> comments = new ArrayList<>();

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

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public List<String> getLoves() {
		return loves;
	}

	public void setLoves(List<String> loves) {
		this.loves = loves;
	}

	public List<CommentEntity> getComments() {
		return comments;
	}

	public void setComments(List<CommentEntity> comments) {
		this.comments = comments;
	}

	public PostEntity(String id, String userId, String content, String image, String createdAt, List<String> loves, List<CommentEntity> comments) {
		this.id = id;
		this.userId = userId;
		this.content = content;
		this.image = image;
		this.createdAt = createdAt;
		this.loves = loves;
		this.comments = comments;
	}
	public PostEntity() {
		super();
	}
}

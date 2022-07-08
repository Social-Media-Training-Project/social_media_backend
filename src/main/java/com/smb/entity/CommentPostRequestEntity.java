package com.smb.entity;

public class CommentPostRequestEntity {
	private CommentEntity commentEntity;
    private IdObjectEntity postId;
	public CommentPostRequestEntity(CommentEntity commentEntity, IdObjectEntity postId) {
		super();
		this.commentEntity = commentEntity;
		this.postId = postId;
	}
	public CommentPostRequestEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CommentEntity getCommentEntity() {
		return commentEntity;
	}
	public void setCommentEntity(CommentEntity commentEntity) {
		this.commentEntity = commentEntity;
	}
	public IdObjectEntity getPostId() {
		return postId;
	}
	public void setPostId(IdObjectEntity postId) {
		this.postId = postId;
	}
    
    

}

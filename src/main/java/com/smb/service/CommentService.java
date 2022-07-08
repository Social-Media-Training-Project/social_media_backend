package com.smb.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.smb.entity.IdObjectEntity;
import com.smb.entity.UserEntity;
import com.smb.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smb.entity.CommentEntity;
import com.smb.entity.PostEntity;
import com.smb.repo.CommentRepo;
import com.smb.repo.PostRepo;

@Service
public class CommentService {
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PostService postService;

    public ResponseService insertComment(CommentEntity inputComment) {
        ResponseService responseObj = new ResponseService();
        Optional<UserEntity> optUser = userRepo.findById(inputComment.getUserId());
        Optional<PostEntity> optPost = postRepo.findById(inputComment.getPostId());
        if (!optUser.isPresent() || !optPost.isPresent()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("cannot find target post id: " + inputComment.getPostId() + " or user Id: " + inputComment.getUserId());
            responseObj.setPayload(null);
        } else {
            inputComment.setCreatedAt(Instant.now().toString());
            commentRepo.save(inputComment);
            PostEntity targetPost = optPost.get();
            List<CommentEntity> commentsList = targetPost.getComments();
            if (commentsList == null) {
                commentsList = new ArrayList<>();
            }
            commentsList.add(inputComment);
            targetPost.setComments(commentsList);
            postService.updatePostByComments(targetPost);
            responseObj.setStatus("success");
            responseObj.setMessage("comment posted");
            responseObj.setPayload(inputComment);
        }
        return responseObj;
    }

    public ResponseService getComments(IdObjectEntity inputPostId) {
        ResponseService responseObj = new ResponseService();
        Optional<PostEntity> optTargetPost = postRepo.findById(inputPostId.getId());
        if (optTargetPost.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("no post available with id:" + inputPostId.getId());
            responseObj.setPayload(null);
            return responseObj;
        } else {
            PostEntity targetPost = optTargetPost.get();
            List<CommentEntity> commentsList = targetPost.getComments();
            if (commentsList.size() > 0) {
                responseObj.setStatus("success");
                responseObj.setMessage("success");
                responseObj.setPayload(commentsList);
                return responseObj;
            } else {
                responseObj.setStatus("success");
                responseObj.setMessage("Post id " + inputPostId + " does not have any comments");
                responseObj.setPayload(null);
                return responseObj;
            }
        }
    }
}
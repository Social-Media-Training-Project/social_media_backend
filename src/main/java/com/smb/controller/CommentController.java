package com.smb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smb.entity.CommentEntity;
import com.smb.entity.CommentPostRequestEntity;
import com.smb.entity.IdObjectEntity;
import com.smb.service.CommentService;
import com.smb.service.ResponseService;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "*")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/users/insertcomment")
    public ResponseEntity<ResponseService> insertComment(@RequestBody CommentPostRequestEntity postedComment) {
        CommentEntity inputComment = postedComment.getCommentEntity();
        IdObjectEntity inputPostId = postedComment.getPostId();
        return new ResponseEntity<ResponseService>(commentService.insertComment(inputComment, inputPostId.getId()), HttpStatus.OK);
    }

    @PostMapping("/users/getcomments") 
    public ResponseEntity<ResponseService> getComments(@RequestBody IdObjectEntity inputPostId) {
        return new ResponseEntity<ResponseService>(commentService.getComments(inputPostId.getId()), HttpStatus.OK);
    }
}

package com.smb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smb.entity.CommentEntity;
import com.smb.entity.IdObjectEntity;
import com.smb.service.CommentService;
import com.smb.service.ResponseService;

@RestController
@RequestMapping("/api/")
public class CommentsController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/users/insertcomment")
    public ResponseEntity<ResponseService> insertComment(@RequestBody CommentEntity inputComment) {
        return new ResponseEntity<ResponseService>(commentService.insertComment(inputComment), HttpStatus.OK);
    }

    @PostMapping("/users/getcomments")
    public ResponseEntity<ResponseService> getCommentss(@RequestBody IdObjectEntity inputPostId) {
        return new ResponseEntity<ResponseService>(commentService.getComments(inputPostId), HttpStatus.OK);
    }
}

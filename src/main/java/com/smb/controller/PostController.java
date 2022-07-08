package com.smb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smb.entity.DoubleIdObjectEntity;
import com.smb.entity.IdObjectEntity;
import com.smb.entity.PostEntity;
import com.smb.service.PostService;
import com.smb.service.ResponseService;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "*")
public class PostController {
	@Autowired
    private PostService postService;

    @PostMapping("/users/insertpost")
    public ResponseEntity<ResponseService> insertPost(@RequestBody PostEntity inputPost) {
        return new ResponseEntity<ResponseService>(postService.insertPost(inputPost), HttpStatus.OK);
    }
    @DeleteMapping("/users/deletepost")
    public ResponseEntity<ResponseService> deletePost(@RequestBody PostEntity inputPost) {
        return new ResponseEntity<ResponseService>(postService.deletePost(inputPost), HttpStatus.OK);
    }
    
    @GetMapping("/users/myposts/{userId}")
    public ResponseEntity<ResponseService> findPostByUserId(@PathVariable String userId) {
        return new ResponseEntity<ResponseService>(postService.findPostByUserId(userId), HttpStatus.OK);
    }
 
    @PostMapping("/users/lovespost")
    public ResponseEntity<ResponseService> lovesPost(@RequestBody DoubleIdObjectEntity doubleId) {
        return new ResponseEntity<ResponseService>(postService.updatePostByLoves(doubleId), HttpStatus.OK);
    }
}

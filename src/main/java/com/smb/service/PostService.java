package com.smb.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smb.entity.PostEntity;
import com.smb.entity.UserEntity;
import com.smb.repo.PostRepo;
import com.smb.repo.UserRepo;

@Service
public class PostService {
	@Autowired
    private PostRepo postRepo;
    @Autowired
    private UserRepo userRepo;
    
    public ResponseService insertPost(PostEntity inputPost) {
        ResponseService responseObj = new ResponseService();
        inputPost.setCreatedAt(Instant.now());
        responseObj.setStatus("success");
        responseObj.setMessage("success");
        responseObj.setPayload(postRepo.save(inputPost));
        return responseObj;
    }

}

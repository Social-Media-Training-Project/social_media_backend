package com.smb.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smb.entity.DoubleIdObjectEntity;
import com.smb.entity.IdObjectEntity;
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
    	Optional<UserEntity> optThisUser = userRepo.findById(inputPost.getUserId());
    	
    	if(optThisUser.isEmpty()) {
    		responseObj.setStatus("fail");
            responseObj.setMessage("fail");
            responseObj.setPayload(null);
        }
    	else {
    		PostEntity newPost = postRepo.save(inputPost);
    		List<String> fansId =  optThisUser.get().getFans();
    		List<UserEntity> fans = (List<UserEntity>) userRepo.findAllById(fansId);
    		
    		fans.forEach(fan -> {
    			List<String> fanFeed = fan.getUserFeed();
    			fanFeed.add(newPost.getId());
    			fan.setUserFeed(fanFeed.stream().distinct().collect(Collectors.toList()));
    		});

    		userRepo.saveAll(fans);
    		
    		inputPost.setCreatedAt(Instant.now());

            responseObj.setStatus("success");
            responseObj.setMessage("success");
            responseObj.setPayload(newPost);
        }
        return responseObj;
    }

    
    public ResponseService deletePost(PostEntity inputPost) {
    	
    	ResponseService responseObj = new ResponseService();
    	Optional<UserEntity> optThisUser = userRepo.findById(inputPost.getUserId());
    	
    	if(optThisUser.isEmpty()) {
    		responseObj.setStatus("fail");
            responseObj.setMessage("fail");
            responseObj.setPayload(null);
        }
    	else {
    		PostEntity newPost = postRepo.save(inputPost);
    		List<String> fansId =  optThisUser.get().getFans();
    		List<UserEntity> fans = (List<UserEntity>) userRepo.findAllById(fansId);
    		
    		fans.forEach(fan -> {
    			List<String> fanFeed = fan.getUserFeed();
    			fanFeed.remove(newPost.getId());
    			fan.setUserFeed(fanFeed.stream().distinct().collect(Collectors.toList()));
    		});
    		
    		postRepo.deleteById(inputPost.getId());

    		userRepo.saveAll(fans);
    		
    		inputPost.setCreatedAt(Instant.now());
            responseObj.setStatus("success");
            responseObj.setMessage("success");
            responseObj.setPayload(newPost);
        }
        return responseObj;
    }
    
    public ResponseService findPostByUserId(IdObjectEntity inputUserId) {
        ResponseService responseObj = new ResponseService();
        Optional<List<PostEntity>> userPostsOpt = postRepo.findByUserIdOrderByCreatedAtDesc(inputUserId.getId());
        if (userPostsOpt.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("cannot find any post from user id: " + inputUserId.getId());
            responseObj.setPayload(null);
            return responseObj;
        } else {
            List<PostEntity> userPosts = userPostsOpt.get();
            responseObj.setStatus("success");
            responseObj.setMessage("success");
            responseObj.setPayload(userPosts);
            return responseObj;
        }
    }
    public ResponseService updatePostByComment(PostEntity inputPost) {
        ResponseService responseObj = new ResponseService();
        Optional<PostEntity> optPost = postRepo.findById(inputPost.getId());
        if (optPost.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("cannot find post id: " + inputPost.getId());
            responseObj.setPayload(null);
            return responseObj;
        } else {
            // inputPost.setCreatedAt(Instant.now());
            postRepo.save(inputPost);
            responseObj.setStatus("success");
            responseObj.setMessage("post is updated successfully");
            responseObj.setPayload(inputPost);
            return responseObj;
        }
    }
    public ResponseService updatePostByLove(DoubleIdObjectEntity doubleId) {
        // id 1 - post Id, id 2 - user who liked post
        ResponseService responseObj = new ResponseService();
        Optional<PostEntity> optPost = postRepo.findById(doubleId.getOtherAcc());
        if (optPost.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("cannot find post id: " + doubleId.getOtherAcc());
            responseObj.setPayload(null);
            return responseObj;
        } else {
            PostEntity targetPost = optPost.get();
            List<String> loveList = targetPost.getLove();
            if (loveList == null) {
                loveList = new ArrayList<>();
            }
            // love and unlove a post
            if (!loveList.contains(doubleId.getThisAcc())) {
                loveList.add(doubleId.getThisAcc());
            } else {
                loveList.remove(doubleId.getThisAcc());
            }
            targetPost.setLove(loveList);
            postRepo.save(targetPost);
            responseObj.setStatus("success");
            responseObj.setMessage("update love to the target post id: " + targetPost.getId());
            responseObj.setPayload(targetPost);
            return responseObj;
        }
    }
    public ResponseService updatePostByShare(DoubleIdObjectEntity doubleId) {
        // id 1 - post Id, id 2 - user who shared post
        ResponseService responseObj = new ResponseService();
        Optional<PostEntity> optPost = postRepo.findById(doubleId.getOtherAcc());
        if (optPost.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("cannot find post id: " + doubleId.getOtherAcc());
            responseObj.setPayload(null);
            return responseObj;
        } else {
            PostEntity targetPost = optPost.get();
            List<String> shareList = targetPost.getShare();
            if (shareList == null) {
                shareList = new ArrayList<>();
            }
            // save id of user who shared the post then update post
            shareList.add(doubleId.getThisAcc());
            //targetPost.setShare(shareList);
            postRepo.save(targetPost);
             //update post list of user who shared the post
            targetPost.setUserId(doubleId.getThisAcc());
            targetPost.setId(null);
            targetPost.setContent("Shared a post: " + targetPost.getContent());
            targetPost.setLove(new ArrayList<>());
            targetPost.setShare(new ArrayList<>());
            targetPost.setComment(new ArrayList<>());
            postRepo.save(targetPost);

            responseObj.setStatus("success");
            responseObj.setMessage("add a share to the target post id: " + targetPost.getId());
            responseObj.setPayload(targetPost);
            return responseObj;
        }
    }
}

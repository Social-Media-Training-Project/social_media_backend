package com.smb.service;

import com.smb.entity.*;
import com.smb.repo.PostRepo;
import com.smb.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptEncoder;

    public ResponseService saveUser(UserEntity inputUser) {
        ResponseService responseObj = new ResponseService();
        Optional<UserEntity> optUser = userRepo.findByUserName(inputUser.getUserName());
        if (optUser.isPresent()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("UserName address " + inputUser.getUserName() + " existed");
            responseObj.setPayload(null);
        } else {
            inputUser.setCreatedAt(Instant.now().toString());
            inputUser.setPassword(bCryptEncoder.encode(inputUser.getPassword()));
            UserEntity user = userRepo.save(inputUser);
            List<String> fans = user.getFans();
            if (fans.isEmpty()) {
                fans = new ArrayList<>();
            }
            fans.add(user.getId());
            user.setFans(fans);
            userRepo.save(user);
            responseObj.setPayload(user.getUserName());
            responseObj.setStatus("success");
            responseObj.setMessage("success");
        }
        return responseObj;
    }
    
    public ResponseService findAll() {
        ResponseService responseObj = new ResponseService();
        List<UserEntity> users = userRepo.findAll();
        
//        users.stream().forEach( user -> {
//        	user.setPassword("");
//        });
        
        responseObj.setPayload(users);
        responseObj.setStatus("success");
        responseObj.setMessage("success");
        return responseObj;
    }

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<UserEntity> optUser = userRepo.findByUserName(userName);
        User springUser = null;

        if (optUser.isEmpty()) {
            throw new UsernameNotFoundException("Cannot find user with userName: " + userName);
        } else {
            UserEntity foundUser = optUser.get();
            Set<GrantedAuthority> ga = new HashSet<>();
            springUser = new User(foundUser.getUserName(), foundUser.getPassword(), ga);
            return springUser;
        }
		
	}
	
	public ResponseService findById(String id) {
        ResponseService responseObj = new ResponseService();
        Optional<UserEntity> optUser = userRepo.findById(id);
        if (optUser.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("user id: " + id + " not existed");
            responseObj.setPayload(null);
            return responseObj;
        } else {
        	UserEntity user = optUser.get();
        	user.setPassword("");
            responseObj.setPayload(user);
            responseObj.setStatus("success");
            responseObj.setMessage("success");
            return responseObj;
        }
    }

    public ResponseService followUser(DoubleIdObjectEntity doubleId) {

        ResponseService responseObj = new ResponseService();
        Optional<UserEntity> optOtherAccUser = userRepo.findById(doubleId.getOtherAcc());
        Optional<UserEntity> optThisAccUser = userRepo.findById(doubleId.getThisAcc());
        if (optOtherAccUser.isEmpty() || optThisAccUser.isEmpty() || doubleId.getThisAcc().equals(doubleId.getOtherAcc())) {
            responseObj.setStatus("fail");
            String msg = "invalid user id";
            if(doubleId.getThisAcc().equals(doubleId.getOtherAcc()))
                msg = "cannot follow yourself";
            responseObj.setMessage(msg);
            responseObj.setPayload(null);
        } else {
            UserEntity otherAccUser = optOtherAccUser.get();
            UserEntity thisAccUser = optThisAccUser.get();

            // update gods list and feed of the user, which was followed by current user
            List<String> godsList = thisAccUser.getGods();
            if (godsList == null) {
                godsList = new ArrayList<>();
            }
            godsList.add(otherAccUser.getId());
            thisAccUser.setGods(godsList.stream().distinct().collect(Collectors.toList()));

            List<String> thisAccFeed = thisAccUser.getUserFeed();
            if (thisAccFeed == null) {
                thisAccFeed = new ArrayList<>();
            }

            Optional<List<PostEntity>> otherAccPosts = postRepo.findByUserIdOrderByCreatedAtDesc(doubleId.getOtherAcc());
            if (!otherAccPosts.isEmpty()) {
                List<String> finalThisAccFeed = thisAccFeed;
                otherAccPosts.get().forEach(postEntity -> {
                    finalThisAccFeed.add(postEntity.getId());
                });
                thisAccUser.setUserFeed(finalThisAccFeed.stream().distinct().collect(Collectors.toList()));
            }

            // update fans list of other user
            List<String> fansList = otherAccUser.getFans();
            if (fansList == null) {
                fansList = new ArrayList<>();
            }
            fansList.add(thisAccUser.getId());
            otherAccUser.setFans(fansList.stream().distinct().collect(Collectors.toList()));


            userRepo.save(otherAccUser);
            userRepo.save(thisAccUser);


            responseObj.setStatus("success");
            responseObj.setMessage(
                    "User id " + thisAccUser.getId() + " successfully followed user id " + otherAccUser.getId());
            responseObj.setPayload(new IdObjectEntity(doubleId.getOtherAcc()));
        }
        return responseObj;
    }

    public ResponseService unfollowUser(DoubleIdObjectEntity doubleId) {

        ResponseService responseObj = new ResponseService();
        Optional<UserEntity> optOtherAccUser = userRepo.findById(doubleId.getOtherAcc());
        Optional<UserEntity> optThisAccUser = userRepo.findById(doubleId.getThisAcc());
        if (optOtherAccUser.isEmpty() || optThisAccUser.isEmpty() || doubleId.getThisAcc().equals(doubleId.getOtherAcc())) {
            responseObj.setStatus("fail");
            String msg = "invalid user id";
            if(doubleId.getThisAcc().equals(doubleId.getOtherAcc()))
            	msg = "cannot unfollow yourself";
            responseObj.setMessage(msg);
            responseObj.setPayload(null);
        } else {
            UserEntity otherAccUser = optOtherAccUser.get();
            UserEntity thisAccUser = optThisAccUser.get();

            // update gods list and feed of the user, which was followed by current user
            List<String> godsList = thisAccUser.getGods();
            if (godsList == null) {
                godsList = new ArrayList<>();
            }
            if (godsList.contains(otherAccUser.getId())) {
                godsList.remove(otherAccUser.getId());
                thisAccUser.setGods(godsList);
            }
            else {
                responseObj.setStatus("fail");
                responseObj.setMessage("cannot unfollow a user you are not following");
                responseObj.setPayload(null);
            }


            List<String> thisAccFeed = thisAccUser.getUserFeed();
            if (thisAccFeed == null) {
                thisAccFeed = new ArrayList<>();
            }
            Optional<List<PostEntity>> otherAccPosts = postRepo.findByUserIdOrderByCreatedAtDesc(doubleId.getOtherAcc());
            if (!otherAccPosts.isEmpty()) {
                List<String> finalThisAccFeed = thisAccFeed;
                otherAccPosts.get().forEach(postEntity -> {
                    finalThisAccFeed.remove(postEntity.getId());
                });
                thisAccUser.setUserFeed(finalThisAccFeed);
            }

            // update fans list of other user
            List<String> fansList = otherAccUser.getFans();
            if (fansList == null) {
                fansList = new ArrayList<>();
            }
            fansList.remove(thisAccUser.getId());
            otherAccUser.setFans(fansList);


            userRepo.save(otherAccUser);
            userRepo.save(thisAccUser);


            responseObj.setStatus("success");
            responseObj.setMessage(
                    "User id " + thisAccUser.getId() + " successfully UNfollowed user id " + otherAccUser.getId());
            responseObj.setPayload(new IdObjectEntity(doubleId.getOtherAcc()));
        }
        return responseObj;
    }

    public ResponseService getUserFeed(String userId) {
        ResponseService responseObj = new ResponseService();
        Optional<UserEntity> optUser = userRepo.findById(userId);
        if(optUser.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("invalid user id");
            responseObj.setPayload(null);
        }
        else {
            UserEntity user = optUser.get();
            List<String> userFeedId = user.getUserFeed();
            List<PostEntity> userFeedPopulated = postRepo.findAll();
            responseObj.setStatus("success");
            responseObj.setMessage("success");
            responseObj.setPayload(userFeedPopulated);
        }
        return responseObj;
    }
    public ResponseService updateUser(UserEntity inputUser) {
        ResponseService responseObj = new ResponseService();
        Optional<UserEntity> optUser = userRepo.findById(inputUser.getId());
        if (optUser.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("user id: " + inputUser.getId() + " not existed");
            responseObj.setPayload(null);
        }
        else {
            UserEntity currentUser = optUser.get();
            if (bCryptEncoder.matches(inputUser.getPassword(), currentUser.getPassword())) {
                inputUser.setPassword(bCryptEncoder.encode(inputUser.getPassword()));
                UserEntity user = userRepo.save(inputUser);
                user.setPassword("");
                responseObj.setPayload(user);
                responseObj.setStatus("success");
                responseObj.setMessage("success");
            } else {
                responseObj.setStatus("fail");
                responseObj.setMessage("current password is not correct");
                responseObj.setPayload(null);
            }
        }
        return responseObj;
    }
    public ResponseService deleteUser(UserSignInEntity inputUser){
        ResponseService responseObj = new ResponseService();
        Optional<UserEntity> optUser = userRepo.findByUserName(inputUser.getUserName());
        if (!optUser.isPresent()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("user name : " + inputUser.getUserName() + " not existed");
            responseObj.setPayload(null);
        }
        else {
            UserEntity currentUser = optUser.get();
            if (bCryptEncoder.matches(inputUser.getPassword(), currentUser.getPassword())) {
                currentUser.setPassword(bCryptEncoder.encode(bCryptEncoder.encode(inputUser.getPassword())));
                currentUser.setUserName("Deleted user");
                userRepo.save(currentUser);
                responseObj.setPayload("User Deleted");
                responseObj.setStatus("success");
                responseObj.setMessage("success");
            } else {
                responseObj.setStatus("fail");
                responseObj.setMessage("current password is not correct");
                responseObj.setPayload(null);
            }
        }
        return responseObj;
    }



}
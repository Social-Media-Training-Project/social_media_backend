package com.smb.service;

import com.smb.entity.DoubleIdObjectEntity;
import com.smb.entity.IdObjectEntity;
import com.smb.entity.PostEntity;
import com.smb.entity.UserEntity;
import com.smb.repo.PostRepo;
import com.smb.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;
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
            return responseObj;
        } else {
            inputUser.setPassword(bCryptEncoder.encode(inputUser.getPassword()));
            UserEntity user = userRepo.save(inputUser);
            List<String> following = user.getFollowing();
            if (following.isEmpty()) {
                following = new ArrayList<>();
            }
            following.add(user.getId());
            user.setFollowing(following);
            userRepo.save(user);
            responseObj.setPayload(user.getUserName());
            responseObj.setStatus("success");
            responseObj.setMessage("success");
            return responseObj;
        }
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
            String role = foundUser.getRole();
            Set<GrantedAuthority> ga = new HashSet<>();
            ga.add(new SimpleGrantedAuthority(role));
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
        // id1 - followed user, id2 - follower

        ResponseService responseObj = new ResponseService();
        Optional<UserEntity> optId1User = userRepo.findById(doubleId.getId1());
        Optional<UserEntity> optId2User = userRepo.findById(doubleId.getId2());
        if (optId1User.isEmpty() || optId2User.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("invalid user id");
            responseObj.setPayload(null);
        } else {
            UserEntity id1User = optId1User.get();
            UserEntity id2User = optId2User.get();

            // add to following list and to feed of the user which was followed by current user
            List<String> followerList = id1User.getFollower();
            if (followerList == null) {
                followerList = new ArrayList<>();
            }
            followerList.add(id2User.getId());
            id1User.setFollower(followerList);

            // add to following list and to feed of current user
            List<String> followingList = id2User.getFollowing();
            if (followingList == null) {
                followingList = new ArrayList<>();
            }
            followingList.add(id1User.getId());
            id1User.setFollowing(followingList);

            List<String> id2Feed = id2User.getUserFeed();
            if (id2Feed == null) {
                id2Feed = new ArrayList<>();
            }

            Optional<List<PostEntity>> id1Posts = postRepo.findByUserIdOrderByCreatedAtDesc(doubleId.getId1());
            if (!id1Posts.isEmpty()) {
                List<String> finalId2Feed = id2Feed;
                id1Posts.get().forEach(postEntity -> {
                    finalId2Feed.add(postEntity.getId());
                });
                id2User.setUserFeed(finalId2Feed);
            }
            /* Todo: Follower

             */

            userRepo.save(id1User);
            userRepo.save(id2User);


            responseObj.setStatus("success");
            responseObj.setMessage(
                    "User id " + id2User.getId() + " successfully followed user id " + id1User.getId());
            responseObj.setPayload(new IdObjectEntity(doubleId.getId1()));
        }
        return responseObj;
    }


}

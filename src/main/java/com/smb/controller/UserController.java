package com.smb.controller;

import java.util.Optional;

import com.smb.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smb.repo.UserRepo;
import com.smb.service.JWTUtil;
import com.smb.service.ResponseService;
import com.smb.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	@Autowired
    private UserService userService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepo userRepo;
    
    
    @GetMapping("/users")
    public ResponseEntity<ResponseService> findAllUsers() {
        return new ResponseEntity<ResponseService>(userService.findAll(), HttpStatus.OK);
    }
    
    
    @PostMapping("/users/save")
    public ResponseEntity<ResponseService> saveUser(@RequestBody UserEntity inputUser) {
        return new ResponseEntity<ResponseService>(userService.saveUser(inputUser), HttpStatus.OK);
    }

    @PostMapping("/users/signin")
    public ResponseEntity<ResponseService> userSignIn(@RequestBody UserSignInEntity inputUser) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(inputUser.getUserName(), inputUser.getPassword()));
            String token = jwtUtil.generateToken(inputUser.getUserName());
            Optional<UserEntity> optUser = userRepo.findByUserName(inputUser.getUserName());
            UserEntity user = optUser.get();
            user.setPassword("");
            return new ResponseEntity<ResponseService>(new ResponseService("success", "authenticated", new AuthEntity(user, token)), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<ResponseService>(new ResponseService("fail", "unauthenticated", null), HttpStatus.OK);
        }
    }
    
    @GetMapping("/users/profile/{inputId}")
    public ResponseEntity<ResponseService> findById(@PathVariable("inputId") String inputId) {
        return new ResponseEntity<ResponseService>(userService.findById(inputId), HttpStatus.OK);
    }

    @PostMapping("/users/follow")
    public ResponseEntity<ResponseService> followUser(@RequestBody DoubleIdObjectEntity doubleId) {
        return new ResponseEntity<ResponseService>(userService.followUser(doubleId), HttpStatus.OK);
    }

    @PostMapping("/users/unfollow")
    public ResponseEntity<ResponseService> unfollowUser(@RequestBody DoubleIdObjectEntity doubleId) {
        return new ResponseEntity<ResponseService>(userService.unfollowUser(doubleId), HttpStatus.OK);
    }
    
     @GetMapping("/users/feed/{userId}")
     public ResponseEntity<ResponseService> getUserFeed(@PathVariable("userId") String userId) {
         return new ResponseEntity<ResponseService>(userService.getUserFeed(userId), HttpStatus.OK);
     }
    
     @PutMapping("/users")
     public ResponseEntity<ResponseService> updateUser(@RequestBody  UserEntity inputUser ){
         return new ResponseEntity<ResponseService>(userService.updateUser(inputUser), HttpStatus.OK);
     }
    
     @DeleteMapping("/users")
     public ResponseEntity<ResponseService> deleteUser(@RequestBody UserSignInEntity inputUser ){
         return new ResponseEntity<ResponseService>(userService.deleteUser(inputUser), HttpStatus.OK);
     }
    
    
}

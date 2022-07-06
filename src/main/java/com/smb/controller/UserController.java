package com.smb.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smb.entity.AuthEntity;
import com.smb.entity.UserEntity;
import com.smb.entity.UserSignInEntity;
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
        	System.out.print(ex);
            return new ResponseEntity<ResponseService>(new ResponseService("fail", "unauthenticated", null), HttpStatus.OK);
        }
    }
}

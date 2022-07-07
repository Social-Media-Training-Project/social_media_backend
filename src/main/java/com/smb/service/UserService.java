package com.smb.service;

import com.smb.entity.UserEntity;
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

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

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
}

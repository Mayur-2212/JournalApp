package com.mayursbapplication.journalApp.controller;

import com.mayursbapplication.journalApp.entity.User;
import com.mayursbapplication.journalApp.services.UserDetailsServiceImpl;
import com.mayursbapplication.journalApp.services.UserService;
import com.mayursbapplication.journalApp.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publicJWT")
@Slf4j
public class PublicControllerWithJWT{

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("get-users")
    public ResponseEntity<List<User>> getUsers () {
        List<User> users = userService.getAll();

        if (users != null && !users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping("sign-up")
    public ResponseEntity<User> createUser (@RequestBody User user) {

        try {
            userService.saveNewUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
        catch (RuntimeException e) {
            throw new RuntimeException("Username is already present");
        }


    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUSer (@RequestBody User user) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt,HttpStatus.OK);
        }
        catch (RuntimeException e) {
            log.error("Exception occurred while createAuthenticationToken ", e);
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }


    }
}

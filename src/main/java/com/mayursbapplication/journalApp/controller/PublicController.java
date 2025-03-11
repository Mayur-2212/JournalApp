package com.mayursbapplication.journalApp.controller;

import com.mayursbapplication.journalApp.entity.User;
import com.mayursbapplication.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController{

    @Autowired
    private UserService userService;

    @GetMapping("get-users")
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = userService.getAll();

        if(users != null && !users.isEmpty()){
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping("create-user")
    public ResponseEntity<User> createUser(@RequestBody User user){

        try {
            userService.saveNewUser(user);
            return new ResponseEntity<>(user,HttpStatus.CREATED) ;
        }
        catch (RuntimeException e) {
            throw new RuntimeException("Username is already present");
        }


    }
}

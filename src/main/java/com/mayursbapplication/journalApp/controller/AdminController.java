package com.mayursbapplication.journalApp.controller;

import com.mayursbapplication.journalApp.entity.User;
import com.mayursbapplication.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController{

    @Autowired
    private UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUser() {

        List<User> users = userService.getAll();

        if (users != null && !users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-Admin")
    public void createAdmin(@RequestBody User user){
        userService.saveAdmin(user);
    }

}

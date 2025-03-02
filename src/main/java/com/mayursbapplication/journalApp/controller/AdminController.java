package com.mayursbapplication.journalApp.controller;

import com.mayursbapplication.journalApp.cache.AppCache;
import com.mayursbapplication.journalApp.entity.User;
import com.mayursbapplication.journalApp.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin")
public class AdminController{

    @Autowired
    private UserService userService;

    @Autowired
    private AppCache appCache;

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

    // this is used to clear the stored in memory keys as your application is running hence bean is already created of AppCache and apikey is stored in memory
    @GetMapping("clear-app-cache")
    public ResponseEntity<String> clearCache(){
        appCache.initial();
        log.info("Application cache cleared.");
        return new ResponseEntity<>("Application cache cleared successfully.",HttpStatus.OK);
    }


}

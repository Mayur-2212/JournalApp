package com.mayursbapplication.journalApp.controller;

import com.mayursbapplication.journalApp.entity.User;
import com.mayursbapplication.journalApp.repository.UserRepo;
import com.mayursbapplication.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController{

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;


    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody User user) {

        // when user get authenticate at the time of login, SecurityContextHolder store the details of authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User userInDB = userService.findByUserName(username);
        userInDB.setUserName(user.getUserName());
        userInDB.setPassword(user.getPassword());

        userService.saveNewUser(userInDB);

        return new ResponseEntity<>(userInDB, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        userRepo.deleteByUserName(authentication.getName());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.isAuthenticated()){
            try {
                userService.deleteAll();
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}


    /// We don't pass userid in url
//    @PutMapping("updateUser/{id}")
//    public ResponseEntity<?> updateUser(@PathVariable ObjectId id,@RequestBody User newUserInfo){
//        User oldUser = userService.findById(id).orElse(null);
//
//        if(oldUser != null){
//            oldUser.setUserName(!newUserInfo.getUserName().isEmpty() ? newUserInfo.getUserName() : oldUser.getUserName());
//            oldUser.setPassword(!newUserInfo.getPassword().isEmpty() ? newUserInfo.getPassword() : oldUser.getPassword());
//            userService.saveEntry(oldUser);
//            return new ResponseEntity<>(oldUser,HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//
//    }
//
//    @DeleteMapping("deleteUser/{id}")
//    public ResponseEntity<?> deleteUserById(@PathVariable ObjectId id){
//
//        userService.deleteById(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }


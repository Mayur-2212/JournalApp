
package com.mayursbapplication.journalApp.services;


import com.mayursbapplication.journalApp.entity.User;
import com.mayursbapplication.journalApp.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserService{

    /// Business Logic is written in service classes
    /// Service class call the repo class


    public static final PasswordEncoder passwordEncoder=  new BCryptPasswordEncoder();

    /// creating instance of logger class // commented as already @Slf4j used
  //  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

   @Autowired
   private UserRepo userRepo;
   @Transactional
   public boolean saveNewUser(User user){
       try{
           user.setPassword(passwordEncoder.encode(user.getPassword()));
           user.setRole(Arrays.asList("User"));
           userRepo.save(user);
           return true;
       }
       catch (Exception e) {

           /// used this if you are using @Slf4j
           log.trace("Tracing");// need to do additional configuration for Trace and Debug IN .properties or .yml file
           log.debug("Debugging");
           log.info("Info");
           log.warn("Warning",e);
           log.error("Error Occurred for {} :",user.getUserName(),e); // you can provide multiple argument {}


           /// used this if you are using instane of Logger class
//           logger.trace("Tracing",e);// need to do additional configuration for Trace and Debug
//           logger.debug("Debugging",e);
//           logger.info("Info",e);
//           logger.warn("Warning",e);
//           logger.error("Error Occurred for {} :",user.getUserName(),e); // you can provide multiple argument {}

           return false;
       }
   }

    public void saveAdmin(User user){

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Arrays.asList("User","ADMIN"));
        userRepo.save(user);

    }

    public void saveUser(User user){

        userRepo.save(user);

    }


   public List<User> getAll(){
       return this.userRepo.findAll();
   }

   public Optional<User> findById(ObjectId id){
       return userRepo.findById(id);

   }

   public void deleteById(ObjectId id){
        userRepo.deleteById(id);
   }

   public void deleteAll(){
       userRepo.deleteAll();
   }

   public User findByUserName(String username){
       return userRepo.findByUserName(username);
   }
}

package com.ecommerce.loginservice.controller;

import com.ecommerce.loginservice.exceptions.UserAlreadyExistException;
import com.ecommerce.loginservice.exceptions.UserNotFoundException;
import com.ecommerce.loginservice.model.User;
import com.ecommerce.loginservice.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/check")
    public String check(){
        return "User Working..!";
    }

    @GetMapping("/getUserByUserEmail/{userEmail}")
    public ResponseEntity getUserByUserEmail(@PathVariable String userEmail) throws IOException{
        try {
            return new ResponseEntity<User>(userService.getUserByUserEmail(userEmail), HttpStatus.OK);
        }catch (UserNotFoundException e){
            return new ResponseEntity("User Not Found", HttpStatus.ACCEPTED);
        }
    }

    @PostMapping("/registerNewUser")
    public ResponseEntity<User> registerNewUser(@RequestBody User user) throws IOException{
       try {
           return new ResponseEntity<User>(userService.registerNewUser(user), HttpStatus.OK);
       }catch (UserAlreadyExistException e){
           return new ResponseEntity("User Already Exist", HttpStatus.CONFLICT);
       }
    }

    @PostConstruct
    public void initRoleAndUser(){
        userService.initRoleAndUser();
    }
}

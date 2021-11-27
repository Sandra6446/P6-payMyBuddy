package com.api.payMyBuddy.controller;

import com.api.payMyBuddy.model.front.User;
import com.api.payMyBuddy.model.requestBody.UserProfile;
import com.api.payMyBuddy.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController extends ValidationClass {

    @Autowired
    private final UserService userService;

    @ApiOperation("Adds a user in database")
    @PostMapping
    public ResponseEntity<Object> addUser(@RequestBody User user) {
        if(isNotValid(user)) {
            return new ResponseEntity<>("Error in request body", HttpStatus.BAD_REQUEST);
        } else {
            return userService.createUser(user);
        }
    }

    @ApiOperation("Gets a user balance")
    @GetMapping("/{email}/balance")
    public ResponseEntity<Object> getBalance(@PathVariable String email) {
        if (isEmpty(email)) {
            return new ResponseEntity<>("Error in request body", HttpStatus.BAD_REQUEST);
        } else {
            return userService.getBalance(email);
        }
    }

    @ApiOperation("Gets a user")
    @GetMapping("/{email}")
    public ResponseEntity<Object> getUser(@PathVariable String email) {
        if (isEmpty(email)) {
            return new ResponseEntity<>("Error in request body", HttpStatus.BAD_REQUEST);
        } else {
            return userService.readUserByEmail(email);
        }
    }

    @ApiOperation("Updates a user")
    @PutMapping("/{email}")
    public ResponseEntity<Object> updateUser(@PathVariable String email, @RequestBody UserProfile profile) {
        if(isNotValid(profile)) {
            return new ResponseEntity<>("Error in request body", HttpStatus.BAD_REQUEST);
        } else {
            return userService.updateUser(email,profile);
        }
    }
}

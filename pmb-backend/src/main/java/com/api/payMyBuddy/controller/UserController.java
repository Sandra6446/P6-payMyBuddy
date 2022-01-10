package com.api.payMyBuddy.controller;

import com.api.payMyBuddy.exceptions.APIRuntimeException;
import com.api.payMyBuddy.model.front.User;
import com.api.payMyBuddy.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Collects and updates user information
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController extends ValidationClass {

    @Autowired
    private final UserService userService;

    /**
     * Gets all user information
     *
     * @param email : The current user email
     * @return Status OK, with user information if the operation succeeds, otherwise the reason of the failure
     */
    @ApiOperation(value = "Gets a user", authorizations = {@Authorization(value = "jwtToken")})
    @GetMapping("/{email}")
    public ResponseEntity<?> getUser(@PathVariable String email) {
        if (isEmpty(email)) {
            return new ResponseEntity<>("Error in request body", HttpStatus.BAD_REQUEST);
        } else {
            try {
                return userService.getUser(email);
            } catch (APIRuntimeException e) {
                return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
            }
        }
    }

    /**
     * Updates user profile
     *
     * @param email : The current user email
     * @param user  : The new profile
     * @return Status OK, "Profile updated" if the operation succeeds, otherwise the reason of the failure
     */
    @ApiOperation(value = "Updates a user", authorizations = {@Authorization(value = "jwtToken")})
    @PutMapping("/{email}")
    public ResponseEntity<?> updateUser(@PathVariable String email, @RequestBody User user) {
        if (isNotValid(user) || isEmpty(email)) {
            return new ResponseEntity<>("Error in request body", HttpStatus.BAD_REQUEST);
        } else {
            try {
                return userService.updateUser(email, user);
            } catch (APIRuntimeException e) {
                return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
            }
        }
    }
}

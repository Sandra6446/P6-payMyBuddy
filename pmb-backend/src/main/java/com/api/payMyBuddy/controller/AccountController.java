package com.api.payMyBuddy.controller;

import com.api.payMyBuddy.exceptions.APIRuntimeException;
import com.api.payMyBuddy.service.AccountService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controls the user money in Pay My Buddy
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController extends ValidationClass {

    @Autowired
    private final AccountService accountService;

    /**
     * Adds money to a user account
     * @param email : the receiver email
     * @param amount : the amount to be added
     * @return Status OK,"Account updated" if the operation succeeds, otherwise the reason of the failure
     */
    @ApiOperation(value = "Adds money to a user account", authorizations = { @Authorization(value="jwtToken") })
    @PostMapping()
    public HttpEntity<?> addMoney(@RequestParam String email, @RequestParam int amount ) {
        if (isEmpty(email)) {
            return new ResponseEntity<>("Error in request body", HttpStatus.BAD_REQUEST);
        } else {
            try {
                return accountService.addMoney(email,amount);
            } catch (APIRuntimeException e) {
                return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
            }
        }
    }

}

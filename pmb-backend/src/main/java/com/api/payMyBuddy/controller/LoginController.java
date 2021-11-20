package com.api.payMyBuddy.controller;

import com.api.payMyBuddy.model.front.Login;
import com.api.payMyBuddy.service.LoginService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController extends ValidationClass {

    @Autowired
    private final LoginService loginService;

    @ApiOperation("Checks if the person exists in database")
    @PostMapping
    public ResponseEntity<String> checkLogin(@RequestBody Login login) {
        if (isNotValid(login)) {
            return new ResponseEntity<>("Error in request body", HttpStatus.BAD_REQUEST);
        } else {
            return loginService.checkLogin(login);
        }
    }
}

package com.api.payMyBuddy.controller;

import com.api.payMyBuddy.model.front.Login;
import com.api.payMyBuddy.service.LoginService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private static final Logger logger = LogManager.getLogger(LoginController.class);

    @Autowired
    private final LoginService loginService;

    @ApiOperation("Checks if the person exists in database")
    @PostMapping
    public ResponseEntity<String> checkLogin(@RequestBody Login login) {
        return loginService.checkLogin(login);
    }
}

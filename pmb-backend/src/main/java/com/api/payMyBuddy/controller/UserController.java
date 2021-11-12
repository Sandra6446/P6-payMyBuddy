package com.api.payMyBuddy.controller;

import com.api.payMyBuddy.model.front.User;
import com.api.payMyBuddy.service.UserService;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Null;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    @Autowired
    private final UserService userService;

    @ApiOperation("Adds user in database")
    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        Set<ConstraintViolation<User>> constraintViolations =
                validator.validate(user);

        if (constraintViolations.size() > 0) {
            for (ConstraintViolation<User> constraint : constraintViolations) {
                logger.error(constraint.getRootBeanClass().getSimpleName() +
                        "." + constraint.getPropertyPath() + " " + constraint.getMessage());
            }
            return new ResponseEntity<>("Error in request body", HttpStatus.BAD_REQUEST);
        } else {
            return userService.createUser(user);
        }
    }

    @ApiOperation("Gets user balance")
    @GetMapping("/{email}/balance")
    public ResponseEntity<Object> getBalance(@PathVariable String email) {
        if (email.isEmpty()) {
            return new ResponseEntity<>("Error in request body", HttpStatus.BAD_REQUEST);
        } else {
            User user = userService.readUserByEmail(email);
            if (Objects.isNull(user.getEmail())) {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            } else {
                FilterProvider filterProvider = new SimpleFilterProvider()
                        .addFilter("userFilter", SimpleBeanPropertyFilter.filterOutAllExcept("balance"));
                MappingJacksonValue balance = new MappingJacksonValue(user);
                balance.setFilters(filterProvider);
                return ResponseEntity.ok(balance);
            }
        }
    }

    @ApiOperation("Gets user")
    @GetMapping("/{email}")
    public ResponseEntity<Object> getUser(@PathVariable String email) {
        if (email.isEmpty()) {
            return new ResponseEntity<>("Error in request body", HttpStatus.BAD_REQUEST);
        } else {
            User user = userService.readUserByEmail(email);
            if (Objects.isNull(user.getEmail())) {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            } else {
                FilterProvider filterProvider = new SimpleFilterProvider()
                        .addFilter("userFilter", SimpleBeanPropertyFilter.serializeAll());
                MappingJacksonValue response = new MappingJacksonValue(user);
                response.setFilters(filterProvider);
                return ResponseEntity.ok(response);
            }
        }
    }
}

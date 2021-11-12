package com.api.payMyBuddy.controller;

import com.api.payMyBuddy.model.front.User;
import com.api.payMyBuddy.service.ConnectionService;
import com.api.payMyBuddy.service.UserService;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/connection")
@RequiredArgsConstructor
public class ConnectionController {

    @Autowired
    private final ConnectionService connectionService;

    @Autowired
    private final UserService userService;

    @ApiOperation("Adds connection in database")
    @PostMapping
    public ResponseEntity<Object> addConnection(@RequestParam("user_email") String user_email, @RequestParam("connection_email") String connection_email) {
        if (user_email.isEmpty() || connection_email.isEmpty()) {
            return new ResponseEntity<>("Error in request params", HttpStatus.BAD_REQUEST);
        } else {
            return connectionService.createConnectionEntity(user_email, connection_email);
        }
    }

    @ApiOperation("Gets connections of a user")
    @GetMapping("/{email}")
    public ResponseEntity<Object> getConnections(@PathVariable String email) {
        if (email.isEmpty()) {
            return new ResponseEntity<>("Error in request body", HttpStatus.BAD_REQUEST);
        } else {
            User user = userService.readUserByEmail(email);
            if (Objects.isNull(user.getEmail())) {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            } else {
                FilterProvider filterProvider = new SimpleFilterProvider()
                        .addFilter("userFilter", SimpleBeanPropertyFilter.filterOutAllExcept("connections"));
                MappingJacksonValue connections = new MappingJacksonValue(user);
                connections.setFilters(filterProvider);
                return ResponseEntity.ok(connections);
            }
        }
    }
}

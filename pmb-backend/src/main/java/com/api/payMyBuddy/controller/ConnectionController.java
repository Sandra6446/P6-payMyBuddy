package com.api.payMyBuddy.controller;

import com.api.payMyBuddy.model.front.Connection;
import com.api.payMyBuddy.service.ConnectionService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8090")
@RestController
@RequestMapping("/connection")
@RequiredArgsConstructor
public class ConnectionController extends ValidationClass {

    @Autowired
    private final ConnectionService connectionService;

    @ApiOperation("Adds connection in database")
    @PostMapping
    public ResponseEntity<String> addConnection(@RequestBody Connection connection) {
        if (isNotValid(connection)) {
            return new ResponseEntity<>("Error in request body", HttpStatus.BAD_REQUEST);
        } else if (connection.getUserEmail().equals(connection.getConnectionEmail())) {
            return new ResponseEntity<>("Emails have to be different", HttpStatus.BAD_REQUEST);
        } else {
            return connectionService.createConnection(connection);
        }
    }

    @ApiOperation("Gets connections of a user")
    @GetMapping("/{email}")
    public ResponseEntity<Object> getConnections(@PathVariable String email) {
        if (isEmpty(email)) {
            return new ResponseEntity<>("Error in request body", HttpStatus.BAD_REQUEST);
        } else {
            return connectionService.getConnections(email);
        }
    }
}
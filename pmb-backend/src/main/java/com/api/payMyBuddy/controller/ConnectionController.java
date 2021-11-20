package com.api.payMyBuddy.controller;

import com.api.payMyBuddy.model.requestBody.ConnectionBody;
import com.api.payMyBuddy.service.ConnectionService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/connection")
@RequiredArgsConstructor
public class ConnectionController extends ValidationClass {

    @Autowired
    private final ConnectionService connectionService;

    @ApiOperation("Adds connection in database")
    @PostMapping
    public ResponseEntity<String> addConnection(@RequestBody ConnectionBody connectionBody) {
        if (isNotValid(connectionBody)) {
            return new ResponseEntity<>("Error in request body", HttpStatus.BAD_REQUEST);
        } else if (connectionBody.getUserEmail().equals(connectionBody.getConnectionEmail())) {
            return new ResponseEntity<>("Emails have to be different", HttpStatus.BAD_REQUEST);
        } else {
            return connectionService.createConnection(connectionBody);
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

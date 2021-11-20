package com.api.payMyBuddy.controller;

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
public class ConnectionController {

    @Autowired
    private final ConnectionService connectionService;

    //-- A revoir
    @ApiOperation("Adds connection in database")
    @PostMapping
    public ResponseEntity<Object> addConnection(@RequestParam("user_email") String user_email, @RequestParam("connection_email") String connection_email) {
        if (user_email.isEmpty() || connection_email.isEmpty()) {
            return new ResponseEntity<>("Error in request params", HttpStatus.BAD_REQUEST);
        } else {
            return connectionService.createConnection(user_email, connection_email);
        }
    }

    @ApiOperation("Gets connections of a user")
    @GetMapping("/{email}")
    public ResponseEntity<Object> getConnections(@PathVariable String email) {
        if (email.isEmpty()) {
            return new ResponseEntity<>("Error in request body", HttpStatus.BAD_REQUEST);
        } else {
            return connectionService.getConnections(email);
        }
    }
}

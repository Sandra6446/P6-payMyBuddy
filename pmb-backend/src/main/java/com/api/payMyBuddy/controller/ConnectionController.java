package com.api.payMyBuddy.controller;

import com.api.payMyBuddy.exceptions.APIRuntimeException;
import com.api.payMyBuddy.model.front.Connection;
import com.api.payMyBuddy.service.ConnectionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Collects and updates information about a user connections
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/connection")
@RequiredArgsConstructor
public class ConnectionController extends ValidationClass {

    @Autowired
    private final ConnectionService connectionService;

    /**
     * Adds a contact
     *
     * @param connection : The contact to create
     * @return Status CREATED,"Connection added" if the operation succeeds, otherwise the reason of the failure
     */
    @ApiOperation(value = "Adds connection in database", authorizations = {@Authorization(value = "jwtToken")})
    @PostMapping
    public ResponseEntity<?> addConnection(@RequestBody Connection connection) {
        if (isNotValid(connection) || connection.getUserEmail().equals(connection.getConnectionEmail())) {
            return new ResponseEntity<>("Error in request body", HttpStatus.BAD_REQUEST);
        } else {
            try {
                return connectionService.createConnection(connection);
            } catch (APIRuntimeException e) {
                return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
            }
        }
    }

    /**
     * Gets the contact list of current user
     *
     * @param email : The current user email
     * @return Status OK, with the contact list if the operation succeeds, otherwise the reason of the failure
     */
    @ApiOperation(value = "Gets connections of a user", authorizations = {@Authorization(value = "jwtToken")})
    @GetMapping("/{email}")
    public ResponseEntity<?> getConnections(@PathVariable String email) {
        if (isEmpty(email)) {
            return new ResponseEntity<>("Error in request body", HttpStatus.BAD_REQUEST);
        } else {
            try {
                return connectionService.getConnections(email);
            } catch (APIRuntimeException e) {
                return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
            }
        }
    }
}

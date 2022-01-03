package com.api.payMyBuddy.controller;

import com.api.payMyBuddy.exceptions.APIRuntimeException;
import com.api.payMyBuddy.model.front.Transaction;
import com.api.payMyBuddy.service.TransactionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController extends ValidationClass {

    @Autowired
    private final TransactionService transactionService;

    @ApiOperation(value = "Gets all transactions of a user", authorizations = { @Authorization(value="jwtToken") })
    @GetMapping("/{email}")
    public ResponseEntity<?> getAllTransactions(@PathVariable String email) {
        if (isEmpty(email)) {
            return new ResponseEntity<>("Error in request body", HttpStatus.BAD_REQUEST);
        } else {
            try {
                return transactionService.getAllTransactions(email);
            } catch (APIRuntimeException e) {
                return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
            }
        }
    }

    @ApiOperation(value = "Gets transactions made by a user", authorizations = { @Authorization(value="jwtToken") })
    @GetMapping("/{email}/myTransactions")
    public ResponseEntity<?> getTransactions(@PathVariable String email) {
        if (isEmpty(email)) {
            return new ResponseEntity<>("Error in request body", HttpStatus.BAD_REQUEST);
        } else {
            try {
                return transactionService.getMyTransactions(email);
            } catch (APIRuntimeException e) {
                return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
            }
        }
    }

    @ApiOperation(value = "Adds a transaction for a user", authorizations = { @Authorization(value="jwtToken") })
    @PostMapping
    public ResponseEntity<?> addTransaction(@RequestBody Transaction transaction) {
        if (isNotValid(transaction)) {
            return new ResponseEntity<>("Error in request body", HttpStatus.BAD_REQUEST);
        } else if (transaction.getUserEmail().equals(transaction.getConnectionEmail())) {
            return new ResponseEntity<>("Emails have to be different", HttpStatus.BAD_REQUEST);
        } else {
            try {
                return transactionService.createTransaction(transaction);
            } catch (APIRuntimeException e) {
                return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
            }
        }
    }

}

package com.api.payMyBuddy.controller;

import com.api.payMyBuddy.model.requestBody.TransactionBody;
import com.api.payMyBuddy.service.TransactionService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController extends ValidationClass {

    @Autowired
    private final TransactionService transactionService;

    @ApiOperation("Gets transactions made by a user")
    @GetMapping("/{email}")
    public ResponseEntity<Object> getAllTransactions(@PathVariable String email) {
        if (isEmpty(email)) {
            return new ResponseEntity<>("Error in request body", HttpStatus.BAD_REQUEST);
        } else {
            return transactionService.getAllTransactions(email);
        }
    }

    @ApiOperation("Gets transactions made by a user")
    @GetMapping("/{email}/myTransactions")
    public ResponseEntity<Object> getTransactions(@PathVariable String email) {
        if (isEmpty(email)) {
            return new ResponseEntity<>("Error in request body", HttpStatus.BAD_REQUEST);
        } else {
            return transactionService.getMyTransactions(email);
        }
    }

    @ApiOperation("Adds a transaction for a user")
    @PostMapping
    public ResponseEntity<String> addTransaction(@RequestBody TransactionBody transactionBody) {
        if (isNotValid(transactionBody)) {
            return new ResponseEntity<>("Error in request body", HttpStatus.BAD_REQUEST);
        } else if (transactionBody.getUserEmail().equals(transactionBody.getConnectionEmail())) {
            return new ResponseEntity<>("Emails have to be different", HttpStatus.BAD_REQUEST);
        } else {
            return transactionService.createTransaction(transactionBody);
        }
    }

}

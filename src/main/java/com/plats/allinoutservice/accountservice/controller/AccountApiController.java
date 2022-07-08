package com.plats.allinoutservice.accountservice.controller;

import com.plats.allinoutservice.accountservice.pojo.Account;
import com.plats.allinoutservice.accountservice.pojo.LoginResponse;
import com.plats.allinoutservice.accountservice.pojo.Secret;
import com.plats.allinoutservice.accountservice.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.*;


@RestController
public class AccountApiController {

   private final AccountService accountService;

    @Autowired
    public AccountApiController(AccountService accountService) {
        this.accountService = accountService;

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody Account account) {
        String requestId = UUID.randomUUID().toString();

        LoginResponse response = accountService.login(account);

        return ResponseEntity
                .status(HttpStatus.OK)
                .header("request_id", requestId)
                .body(response);

    }

    @PostMapping("/signup")
    public ResponseEntity<Boolean> signup(@RequestBody Account account) {

        String requestId = UUID.randomUUID().toString();

        accountService.signup(account);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("request_id", requestId)
                .body(true);
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> arr = accountService.getAllAccounts();

        String requestId = UUID.randomUUID().toString();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("request_id", requestId)
                .body(arr);
    }

    @DeleteMapping("/accounts/{username}")
    public ResponseEntity<String> deleteAccount(@RequestParam("ss") String secretString,
                                                @PathVariable String username) {
        accountService.deleteAccount(secretString, username);

        String requestId = UUID.randomUUID().toString();
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("request_id", requestId)
                .body("Account " + username + " deleted.");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody Secret secret) {
        accountService.logout(secret);

        String requestId = UUID.randomUUID().toString();
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("request_id", requestId)
                .body("Success.");
    }


}

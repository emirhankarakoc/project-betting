package com.betting.karakoc.controller;

import com.betting.karakoc.model.dtos.UserEntityDTO;
import com.betting.karakoc.model.requests.CreateUserRequest;
import com.betting.karakoc.service.abstracts.AccountService;
import com.betting.karakoc.service.abstracts.MailSenderService;
import com.betting.karakoc.service.abstracts.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@AllArgsConstructor
@Tag(name = "Account Controller")
@CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")

public class AccountController {
    private final AccountService accountService;
    private final UserService userService;
    private final MailSenderService mailSenderService;

    @Operation(
            summary = "register to system.")
    @PostMapping("/register")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
    public UserEntityDTO register(CreateUserRequest request) {
        return accountService.register(request);
    }

    @Operation(
            summary = "login to system.")
    @PostMapping("/login")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
    public ResponseEntity<String> login(String username, String password) {
        return accountService.login(username, password);

    }

    @GetMapping
    public UserEntityDTO getMe() {
        return accountService.getMe();
    }

    @Operation(
            summary = "changing password")
    @PutMapping("/changePassword")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")

    public UserEntityDTO changePassword(String username, String password, String newPassword) {
        return userService.changePassword(username, password, newPassword);
    }


    @Operation(
            summary = "sends a new password to users mail adress")
    @PostMapping("/forgotPassword")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")

    public String forgotPassword(@RequestParam String username) {
        return mailSenderService.forgotPassword(username);
    }



}

package com.betting.karakoc.controller;

import com.betting.karakoc.models.dtos.UserEntityDTO;
import com.betting.karakoc.models.requests.ChangePasswordRequest;
import com.betting.karakoc.models.requests.CreateUserRequest;
import com.betting.karakoc.models.requests.LoginRequest;
import com.betting.karakoc.service.interfaces.AccountService;
import com.betting.karakoc.service.interfaces.MailSenderService;
import com.betting.karakoc.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
            summary = "REGISTER")
    @PostMapping("/register")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
    public UserEntityDTO register(@Valid @RequestBody CreateUserRequest request) {
        return accountService.register(request);
    }

    @Operation(
            summary = "LOGIN")
    @PostMapping("/login")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
    public String login(@RequestBody LoginRequest request) {
        return accountService.login(request.getUsername(), request.getPassword());

    }

    @Operation(summary = "GET ME")
    @GetMapping
    public UserEntityDTO getMe(String token) {
        return accountService.getMe(token);
    }

    @Operation(
            summary = "CHANGE PASSWORD")
    @PutMapping("/changePassword")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")

    public UserEntityDTO changePassword(@RequestBody ChangePasswordRequest request) {
        return userService.changePassword(request);
    }


    @Operation(
            summary = "FORGOT PASSWORD")
    @PostMapping("/forgotPassword")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")

    public String forgotPassword(@RequestParam @NotNull  String username) {
        return mailSenderService.forgotPassword(username);
    }


}

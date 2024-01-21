package com.betting.karakoc.controller;

import com.betting.karakoc.model.dtos.UserEntityDTO;
import com.betting.karakoc.model.requests.CreateUserRequest;
import com.betting.karakoc.service.abstracts.AccountService;
import com.betting.karakoc.service.abstracts.MailSenderService;
import com.betting.karakoc.service.abstracts.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public UserEntityDTO register(@NotNull CreateUserRequest request) {
        return accountService.register(request);
    }

    @Operation(
            summary = "LOGIN")
    @PostMapping("/login")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")
    public ResponseEntity<String> login(@NotNull @NotBlank String username, @NotNull String password) {
        return accountService.login(username, password);

    }

    @Operation(summary = "GET ME")
    @GetMapping
    public UserEntityDTO getMe() {
        return accountService.getMe();
    }

    @Operation(
            summary = "CHANGE PASSWORD")
    @PutMapping("/changePassword")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")

    public UserEntityDTO changePassword(@NotNull String username, @NotNull String password,@NotNull String newPassword) {
        return userService.changePassword(username, password, newPassword);
    }


    @Operation(
            summary = "FORGOT PASSWORD")
    @PostMapping("/forgotPassword")
    @CrossOrigin(origins = "https://bettting.ey.r.appspot.com/")

    public String forgotPassword(@RequestParam @NotNull  String username) {
        return mailSenderService.forgotPassword(username);
    }


}

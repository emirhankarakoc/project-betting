package com.betting.karakoc.models.requests;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserRequest {
    @NotBlank
    String firstname;
    @NotBlank
    String lastname;
    @NotBlank
    @Size(min = 3,max = 20,message = "Username length must be lower than 20, higher than 3 ")
    String username;
    @NotBlank
    @Size(min = 8,max = 32,message = "Password length must be lower than 32, higher than 8 ")
    String password;
    @NotBlank
    String repeatPassword;

    @AssertTrue(message = "Passwords must match")
    private boolean isPasswordMatching() {
        return password.equals(repeatPassword);
    }
}

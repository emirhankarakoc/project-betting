package com.betting.karakoc.models.requests;


import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateManagerRequest {
    @NotBlank(message = "must be entered")
    String firstname;
    @NotBlank(message = "must be entered")
    String lastname;
    @NotBlank(message = "must be entered")
    String username;
    @NotBlank(message = "Password must be entered")
    @Size(min = 8,max = 32,message = "Password length must be lower than 32, higher than 8 ")
    String password;
    @NotBlank(message = "Repeat passsword must be entered")
    String repeatPassword;

    @AssertTrue(message = "Passwords must match")
    private boolean isPasswordMatching() {
        if (password == null) {
            return repeatPassword == null;
        }
        return password.equals(repeatPassword);
    }
}

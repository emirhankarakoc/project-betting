package com.betting.karakoc.model.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUserRequest {
    @NotNull @NotBlank @NotEmpty
    String firstname;
    @NotNull@NotBlank@NotEmpty
    String lastname;
    @NotNull@NotBlank@NotEmpty
    String username;
    @NotNull@NotBlank@NotEmpty
    String password;
    @NotNull@NotBlank@NotEmpty
    String repeatPassword;

}

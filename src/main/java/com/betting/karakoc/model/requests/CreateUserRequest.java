package com.betting.karakoc.model.requests;

import lombok.Data;

@Data
public class CreateUserRequest {
    String firstname;
    String lastname;
    String username;
    String password;
    String repeatPassword;

}

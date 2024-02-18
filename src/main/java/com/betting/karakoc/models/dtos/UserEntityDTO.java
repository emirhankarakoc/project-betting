package com.betting.karakoc.models.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntityDTO {

    private String message;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String token;


}

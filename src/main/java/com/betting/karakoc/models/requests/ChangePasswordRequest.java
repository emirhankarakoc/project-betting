package com.betting.karakoc.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ChangePasswordRequest {
    @NotBlank private String password;
    @NotBlank private String newPassword;
    @NotBlank private String userToken;
}

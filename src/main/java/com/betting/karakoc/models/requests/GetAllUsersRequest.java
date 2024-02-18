package com.betting.karakoc.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GetAllUsersRequest {
    @NotBlank
    private String adminToken;
    @NotBlank
    private int pageSize;
}

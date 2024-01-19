package com.betting.karakoc.model.real;


import com.betting.karakoc.exceptions.general.BadRequestException;
import com.betting.karakoc.model.dtos.UserEntityDTO;
import com.betting.karakoc.model.enums.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data

public class UserEntity {
    @Id
    @NotNull
    @NotBlank
    @NotEmpty
    private String id;
    @NotNull@NotBlank@NotEmpty
    private LocalDate createddatetime;
    //dont change to createdDateTime. using createddatetime for pageable. must be createddatetime. dont touch.
    @NotNull@NotBlank@NotEmpty
    private LocalDate updatedDateTime;
    @NotNull@NotBlank@NotEmpty
    private String firstname;
    @NotNull@NotBlank@NotEmpty
    private String lastname;
    @NotNull@NotBlank@NotEmpty
    private String username;
    @NotNull@NotBlank@NotEmpty
    private String password;
    @Enumerated
    @NotNull@NotBlank@NotEmpty
    private UserRole role;


    public static UserEntityDTO userToDto(UserEntity user) {

        UserEntityDTO dto = new UserEntityDTO();
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setPassword(user.getPassword());
        dto.setUsername(user.getUsername());
        return dto;
    }

    public static int validateUsername(String username) {
        int specialCharCount = 0;
        for (int i = 0; i < username.length(); i++) {
            if (username.charAt(i) == '@') specialCharCount++;
            if (username.charAt(i) == '.') specialCharCount++;
            if (!username.endsWith(".com"))
                throw new BadRequestException("Invalid username type.\nExample: example@example.com");
        }
        return specialCharCount;
    }


}

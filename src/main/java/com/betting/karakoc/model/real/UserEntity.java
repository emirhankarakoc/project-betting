package com.betting.karakoc.model.real;


import com.betting.karakoc.exceptions.general.BadRequestException;
import com.betting.karakoc.model.dtos.UserEntityDTO;
import com.betting.karakoc.model.enums.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data

public class UserEntity {
    @Id
    private String id;
    private LocalDate createddatetime;
    private LocalDate updatedDateTime;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    @Enumerated
    private UserRole role;


    public static UserEntityDTO userToDto(UserEntity user) {

        UserEntityDTO dto = new UserEntityDTO();
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setPassword(user.getPassword());
        dto.setUsername(user.getUsername());
        return dto;
    }

    public static int validateusername(String username) {
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

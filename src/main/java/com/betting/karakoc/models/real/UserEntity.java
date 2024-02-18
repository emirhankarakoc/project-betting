package com.betting.karakoc.models.real;


import com.betting.karakoc.exceptions.general.BadRequestException;
import com.betting.karakoc.exceptions.general.NotfoundException;
import com.betting.karakoc.models.dtos.UserEntityDTO;
import com.betting.karakoc.models.enums.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Optional;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    private String id;
    private LocalDate createddatetime;
    //dont change to createdDateTime. using createddatetime for pageable. must be createddatetime. dont touch.
    private LocalDate updatedDateTime;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    @Enumerated
    private UserRole role;
    private String token;


    // We don't set password here. Because we use passwordCrypter().

    public static UserEntityDTO userToDto(UserEntity user) {
        UserEntityDTO dto = new UserEntityDTO();
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setUsername(user.getUsername());
        dto.setToken(user.getToken());
        dto.setCreatedDateTime(user.getCreateddatetime());
        return dto;
    }

    // This method counts how many times our nickname has "." and "@"
    // I this counter lower than 2 or ends without ".com" and length is shorter than 7... THROW EXCEPTION !!!
    public static void validateUsername(String username) {
        int specialCharCount = 0;
        for (int i = 0; i < username.length(); i++) {
            if (username.charAt(i) == '@') specialCharCount++;
            if (username.charAt(i) == '.') specialCharCount++;
            if (!username.endsWith(".com"))
                throw new BadRequestException("Invalid username type.\nExample: example@example.com");
        }
        if (specialCharCount != 2 && username.length() < 7)
            throw new BadRequestException("Invalid username type.\nExample: example@example.com\nThe mail adress must be 8 digits or more.");

    }

    public static String passwordCrypter(String password) {
        // :(
        return password;
    }

    public static void isEmpty(Optional<?> obj) {
        if (obj.isEmpty()) throw new NotfoundException("Object not found.");
    }

    public static void onlyAdminValidation(Optional<UserEntity> user) {
        if (!(user.isPresent() && user.get().getRole().equals(UserRole.ROLE_ADMIN)))
            throw new BadRequestException("Invalid admin token.");
    }
}

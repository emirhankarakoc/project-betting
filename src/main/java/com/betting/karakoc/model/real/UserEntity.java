package com.betting.karakoc.model.real;


import com.betting.karakoc.exceptions.GeneralException;
import com.betting.karakoc.model.dtos.UserEntityDTO;
import com.betting.karakoc.model.enums.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.util.Optional;

@Entity@Data

public class UserEntity {
    @Id
    private String  id;
    private LocalDate createddatetime;
    private LocalDate updatedDateTime;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String token;
    @Enumerated
    private UserRole role;

    public static void isUserEmpty(Optional<UserEntity> user){
        if (user.isEmpty()) throw new GeneralException("Invalid token.",400);

    }
    public static void isUserIsAdmin(Optional<UserEntity> user){
        if (!user.get().getRole().equals(UserRole.ROLE_ADMIN)) throw new GeneralException("Forbidden.",403);

    }
    public static void isTokenValid(UserEntity user){
        if (!(user.getRole()== UserRole.ROLE_USER || user.getRole()==UserRole.ROLE_ADMIN)) throw new GeneralException("Access denied.",401);

    }

    public static UserEntityDTO userToDto(UserEntity user){

        UserEntityDTO dto = new UserEntityDTO();
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setToken(user.getToken());
        dto.setPassword(user.getPassword());
        dto.setUsername(user.getUsername());
        return dto;
    }



}

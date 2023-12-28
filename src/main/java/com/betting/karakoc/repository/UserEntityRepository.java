package com.betting.karakoc.repository;

import com.betting.karakoc.model.dtos.UserEntityDTO;
import com.betting.karakoc.model.real.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserEntityRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findById(String id);
    Optional<UserEntity> findByUsername(String mail);

    boolean existsByUsername(String mail);
    Optional<UserEntity> findByToken(String token);

}

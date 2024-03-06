package com.betting.karakoc.repository;

import com.betting.karakoc.models.real.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserEntityRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findById(String id);

    Optional<UserEntity> findByUsername(String username);

    boolean existsByUsername(String username);

    Optional<UserEntity> findByToken(String token);
    Optional<UserEntity> findByCreatorCode(int creatorCode);
    boolean existsByCreatorCode(int creatorCode);


}

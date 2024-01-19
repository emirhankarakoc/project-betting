package com.betting.karakoc.repository;

import com.betting.karakoc.model.real.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserEntityRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findById(String id);

    Optional<UserEntity> findByUsername(String username);

    boolean existsByusername(String username);


}

package com.project.api_cotacao.repositories;

import com.project.api_cotacao.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);
}

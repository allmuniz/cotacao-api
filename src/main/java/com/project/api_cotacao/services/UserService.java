package com.project.api_cotacao.services;

import com.project.api_cotacao.entities.user.UserEntity;
import com.project.api_cotacao.entities.user.dtos.UserRequestDto;
import com.project.api_cotacao.entities.user.dtos.UserResponseAllDto;
import com.project.api_cotacao.entities.user.dtos.UserResponseBalanceDto;
import com.project.api_cotacao.entities.user.exceptions.UserFoundException;
import com.project.api_cotacao.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<UserResponseAllDto> createUser(UserRequestDto request) {

        UserEntity user = userRepository.findByEmail(request.email());
        if (user != null) throw new UserFoundException("User already exists");

        UserEntity newUser = new UserEntity(request);
        userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponseAllDto.fromEntity(newUser));
    }

    /*public ResponseEntity<UserResponseBalanceDto> updatePrincipalBalance(Long id) {
        UserEntity user = getUser(id);
    }*/

    public UserEntity getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserFoundException("User not found"));
    }
}

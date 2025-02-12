package com.project.api_cotacao.controllers;

import com.project.api_cotacao.config.security.dtos.AuthRequestDto;
import com.project.api_cotacao.config.security.dtos.AuthResponseDto;
import com.project.api_cotacao.entities.user.UserEntity;
import com.project.api_cotacao.entities.user.dtos.UserRequestDto;
import com.project.api_cotacao.entities.user.dtos.UserResponseAllDto;
import com.project.api_cotacao.services.UserService;
import com.project.api_cotacao.services.security.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponseAllDto> createUser(@RequestBody UserRequestDto request) {
        return userService.createUser(request);
    }

    @GetMapping("/")
    public ResponseEntity<UserResponseAllDto> findUser(@AuthenticationPrincipal UserEntity user) {
        return userService.findUser(user.getId());
    }

    @PutMapping("/update")
    public ResponseEntity updateUser(@AuthenticationPrincipal UserEntity user,
                                     @RequestBody UserRequestDto request) {
        userService.updateUser(user, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteUser(@AuthenticationPrincipal UserEntity user) {
        userService.deleteUser(user);
        return ResponseEntity.ok().build();
    }

}

package com.project.api_cotacao.controllers;

import com.project.api_cotacao.entities.user.dtos.UserRequestDto;
import com.project.api_cotacao.entities.user.dtos.UserResponseAllDto;
import com.project.api_cotacao.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponseAllDto> createUser(@RequestBody UserRequestDto request) {
        return userService.createUser(request);
    }

}

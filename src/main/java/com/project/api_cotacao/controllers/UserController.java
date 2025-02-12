package com.project.api_cotacao.controllers;

import com.project.api_cotacao.config.security.dtos.AuthRequestDto;
import com.project.api_cotacao.config.security.dtos.AuthResponseDto;
import com.project.api_cotacao.entities.user.UserEntity;
import com.project.api_cotacao.entities.user.dtos.UserRequestDto;
import com.project.api_cotacao.entities.user.dtos.UserResponseAllDto;
import com.project.api_cotacao.services.UserService;
import com.project.api_cotacao.services.security.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name = "User", description = "User Manager")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/auth")
    @Operation(description = "Endpoint responsavel por fazer a autenticação do usuário",
            summary = "Autenticação usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticação efetuada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Erro na autenticação"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/create")
    @Operation(description = "Endpoint responsavel por criar um usuário",
            summary = "Criação de usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criação efetuada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na criação do usuário"),
            @ApiResponse(responseCode = "403", description = "Erro na autenticação"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<UserResponseAllDto> createUser(@RequestBody UserRequestDto request) {
        return userService.createUser(request);
    }

    @GetMapping("/")
    @Operation(description = "Endpoint responsavel por buscar um usuário",
            summary = "Busca de usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca efetuada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na busca do usuário"),
            @ApiResponse(responseCode = "403", description = "Erro na autenticação"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<UserResponseAllDto> findUser(@AuthenticationPrincipal UserEntity user) {
        return userService.findUser(user.getId());
    }

    @PutMapping("/update")
    @Operation(description = "Endpoint responsavel por atualizar um usuário",
            summary = "Atualização de usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualização efetuada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na atualização do usuário"),
            @ApiResponse(responseCode = "403", description = "Erro na autenticação"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity updateUser(@AuthenticationPrincipal UserEntity user,
                                     @RequestBody UserRequestDto request) {
        userService.updateUser(user, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    @Operation(description = "Endpoint responsavel por deletar um usuário",
            summary = "Deleção de usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualização efetuada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na atualização do usuário"),
            @ApiResponse(responseCode = "403", description = "Erro na autenticação"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity deleteUser(@AuthenticationPrincipal UserEntity user) {
        userService.deleteUser(user);
        return ResponseEntity.ok().build();
    }

}

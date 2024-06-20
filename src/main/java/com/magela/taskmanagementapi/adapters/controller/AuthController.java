package com.magela.taskmanagementapi.adapters.controller;

import com.magela.taskmanagementapi.application.dto.AuthenticationDTO;
import com.magela.taskmanagementapi.application.dto.LoginResponseDTO;
import com.magela.taskmanagementapi.core.usecase.user.LoginUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@Tag(name = "User", description = "Operations about authentication")
public class AuthController {

    private final LoginUserUseCase loginUserUseCase;

    public AuthController(LoginUserUseCase loginUserUseCase) {
        this.loginUserUseCase = loginUserUseCase;
    }

    @PostMapping("/login")
    @Operation(summary = "Login a user and return a JWT token", description = "Authenticate a user and generate a JWT token.")
    public ResponseEntity<LoginResponseDTO> login(@Parameter(description = "Login credentials", required = true) @RequestBody @Valid AuthenticationDTO data){
        return ResponseEntity.ok(loginUserUseCase.execute(data));
    }
}
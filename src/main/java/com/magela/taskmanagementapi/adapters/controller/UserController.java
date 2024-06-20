package com.magela.taskmanagementapi.adapters.controller;

import com.magela.taskmanagementapi.application.dto.RegisterDTO;
import com.magela.taskmanagementapi.core.usecase.user.RegisterUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "User", description = "Operations about user management")
public class UserController {

    private final RegisterUserUseCase registerUserUseCase;

    public UserController(RegisterUserUseCase registerUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Register a new user in the system.")
    public ResponseEntity<Void> register(@Parameter(description = "Registration details", required = true) @RequestBody @Valid RegisterDTO data) {
        registerUserUseCase.execute(data);
        return ResponseEntity.ok().build();
    }
}

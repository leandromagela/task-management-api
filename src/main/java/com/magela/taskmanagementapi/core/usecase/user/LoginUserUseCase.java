package com.magela.taskmanagementapi.core.usecase.user;

import com.magela.taskmanagementapi.application.dto.AuthenticationDTO;
import com.magela.taskmanagementapi.application.dto.LoginResponseDTO;
import com.magela.taskmanagementapi.core.model.User;
import com.magela.taskmanagementapi.service.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class LoginUserUseCase {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public LoginUserUseCase(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public LoginResponseDTO execute(AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return new LoginResponseDTO(token);
    }
}



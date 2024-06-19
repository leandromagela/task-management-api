package com.magela.taskmanagementapi.core.usecase.user;

import com.magela.taskmanagementapi.application.dto.RegisterDTO;
import com.magela.taskmanagementapi.core.model.User;
import com.magela.taskmanagementapi.core.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserUseCase {
    private final UserRepository repository;

    public RegisterUserUseCase(UserRepository repository) {
        this.repository = repository;
    }

    public void execute(@Valid RegisterDTO data) {
        if (repository.findByLogin(data.login()) != null) {
            throw new IllegalArgumentException("User already exists");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.name(), data.login(), encryptedPassword, data.role());
        repository.save(newUser);
    }
}

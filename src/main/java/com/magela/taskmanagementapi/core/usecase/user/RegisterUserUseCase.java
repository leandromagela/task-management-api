package com.magela.taskmanagementapi.core.usecase.user;

import com.magela.taskmanagementapi.application.dto.RegisterDTO;
import com.magela.taskmanagementapi.application.exception.InvalidPasswordException;
import com.magela.taskmanagementapi.application.exception.UserAlreadyExistsException;
import com.magela.taskmanagementapi.core.model.User;
import com.magela.taskmanagementapi.core.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class RegisterUserUseCase {
    private final UserRepository repository;

    public RegisterUserUseCase(UserRepository repository) {
        this.repository = repository;
    }

    public void execute(@Valid RegisterDTO data) {

        validateUserDetails(data);

        if (!isValidPassword(data.password())) {
            throw new InvalidPasswordException("Password does not meet minimum security requirements. The password must contain uppercase letters, lowercase letters, numbers and at least 1 special character EX:!@#$%&");
        }
        if (repository.findByLogin(data.login()) != null) {
            throw new UserAlreadyExistsException("User already exists with email: " + data.login());
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.name(), data.login(), encryptedPassword, data.role());
        repository.save(newUser);
    }

    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%&]).{8,}$";
        return password.matches(passwordPattern);
    }

    private void validateUserDetails(RegisterDTO userDetails) {
        Map<String, String> errors = new HashMap<>();

        if (!StringUtils.hasText(userDetails.name()) || userDetails.name().matches(".*\\d.*")) {
            errors.put("name", "Name is required and cannot contain numbers");
        }

        if (!StringUtils.hasText(userDetails.login())) {
            errors.put("login", "Login is required");
        } else if (!userDetails.login().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errors.put("login", "Login should be a valid email");
        }

        if (!StringUtils.hasText(userDetails.password())) {
            errors.put("password", "Password is required");
        } else if (userDetails.password().length() < 8) {
            errors.put("password", "Password must be at least 8 characters long");
        }

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException("Invalid user details: " + errors);
        }
    }
}

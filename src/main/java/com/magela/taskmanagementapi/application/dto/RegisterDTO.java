package com.magela.taskmanagementapi.application.dto;

import com.magela.taskmanagementapi.core.model.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}

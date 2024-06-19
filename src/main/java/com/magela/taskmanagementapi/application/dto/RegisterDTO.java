package com.magela.taskmanagementapi.application.dto;

import com.magela.taskmanagementapi.core.model.UserRole;

public record RegisterDTO(String name, String login, String password, UserRole role) {
}

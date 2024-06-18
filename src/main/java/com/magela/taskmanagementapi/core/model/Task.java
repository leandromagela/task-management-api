package com.magela.taskmanagementapi.core.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
public class Task {

    @Getter
    private Long id;

    @NotEmpty(message = "Description is required")
    private String description;

    @NotEmpty(message = "Priority is required")
    private String priority;

    @Getter
    private boolean completed;

    @Getter
    private Long userId;

    public Task() {
    }

    public Task(Long id, String description, String priority, boolean completed, Long userId) {
        this.id = id;
        this.description = description;
        this.priority = priority;
        this.completed = completed;
        this.userId = userId;
    }

    // Getters and Setters

    public @NotEmpty(message = "Description is required") String getDescription() {
        return description;
    }

    public @NotEmpty(message = "Priority is required") String getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", priority='" + priority + '\'' +
                ", completed=" + completed +
                ", userId=" + userId +
                '}';
    }
}

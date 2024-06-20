package com.magela.taskmanagementapi.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Setter
@Getter
@EqualsAndHashCode(of = "id")
public class Task {

    // Getters and Setters
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Description is required")
    private String description;
    @NotNull(message = "Priority is required")
    @Enumerated(EnumType.STRING)
    private Priority priority;
    private boolean completed = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    public Task() {
    }

    public Task(Long id, String description, Priority priority, boolean completed, User user) {
        this.id = id;
        this.description = description;
        this.priority = priority;
        this.completed = completed;
        this.user = user;
    }

    public Task(Long id, String description, Priority priority, boolean completed, Long userId) {
        this.id = id;
        this.description = description;
        this.priority = priority;
        this.completed = completed;
        this.user = new User();
        this.user.setId(userId);
    }

    public @NotEmpty(message = "Description is required") String getDescription() {
        return description;
    }

    public void setDescription(@NotEmpty(message = "Description is required") String description) {
        this.description = description;
    }

    public @NotNull(message = "Priority is required") Priority getPriority() {
        return priority;
    }

    public void setPriority(@NotNull(message = "Priority is required") Priority priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", priority='" + priority + '\'' +
                ", completed=" + completed +
                ", user=" + user +
                '}';
    }
}

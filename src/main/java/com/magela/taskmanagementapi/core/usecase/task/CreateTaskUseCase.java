package com.magela.taskmanagementapi.core.usecase.task;

import com.magela.taskmanagementapi.application.exception.InvalidPriorityException;
import com.magela.taskmanagementapi.core.model.Priority;
import com.magela.taskmanagementapi.core.model.Task;
import com.magela.taskmanagementapi.core.model.User;
import com.magela.taskmanagementapi.core.repository.TaskRepository;
import com.magela.taskmanagementapi.core.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class CreateTaskUseCase {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public CreateTaskUseCase(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public Task execute(@Valid Task task) {
        validateTaskDetails(task);

        String username = getCurrentUsername();
        User user = userRepository.findByLogin(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        task.setUser(user);
        return taskRepository.save(task);
    }

    private void validateTaskDetails(Task task) {
        try {
            Priority.valueOf(task.getPriority().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidPriorityException("Priority must be High, Medium, or Low");
        }
    }

    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}

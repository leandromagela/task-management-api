package com.magela.taskmanagementapi.core.usecase.task;

import com.magela.taskmanagementapi.application.exception.ResourceNotFoundException;
import com.magela.taskmanagementapi.core.model.Task;
import com.magela.taskmanagementapi.core.model.User;
import com.magela.taskmanagementapi.core.repository.TaskRepository;
import com.magela.taskmanagementapi.core.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class DeleteTaskUseCase {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public DeleteTaskUseCase(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public void execute(Long taskId) {
        String username = getCurrentUsername();
        User user = userRepository.findByLogin(username);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException("Task not found with id: " + taskId);
        }

        taskRepository.deleteById(taskId);
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

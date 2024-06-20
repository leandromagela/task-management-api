package com.magela.taskmanagementapi.core.usecase.task;

import com.magela.taskmanagementapi.core.model.Priority;
import com.magela.taskmanagementapi.core.model.Task;
import com.magela.taskmanagementapi.core.model.User;
import com.magela.taskmanagementapi.core.repository.TaskRepository;
import com.magela.taskmanagementapi.core.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetPendingTasksUseCase {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public GetPendingTasksUseCase(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<Task> execute(String priority) {
        String username = getCurrentUsername();
        User user = userRepository.findByLogin(username);

        if (priority == null || priority.isEmpty()) {
            return taskRepository.findPendingTasksByUserId(user.getId());
        } else {
            try {
                Priority priorityEnum = Priority.valueOf(priority.toUpperCase());
                return taskRepository.findPendingTasksByUserIdAndPriority(user.getId(), priorityEnum);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid priority value: " + priority);
            }
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

    public List<Task> executeByPriority(Long userId, Priority priority) {
        return taskRepository.findPendingTasksByUserIdAndPriority(userId, priority);
    }
}

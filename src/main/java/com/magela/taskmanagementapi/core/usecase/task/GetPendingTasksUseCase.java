package com.magela.taskmanagementapi.core.usecase.task;

import com.magela.taskmanagementapi.core.model.Task;
import com.magela.taskmanagementapi.core.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetPendingTasksUseCase {

    private final TaskRepository taskRepository;

    public GetPendingTasksUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> execute(Long userId) {
        return taskRepository.findPendingTasksByUserId(userId);
    }

    public List<Task> executeByPriority(Long userId, String priority) {
        return taskRepository.findPendingTasksByUserIdAndPriority(userId, priority);
    }
}

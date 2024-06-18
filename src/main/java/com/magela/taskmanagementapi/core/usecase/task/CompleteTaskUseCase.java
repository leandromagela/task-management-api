package com.magela.taskmanagementapi.core.usecase.task;

import com.magela.taskmanagementapi.application.exception.TaskNotFoundException;
import com.magela.taskmanagementapi.core.model.Task;
import com.magela.taskmanagementapi.core.repository.TaskRepository;

import java.util.Optional;

public class CompleteTaskUseCase {

    private final TaskRepository taskRepository;

    public CompleteTaskUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void execute(Long taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setCompleted(true);
            taskRepository.save(task);
        } else {
            // Handle the case where the task is not found
            throw new TaskNotFoundException("Task with id " + taskId + " not found");
        }
    }
}


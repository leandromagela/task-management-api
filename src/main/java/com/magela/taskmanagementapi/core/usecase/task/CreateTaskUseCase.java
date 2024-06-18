package com.magela.taskmanagementapi.core.usecase.task;

import com.magela.taskmanagementapi.core.model.Task;
import com.magela.taskmanagementapi.core.repository.TaskRepository;

public class CreateTaskUseCase {

    private final TaskRepository taskRepository;

    public CreateTaskUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task execute(Task task) {
        return taskRepository.save(task);
    }
}

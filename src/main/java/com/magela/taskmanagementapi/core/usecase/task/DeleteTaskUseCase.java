package com.magela.taskmanagementapi.core.usecase.task;

import com.magela.taskmanagementapi.core.repository.TaskRepository;

public class DeleteTaskUseCase {

    private final TaskRepository taskRepository;

    public DeleteTaskUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void execute(Long taskId) {
        taskRepository.deleteById(taskId);
    }
}

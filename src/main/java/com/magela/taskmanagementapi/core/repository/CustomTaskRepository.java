package com.magela.taskmanagementapi.core.repository;

import com.magela.taskmanagementapi.core.model.Task;

import java.util.List;

public interface CustomTaskRepository {
    List<Task> findPendingTasksByUserIdAndPriority(Long userId, String priority);
}
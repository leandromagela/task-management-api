package com.magela.taskmanagementapi.core.repository;

import com.magela.taskmanagementapi.core.model.Task;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {

    Task save(Task task);
    void deleteById(Long taskId);
    Optional<Task> findById(Long taskId);

    @Query("SELECT * FROM tasks WHERE user_id = :userId AND completed = false ORDER BY priority")
    List<Task> findPendingTasksByUserId(Long userId);

    @Query("SELECT * FROM tasks WHERE user_id = :userId AND completed = false AND priority = :priority ORDER BY priority")
    List<Task> findPendingTasksByUserIdAndPriority(Long userId, String priority);

    void save(Optional<Task> task);
}

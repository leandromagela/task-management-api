package com.magela.taskmanagementapi.core.repository;

import com.magela.taskmanagementapi.core.model.Priority;
import com.magela.taskmanagementapi.core.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, CustomTaskRepository {

    @Query("SELECT t FROM Task t WHERE t.user.id = :userId AND t.completed = false")
    List<Task> findPendingTasksByUserId(@Param("userId") Long userId);

    @Query("SELECT t FROM Task t WHERE t.user.id = :userId AND t.completed = false AND t.priority = :priority")
    List<Task> findPendingTasksByUserIdAndPriority(@Param("userId") Long userId, @Param("priority") Priority priority);
}
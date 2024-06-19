package com.magela.taskmanagementapi.core.repository;

import com.magela.taskmanagementapi.core.model.Task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    //Task save(Task task);
    //void deleteById(Long taskId);
    //Optional<Task> findById(Long taskId);

    @Query(value = "SELECT * FROM tasks t WHERE t.user_id = :userId AND t.completed = false ORDER BY t.priority", nativeQuery = true)
    List<Task> findPendingTasksByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM tasks WHERE user_id = :userId AND completed = false AND priority = :priority ORDER BY priority", nativeQuery = true)
    List<Task> findPendingTasksByUserIdAndPriority(Long userId, String priority);

    //void save(Optional<Task> task);
}

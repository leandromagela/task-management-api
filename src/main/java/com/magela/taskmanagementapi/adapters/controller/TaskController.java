package com.magela.taskmanagementapi.adapters.controller;

import com.magela.taskmanagementapi.core.model.Task;
import com.magela.taskmanagementapi.core.usecase.task.CreateTaskUseCase;
import com.magela.taskmanagementapi.core.usecase.task.DeleteTaskUseCase;
import com.magela.taskmanagementapi.core.usecase.task.GetPendingTasksUseCase;
import com.magela.taskmanagementapi.core.usecase.task.UpdateTaskUseCase;
import com.magela.taskmanagementapi.core.usecase.task.CompleteTaskUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private CreateTaskUseCase createTaskUseCase;

    @Autowired
    private DeleteTaskUseCase deleteTaskUseCase;

    @Autowired
    private GetPendingTasksUseCase getPendingTasksUseCase;

    @Autowired
    private UpdateTaskUseCase updateTaskUseCase;

    @Autowired
    private CompleteTaskUseCase completeTaskUseCase;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        return ResponseEntity.ok(createTaskUseCase.execute(task));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        deleteTaskUseCase.execute(taskId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Task> updateTask(@RequestBody Task task) {
        return ResponseEntity.ok(updateTaskUseCase.execute(task));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Task>> getPendingTasks(@RequestParam Long userId) {
        return ResponseEntity.ok(getPendingTasksUseCase.execute(userId));
    }

    @GetMapping("/priority")
    public ResponseEntity<List<Task>> getPendingTasksByPriority(@RequestParam Long userId, @RequestParam String priority) {
        return ResponseEntity.ok(getPendingTasksUseCase.executeByPriority(userId, priority));
    }

    @PatchMapping("/{taskId}/complete")
    public ResponseEntity<Void> completeTask(@PathVariable Long taskId) {
        completeTaskUseCase.execute(taskId);
        return ResponseEntity.noContent().build();
    }
}

package com.magela.taskmanagementapi.adapters.controller;

import com.magela.taskmanagementapi.core.model.Task;
import com.magela.taskmanagementapi.core.usecase.task.CreateTaskUseCase;
import com.magela.taskmanagementapi.core.usecase.task.DeleteTaskUseCase;
import com.magela.taskmanagementapi.core.usecase.task.GetPendingTasksUseCase;
import com.magela.taskmanagementapi.core.usecase.task.UpdateTaskUseCase;
import com.magela.taskmanagementapi.core.usecase.task.CompleteTaskUseCase;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final CreateTaskUseCase createTaskUseCase;
    private final DeleteTaskUseCase deleteTaskUseCase;
    private final GetPendingTasksUseCase getPendingTasksUseCase;
    private final UpdateTaskUseCase updateTaskUseCase;
    private final CompleteTaskUseCase completeTaskUseCase;

    @Autowired
    public TaskController(CreateTaskUseCase createTaskUseCase, DeleteTaskUseCase deleteTaskUseCase,
                          GetPendingTasksUseCase getPendingTasksUseCase, UpdateTaskUseCase updateTaskUseCase,
                          CompleteTaskUseCase completeTaskUseCase) {
        this.createTaskUseCase = createTaskUseCase;
        this.deleteTaskUseCase = deleteTaskUseCase;
        this.getPendingTasksUseCase = getPendingTasksUseCase;
        this.updateTaskUseCase = updateTaskUseCase;
        this.completeTaskUseCase = completeTaskUseCase;
    }

    @PostMapping
    @Operation(summary = "Create a new task")
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Task createdTask = createTaskUseCase.execute(task);
        return ResponseEntity.ok(createdTask);
    }

    @DeleteMapping("/{taskId}")
    @Operation(summary = "Delete a task by ID")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        deleteTaskUseCase.execute(taskId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Operation(summary = "Update a task")
    public ResponseEntity<Task> updateTask(@Valid @RequestBody Task task) {
        Task updatedTask = updateTaskUseCase.execute(task);
        return ResponseEntity.ok(updatedTask);
    }

    @GetMapping("/pending")
    @Operation(summary = "Get pending tasks for a user, optionally filtered by priority")
    public ResponseEntity<List<Task>> getPendingTasks(@RequestParam(required = false) String priority) {
        List<Task> pendingTasks = getPendingTasksUseCase.execute(priority);
        return ResponseEntity.ok(pendingTasks);
    }

    @PatchMapping("/{taskId}/complete")
    @Operation(summary = "Mark a task as completed")
    public ResponseEntity<Void> completeTask(@PathVariable Long taskId) {
        completeTaskUseCase.execute(taskId);
        return ResponseEntity.noContent().build();
    }
}

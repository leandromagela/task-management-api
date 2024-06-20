package com.magela.taskmanagementapi.adapters.gateway;

import com.magela.taskmanagementapi.core.model.Priority;
import com.magela.taskmanagementapi.core.model.Task;
import com.magela.taskmanagementapi.core.model.User;
import com.magela.taskmanagementapi.core.repository.CustomTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CustomTaskRepositoryImpl implements CustomTaskRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Task> findPendingTasksByUserIdAndPriority(Long userId, String priority) {
        String sql = "SELECT * FROM tasks WHERE user_id = ? AND completed = false AND priority = ? ORDER BY priority";
        Priority priorityEnum;
        try {
            priorityEnum = Priority.valueOf(priority.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid priority value: " + priority);
        }
        return jdbcTemplate.query(sql, new Object[]{userId, priorityEnum.name()}, this::mapRowToTask);
    }

    private Task mapRowToTask(ResultSet rs, int rowNum) throws SQLException {
        Task task = new Task();
        task.setId(rs.getLong("id"));
        task.setDescription(rs.getString("description"));
        task.setPriority(Priority.valueOf(rs.getString("priority")));
        task.setCompleted(rs.getBoolean("completed"));
        User user = new User();
        user.setId(rs.getLong("user_id"));
        task.setUser(user);
        return task;
    }
}
package com.magela.taskmanagementapi.adapters.gateway;

import com.magela.taskmanagementapi.core.model.Priority;
import com.magela.taskmanagementapi.core.model.Task;
import com.magela.taskmanagementapi.core.model.User;
import com.magela.taskmanagementapi.core.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public abstract class TaskRepositoryImpl implements TaskRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void deleteById(Long taskId) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        jdbcTemplate.update(sql, taskId);
    }

    @Override
    public Optional<Task> findById(Long taskId) {
        String sql = "SELECT * FROM tasks WHERE id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{taskId}, this::mapRowToTask));
    }

    @Override
    public List<Task> findPendingTasksByUserId(Long userId) {
        String sql = "SELECT * FROM tasks WHERE user_id = ? AND completed = false ORDER BY priority";
        return jdbcTemplate.query(sql, new Object[]{userId}, this::mapRowToTask);
    }

    @Override
    public Task save(Task task) {
        if (task.getId() == null) {
            String insertSql = "INSERT INTO tasks (description, priority, completed, user_id) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(insertSql, task.getDescription(), task.getPriority().toString(), task.isCompleted(), task.getUser().getId());

            String selectSql = "SELECT * FROM tasks WHERE description = ? AND priority = ? AND completed = ? AND user_id = ? ORDER BY id DESC LIMIT 1";
            Task insertedTask = jdbcTemplate.queryForObject(selectSql, new Object[]{
                    task.getDescription(), task.getPriority().toString(), task.isCompleted(), task.getUser().getId()
            }, this::mapRowToTask);

            return insertedTask;
        } else {
            String updateSql = "UPDATE tasks SET description = ?, priority = ?, completed = ? WHERE id = ?";
            jdbcTemplate.update(updateSql, task.getDescription(), task.getPriority().toString(), task.isCompleted(), task.getId());
            return task;
        }
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

    // Unimplemented methods may throw exception to prevent accidental use
    @Override
    public void delete(Task entity) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends Task> entities) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public <S extends Task> List<S> saveAll(Iterable<S> entities) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public boolean existsById(Long aLong) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public List<Task> findAll() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public List<Task> findAllById(Iterable<Long> longs) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Not implemented");
    }
}
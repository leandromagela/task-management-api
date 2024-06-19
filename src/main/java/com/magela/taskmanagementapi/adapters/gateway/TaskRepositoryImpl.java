package com.magela.taskmanagementapi.adapters.gateway;

import com.magela.taskmanagementapi.core.model.Task;
import com.magela.taskmanagementapi.core.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{taskId}, (rs, rowNum) -> new Task(
                rs.getLong("id"),
                rs.getString("description"),
                rs.getString("priority"),
                rs.getBoolean("completed"),
                rs.getLong("user_id")
        )));
    }

    @Override
    public List<Task> findPendingTasksByUserId(Long userId) {
        String sql = "SELECT * FROM tasks WHERE user_id = ? AND completed = false ORDER BY priority";
        return jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> new Task(
                rs.getLong("id"),
                rs.getString("description"),
                rs.getString("priority"),
                rs.getBoolean("completed"),
                rs.getLong("user_id")
        ));
    }

    @Override
    public List<Task> findPendingTasksByUserIdAndPriority(Long userId, String priority) {
        String sql = "SELECT * FROM tasks WHERE user_id = ? AND completed = false AND priority = ? ORDER BY priority";
        return jdbcTemplate.query(sql, new Object[]{userId, priority}, (rs, rowNum) -> new Task(
                rs.getLong("id"),
                rs.getString("description"),
                rs.getString("priority"),
                rs.getBoolean("completed"),
                rs.getLong("user_id")
        ));
    }

    @Override
    public Task save(Task task) {
        if (task.getId() == null) {
            String insertSql = "INSERT INTO tasks (description, priority, completed, user_id) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(insertSql, task.getDescription(), task.getPriority(), task.isCompleted(), task.getUserId());

            String selectSql = "SELECT * FROM tasks WHERE description = ? AND priority = ? AND completed = ? AND user_id = ? ORDER BY id DESC LIMIT 1";
            Task insertedTask = jdbcTemplate.queryForObject(selectSql, new Object[]{
                    task.getDescription(), task.getPriority(), task.isCompleted(), task.getUserId()
            }, (rs, rowNum) -> new Task(
                    rs.getLong("id"),
                    rs.getString("description"),
                    rs.getString("priority"),
                    rs.getBoolean("completed"),
                    rs.getLong("user_id")
            ));

            return insertedTask;
        } else {
            String updateSql = "UPDATE tasks SET description = ?, priority = ?, completed = ? WHERE id = ?";
            jdbcTemplate.update(updateSql, task.getDescription(), task.getPriority(), task.isCompleted(), task.getId());
            return task;
        }
    }

    // Métodos não implementados podem lançar exceção para evitar uso acidental
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
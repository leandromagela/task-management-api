package com.magela.taskmanagementapi.adapters.gateway;

import com.magela.taskmanagementapi.core.model.Task;
import com.magela.taskmanagementapi.core.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void deleteById(Long taskId) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        jdbcTemplate.update(sql, taskId);
    }

    @Override
    public void delete(Task entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Task> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Task> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
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
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Task> findAll() {
        return null;
    }

    @Override
    public Iterable<Task> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
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
    public void save(Optional<Task> task) {
        //
    }

    @Override
    public Task save(Task task) {
        if (task.getId() == null) {
            // Inserir nova tarefa
            String insertSql = "INSERT INTO tasks (description, priority, completed, user_id) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(insertSql, task.getDescription(), task.getPriority(), task.isCompleted(), task.getUserId());

            // Recuperar a tarefa recém-inserida para obter o ID
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
            // Atualizar tarefa existente
            String updateSql = "UPDATE tasks SET description = ?, priority = ?, completed = ? WHERE id = ?";
            jdbcTemplate.update(updateSql, task.getDescription(), task.getPriority(), task.isCompleted(), task.getId());
            return task;
        }
    }
}

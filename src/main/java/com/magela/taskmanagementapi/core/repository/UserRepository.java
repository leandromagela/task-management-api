package com.magela.taskmanagementapi.core.repository;

import com.magela.taskmanagementapi.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
}

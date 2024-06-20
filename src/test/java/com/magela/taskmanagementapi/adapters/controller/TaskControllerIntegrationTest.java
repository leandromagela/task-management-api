package com.magela.taskmanagementapi.adapters.controller;

import com.magela.taskmanagementapi.core.model.User;
import com.magela.taskmanagementapi.core.model.UserRole;
import com.magela.taskmanagementapi.core.repository.TaskRepository;
import com.magela.taskmanagementapi.core.repository.UserRepository;
import com.magela.taskmanagementapi.service.TokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Test
    public void createTaskIntegrationTest() throws Exception {
        User user = new User("testUser", "testLogin", new BCryptPasswordEncoder().encode("testPassword"), UserRole.USER);
        userRepository.save(user);

        String token = tokenService.generateToken(user);

        mockMvc.perform(post("/tasks")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\": \"Test Task\", \"priority\": \"HIGH\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Test Task"));
    }
}

package com.magela.taskmanagementapi.infrastructure.config;

import com.magela.taskmanagementapi.core.model.User;
import com.magela.taskmanagementapi.core.model.UserRole;
import com.magela.taskmanagementapi.core.repository.UserRepository;
import com.magela.taskmanagementapi.service.TokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Test
    public void accessProtectedResourceWithoutToken() throws Exception {
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void accessProtectedResourceWithToken() throws Exception {
        User user = new User("testUser", "testLogin", new BCryptPasswordEncoder().encode("testPassword"), UserRole.USER);
        userRepository.save(user);
        String token = tokenService.generateToken(user);

        mockMvc.perform(get("/tasks")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}
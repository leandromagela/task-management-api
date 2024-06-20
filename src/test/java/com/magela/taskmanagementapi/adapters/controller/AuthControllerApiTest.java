package com.magela.taskmanagementapi.adapters.controller;

import com.magela.taskmanagementapi.TaskManagementApiApplication;
import com.magela.taskmanagementapi.service.AuthorizationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@ContextConfiguration(classes = TaskManagementApiApplication.class)
public class AuthControllerApiTest {

    @Autowired
    private MockMvc mockMvc;

    public AuthControllerApiTest(AuthorizationService authorizationService) {
    }

    @Test
    public void testLogin() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .contentType("application/json")
                        .content("{\"username\": \"user\", \"password\": \"password\"}"))
                .andExpect(status().isOk());
    }
}

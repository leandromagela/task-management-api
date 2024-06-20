package com.magela.taskmanagementapi.adapters.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.magela.taskmanagementapi.application.dto.AuthenticationDTO;
import com.magela.taskmanagementapi.application.dto.LoginResponseDTO;
import com.magela.taskmanagementapi.core.usecase.user.LoginUserUseCase;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginUserUseCase loginUserUseCase;

    @Test
    public void loginTest() throws Exception {
        AuthenticationDTO authDTO = new AuthenticationDTO("testUser", "testLogin", "testPassword");
        LoginResponseDTO responseDTO = new LoginResponseDTO("jwtToken");
        Mockito.when(loginUserUseCase.execute(Mockito.any())).thenReturn(responseDTO);

        ResultActions jwtToken = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"login\": \"testLogin\", \"password\": \"testPassword\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwtToken"));
    }
}
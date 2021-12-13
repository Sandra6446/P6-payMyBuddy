package com.api.payMyBuddy.controller;

import com.api.payMyBuddy.model.front.Login;
import com.api.payMyBuddy.service.LoginService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = LoginController.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginService loginService;

    private static Login login;

    @BeforeAll
    private static void setUp() {
        login = new Login("user@email.com", "pwd",null);
    }

    @Test
    void checkLogin() throws Exception {

        String response = "User authorized";

        // Test to check a good Login
        Mockito.when(loginService.checkLogin(ArgumentMatchers.any(Login.class))).thenReturn(ResponseEntity.ok(response));
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(login.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(response));

        // Test to check an empty Login
        Login loginEmpty = new Login();
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(loginEmpty.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
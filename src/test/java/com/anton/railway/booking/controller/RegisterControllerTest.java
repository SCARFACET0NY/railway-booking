package com.anton.railway.booking.controller;

import com.anton.railway.booking.entity.User;
import com.anton.railway.booking.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class RegisterControllerTest {
    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    UserService userService;
    @InjectMocks
    RegisterController registerController;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(registerController).build();
    }

    @Test
    void getRegisterPageTest() throws Exception {
        mockMvc.perform(get("/register/"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void registerTest() throws Exception {
        mockMvc.perform(post("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));

        verify(userService).save(any(User.class));
        verify(bCryptPasswordEncoder).encode(nullable(String.class));
    }
}
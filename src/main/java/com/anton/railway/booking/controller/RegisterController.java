package com.anton.railway.booking.controller;

import com.anton.railway.booking.entity.User;
import com.anton.railway.booking.entity.enums.AccountStatus;
import com.anton.railway.booking.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class RegisterController {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;

    public RegisterController(BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("user", new User());

        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        user.setAccountStatus(AccountStatus.CUSTOMER);
        user.setDateJoined(LocalDateTime.now());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.save(user);

        return "login";
    }
}

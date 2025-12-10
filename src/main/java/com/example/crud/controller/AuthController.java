package com.example.crud.controller;

import com.example.crud.entity.User;
import com.example.crud.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid User user, BindingResult result, Model model) {
        if (userService.findByUsername(user.getUsername()) != null) {
            result.rejectValue("username", "error.user", "Пользователь с таким именем уже существует");
        }

        if (result.hasErrors()) {
            return "register";
        }

        userService.registerNewUser(user);
        model.addAttribute("message", "Регистрация прошла успешно!");
        return "login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
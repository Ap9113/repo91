package com.example.ecommerce.controller;

import com.example.ecommerce.model.User;
import com.example.ecommerce.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute @Valid User user, BindingResult br, Model m) {
        if (br.hasErrors()) {
            return "register";
        }
        try {
            userService.register(user.getUsername(), user.getPassword());
        } catch (IllegalArgumentException e) {
            m.addAttribute("error", e.getMessage());
            return "register";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}

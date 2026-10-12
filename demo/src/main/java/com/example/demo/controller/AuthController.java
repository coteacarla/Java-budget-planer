package com.example.demo.controller;

import com.example.demo.dto.RegistrationRequest;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }


    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("registration", new RegistrationRequest());
        return "register";
    }



    @PostMapping("/register")
    public String register(@ModelAttribute RegistrationRequest registration, Model model) {
        if (userRepository.findByUsername(registration.getUsername()).isPresent()) {
            model.addAttribute("error", "Username is already registered.");
            return "register";
        }

        com.example.demo.model.User user = new com.example.demo.model.User();
        user.setUsername(registration.getUsername());
        user.setPassword(passwordEncoder.encode(registration.getPassword()));
        userRepository.save(user);
        return "redirect:/login";
    }
}

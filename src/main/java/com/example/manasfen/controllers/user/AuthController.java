package com.example.manasfen.controllers.user;


import com.example.manasfen.model.entyties.User;
import com.example.manasfen.model.payload.LoginRequest;
import com.example.manasfen.model.payload.UserCreate;
import com.example.manasfen.services.users.UsersService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsersService usersService;

    @GetMapping("/login")
    public String login() {
        return "public/login";
    }

    @PostMapping("register")
    public String register(@Valid UserCreate userCreate,
                           BindingResult bindingResult,
                           HttpSession session) {
        if (bindingResult.hasErrors()) {
            session.setAttribute("payload", userCreate);
            session.setAttribute("errors", bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage))
            );

            return "redirect:/?errors=1#register";
        } else {
            User user = usersService.createUser(userCreate);
            session.removeAttribute("payload");
            session.removeAttribute("errors");
            return "redirect:/auth/login";
        }
    }
}

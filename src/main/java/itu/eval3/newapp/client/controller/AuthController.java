package itu.eval3.newapp.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/auth")
public class AuthController {
    
    @GetMapping("/login")
    public String loginForm(){
        return "/auth/login";
    }

    @PostMapping("login")
    public String doLogin(){
        return "redirect:/";
    }
}

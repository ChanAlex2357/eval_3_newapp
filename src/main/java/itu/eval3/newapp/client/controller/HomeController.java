package itu.eval3.newapp.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String home(){
        return "dashboard"; // must match home.html under templates/
    }

    @GetMapping("/")
    public String root(){
        return "redirect:/home"; // must match home.html under templates/
    }
}

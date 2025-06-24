package itu.eval3.newapp.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/calendar")
public class Calendar {
    @GetMapping
    public String calendarShow() {
        return "calendar";
    }
    
    
}

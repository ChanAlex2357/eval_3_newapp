package itu.eval3.newapp.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/calendar")
public class CalendarController {
    @GetMapping
    public String calendarShow() {
        return "calendar";
    }
    

    @PostMapping("/events")
    public String createEvent(HttpSession session) {
        return "";
    }
    
    
}

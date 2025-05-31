package itu.eval3.newapp.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import itu.eval3.newapp.client.models.user.UserErpNext;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
    
    @GetMapping("/profile")
    public String getMethodName(HttpSession session,Model model) {
        UserErpNext user = (UserErpNext) session.getAttribute("user");
        if (user == null) {
            return "redirect:/auth/login";
        }
        model.addAttribute("user", user);
        return "/user/profile";
    }

    @GetMapping("/edit")
    public String showEditProfilePage(HttpSession session, Model model) {
        UserErpNext user = (UserErpNext) session.getAttribute("user");
        if (user == null) {
            return "redirect:/auth/login";
        }
        model.addAttribute("user", user);
        return "profile-edit"; // You'll need to create this template
    }
}

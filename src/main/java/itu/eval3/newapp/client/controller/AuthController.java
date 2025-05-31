package itu.eval3.newapp.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.api.requests.LoginRequest;
import itu.eval3.newapp.client.models.user.UserApiDTO;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.auth.AuthService;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String loginForm(RedirectAttributes redirectAttributes, HttpSession session, Model model){

        return "/auth/login";
    }

    @PostMapping("login")
    public String doLogin(@ModelAttribute LoginRequest credentials, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpSession session){

        if (bindingResult.hasErrors()) {
            return "redirect:/auth/login";
        }
        try {
            UserApiDTO userResponse = authService.callLogin(credentials);
            
            UserErpNext user = userResponse.getUser();
            session.setAttribute("user", user);

            return "redirect:/";
        } catch (ERPNexException er) {
            redirectAttributes.addFlashAttribute("err",er.getMessage());
            return "redirect:/auth/login";
        } catch (Exception e) {
            redirectAttributes.addAttribute("err",e.getMessage());
            return "redirect:/auth/login";
        }
        
    }
}

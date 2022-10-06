package com.example.crud_app.controller.user;

import com.example.crud_app.services.user.set_user.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {
    @Autowired
    UserAuthService userAuthService;

    @GetMapping("/signup")
    public String getSignUpPage(HttpServletRequest request, Model model){
        model.addAttribute("error", request.getParameter("error"));
        return "user/signup";
    }

    @PostMapping("/signup")
    public String signUpUser(HttpServletRequest request){
        userAuthService.signUpUser(request);
        return "redirect:/login";
    }
}

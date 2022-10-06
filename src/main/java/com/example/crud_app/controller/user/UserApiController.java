package com.example.crud_app.controller.user;

import com.example.crud_app.exceptions.page_not_found.PageNotFoundException;
import com.example.crud_app.services.user.set_user.UserAuthApiService;
import com.example.crud_app.services.user.set_user.UserAuthApiServiceImpl;
import com.example.crud_app.services.user.set_user.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class UserApiController {
    @Autowired
    public void setUserAuthApiService(UserAuthApiService userAuthApiService) {
        this.userAuthApiService = userAuthApiService;
    }
    UserAuthApiService userAuthApiService;

    @GetMapping("/signup")
    public String getSignUpPage(HttpServletRequest request, Model model){
        throw new PageNotFoundException();
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signUpUser(HttpServletRequest request){
        return userAuthApiService.signUpUser(request);
    }
}

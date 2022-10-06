package com.example.crud_app.controller.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {

    public String test(){
        System.out.println("test is running");
        return "commons/test";
    }
}

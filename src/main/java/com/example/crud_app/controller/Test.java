package com.example.crud_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Test {

    @GetMapping("/test")
    public String test(){
        System.out.println("test is running");
        return "commons/test";
    }
}

package com.example.crud_app.controller.test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestApiController {
    @GetMapping("/")
    public ResponseEntity<Object> test(){
        System.out.println("test is running");
        return new ResponseEntity<>("Tests are running fine", HttpStatus.OK);
    }
}

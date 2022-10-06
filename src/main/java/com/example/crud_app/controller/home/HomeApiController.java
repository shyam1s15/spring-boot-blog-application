package com.example.crud_app.controller.home;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeApiController {
    @GetMapping("/api/")
    public ResponseEntity<Object> home(){
        return new ResponseEntity<> ("Welcome to Blog application", HttpStatus.OK);
    }
}

package com.example.crud_app.services.user.set_user;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface UserAuthApiService {
    public ResponseEntity<Object> signUpUser(HttpServletRequest request);
}

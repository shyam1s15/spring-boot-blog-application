package com.example.crud_app.services.draft;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

public interface DraftApiService {
    public ResponseEntity<Object> getDraftsPosts(HttpServletRequest request);
}

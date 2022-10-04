package com.example.crud_app.services.custom_error;

import javax.servlet.http.HttpServletRequest;

public interface CustomErrorService {
    public String handleError(HttpServletRequest request);
}

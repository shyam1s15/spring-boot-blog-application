package com.example.crud_app.controller;

import com.example.crud_app.exceptions.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = PageNotFoundException.class)
    public String pageNotFoundException(PageNotFoundException exception){
        return "error/404";
    }

    @ExceptionHandler(value = AuthFailedException.class)
    public String authFailedException(AuthFailedException exception){
        return "error/401";
    }

    @ExceptionHandler(value = ServerException.class)
    public String serverException(ServerException exception){
        return "error/500";
    }

    @ExceptionHandler(value = OtherException.class)
    public String otherException(OtherException exception){
        return "error/error";
    }

    @ExceptionHandler(value = DataNotFoundException.class)
    public String dataNotFoundException(DataNotFoundException exception){
        return "error/204";
    }

    @ExceptionHandler(value = UnAuthorizedAccessException.class)
    public String unAuthorizedAccessException(UnAuthorizedAccessException exception){
        return "error/204";
    }

    @ExceptionHandler(value = InsufficientAccessException.class)
    public String insufficientAccessException(InsufficientAccessException exception){
        return "error/badAccess";
    }
    @ExceptionHandler(value = UserAlreadyExists.class)
    public String userAlreadyExists(UserAlreadyExists exception){
        return "redirect:/signup?error=userAlreadyExists";
    }
}

package com.example.crud_app.controller.exceptions;

import com.example.crud_app.exceptions.auth_failed.AuthFailedException;
import com.example.crud_app.exceptions.data_not_found.DataNotFoundException;
import com.example.crud_app.exceptions.insufficient_access.InsufficientAccessException;
import com.example.crud_app.exceptions.other.OtherException;
import com.example.crud_app.exceptions.page_not_found.PageNotFoundException;
import com.example.crud_app.exceptions.server.ServerException;
import com.example.crud_app.exceptions.unauthorized_access.UnAuthorizedAccessException;
import com.example.crud_app.exceptions.user_already_exists.UserAlreadyExistsException;
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
    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public String userAlreadyExists(UserAlreadyExistsException exception){
        return "redirect:/signup?error=userAlreadyExists";
    }
}

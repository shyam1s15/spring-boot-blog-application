package com.example.crud_app.controller.exceptions;

import com.example.crud_app.exceptions.auth_failed.AuthFailedApiException;
import com.example.crud_app.exceptions.data_not_found.DataNotFoundApiException;
import com.example.crud_app.exceptions.insufficient_access.InsufficientAccessApiException;
import com.example.crud_app.exceptions.other.OtherApiException;
import com.example.crud_app.exceptions.page_not_found.PageNotFoundApiException;
import com.example.crud_app.exceptions.server.ServerApiException;
import com.example.crud_app.exceptions.unauthorized_access.UnAuthorizedAccessApiException;
import com.example.crud_app.exceptions.user_already_exists.UserAlreadyExistsApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionController {

    @ExceptionHandler(value = PageNotFoundApiException.class)
    public ResponseEntity<Object> pageNotFoundApiException(PageNotFoundApiException exception){
        return new ResponseEntity<>("Page Not Found 404", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = AuthFailedApiException.class)
    public ResponseEntity<Object> authFailedApiException(AuthFailedApiException exception){
        return new ResponseEntity<>("Authentication Failed", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = ServerApiException.class)
    public ResponseEntity<Object> serverApiException(ServerApiException exception){
        return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = OtherApiException.class)
    public ResponseEntity<Object> otherApiException(OtherApiException exception){
        return new ResponseEntity<>("Some Unknown Error", HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(value = DataNotFoundApiException.class)
    public ResponseEntity<Object> dataNotFoundApiException(DataNotFoundApiException exception){
        return new ResponseEntity<>("Data Not Found Error", HttpStatus.OK);
    }

    @ExceptionHandler(value = UnAuthorizedAccessApiException.class)
    public ResponseEntity<Object> unAuthorizedAccessApiException(UnAuthorizedAccessApiException exception){
        return new ResponseEntity<>("UnAuthorized Access Error", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = InsufficientAccessApiException.class)
    public ResponseEntity<Object> insufficientAccessApiException(InsufficientAccessApiException exception){
        return new ResponseEntity<>("Insufficient Access to use api", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = UserAlreadyExistsApiException.class)
    public ResponseEntity<Object> userAlreadyApiExists(UserAlreadyExistsApiException exception){
        return new ResponseEntity<>("User already exists", HttpStatus.OK);
    }
}

package com.example.crud_app.controller.custom_error;

import com.example.crud_app.exceptions.auth_failed.AuthFailedException;
import com.example.crud_app.exceptions.other.OtherException;
import com.example.crud_app.exceptions.page_not_found.PageNotFoundException;
import com.example.crud_app.exceptions.server.ServerException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public void handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                throw new PageNotFoundException();
            }
            else if(statusCode == HttpStatus.UNAUTHORIZED.value()) {
                throw new AuthFailedException();
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                throw new ServerException();
            }
        }
        else{
            throw new OtherException();
        }
    }


}

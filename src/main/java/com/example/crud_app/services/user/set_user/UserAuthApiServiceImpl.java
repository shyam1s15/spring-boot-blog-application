package com.example.crud_app.services.user.set_user;

import com.example.crud_app.exceptions.user_already_exists.UserAlreadyExistsApiException;
import com.example.crud_app.exceptions.user_already_exists.UserAlreadyExistsException;
import com.example.crud_app.jpa.UserRepository;
import com.example.crud_app.model.User;
import com.example.crud_app.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class UserAuthApiServiceImpl implements UserAuthApiService{
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    private UserRepository userRepository;

    @Override
    public ResponseEntity<Object> signUpUser(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            throw new UserAlreadyExistsApiException();
        } else {
            User user = new User();
            user.setEmail(request.getParameter("email"));
            user.setPassword(request.getParameter("password"));
            user.setUsername(request.getParameter("username"));
            user.setRole(Constants.AUTHOR);
            userRepository.save(user);
        }
        return new ResponseEntity<>("User Created Successfully", HttpStatus.OK);
    }
}

package com.example.crud_app.services.user.app_user;

import com.example.crud_app.jpa.UserRepository;
import com.example.crud_app.model.User;
import com.example.crud_app.services.user.authenticated_user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AppUserDetailsImpl implements AppUserDetails {
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User details not found for the user "+username);
        }
        else{
            UserServiceImpl userService = new UserServiceImpl();
            userService.setUser(optionalUser.get());
            return userService;
        }
    }

}

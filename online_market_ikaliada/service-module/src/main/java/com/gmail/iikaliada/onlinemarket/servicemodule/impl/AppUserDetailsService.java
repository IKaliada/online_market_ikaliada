package com.gmail.iikaliada.onlinemarket.servicemodule.impl;

import com.gmail.iikaliada.onlinemarket.servicemodule.UserService;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.AppUserPrincipal;
import com.gmail.iikaliada.onlinemarket.servicemodule.model.LoginDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final static String USERNAME_EXCEPTION_MESSAGE = "User is not found";
    private final UserService userService;

    public AppUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        LoginDTO userDTO = userService.getUsersByUsername(username);

        if (userDTO == null) {
            throw new UsernameNotFoundException(USERNAME_EXCEPTION_MESSAGE);
        }
        return new AppUserPrincipal(userDTO);
    }
}

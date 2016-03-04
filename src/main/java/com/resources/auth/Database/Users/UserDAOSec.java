package com.resources.auth.Database.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class provides access to database for spring security.
 * It uses userService to get records from base.
 * @author TanVD
 */
@Service("userServiceSec")
@Transactional
public class UserDAOSec implements UserDetailsService{


    public void setUserService(UserDAO userService) {
        this.userService = userService;
    }

    @Autowired
    private UserDAO userService;

    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserDetails user = userService.loadUserByUsername(login);
        if (user == null){
            throw new UsernameNotFoundException("Username not found");
        }
        return user;
    }
}
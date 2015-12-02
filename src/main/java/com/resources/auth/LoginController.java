package com.resources.auth;

/**
 * Created by tanvd on 06.11.15.
 */

import  javax.annotation.*;

import com.resources.auth.database.Users.User;
import com.resources.auth.database.Users.UserAuthority;
import com.resources.auth.database.Users.UserDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Random;

@Controller
public class LoginController {

    @Resource(name="userService")
    private UserDAO userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String login(ModelMap model) {
        model.addAttribute("error", false);
        return "login";
    }

    @RequestMapping(value = "/logErr", method = RequestMethod.GET)
    public String loginError(ModelMap model) {
        model.addAttribute("error", true);
        return "login";
    }
}
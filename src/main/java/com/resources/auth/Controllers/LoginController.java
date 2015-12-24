package com.resources.auth.Controllers;

/**
 * Created by tanvd on 06.11.15.
 */

import  javax.annotation.*;

import com.resources.auth.Database.Users.UserDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
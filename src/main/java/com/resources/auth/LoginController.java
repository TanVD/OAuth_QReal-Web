package com.resources.auth;

/**
 * Created by tanvd on 06.11.15.
 */

import javax.servlet.http.HttpServletRequest;
import  javax.annotation.*;

import com.resources.auth.database.UserAuthority;
import com.resources.auth.database.UserDAO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.resources.auth.database.User;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

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
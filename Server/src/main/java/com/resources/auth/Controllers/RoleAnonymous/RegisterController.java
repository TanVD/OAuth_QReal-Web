package com.resources.auth.Controllers.RoleAnonymous;

/**
 * Created by tanvd on 07.11.15.
 */
import javax.servlet.http.HttpServletRequest;
import  javax.annotation.*;
import java.io.*;

import com.resources.auth.Database.Users.UserAuthority;
import com.resources.auth.Database.Users.UserDAO;
import com.resources.auth.Database.Users.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class RegisterController {

    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);


    @Resource(name="userService")
    private UserDAO userService;

    @Resource(name="passwordEncoder")
    PasswordEncoder passwordEncoder;

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String login(ModelMap model) {
        model.addAttribute("error", false);
        return "ROLE_ANONYMOUS/registerView";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String registerCheck(ModelMap model, HttpServletRequest request) throws IOException{
        String name = request.getParameter("login");
        String pwd1 = request.getParameter("pwd1");
        String pwd2 = request.getParameter("pwd2");
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new UserAuthority("ROLE_USER"));

        if (!pwd1.equals(pwd2)) {
            model.addAttribute("errorPasswordsNotMatch", true);
            return "ROLE_ANONYMOUS/registerView";
        }

        List userWithSameLogin = userService.get(name);
        if (!userWithSameLogin.isEmpty())
        {
            model.addAttribute("errorLoginAlreadyRegistered", true);
            return "ROLE_ANONYMOUS/register";
        }

        User user = new User(name, passwordEncoder.encode(pwd1), authorities);
        userService.add(user);
        logger.trace("User {} registered through standard way", user.getUsername());

        return "redirect:/";
    }
}

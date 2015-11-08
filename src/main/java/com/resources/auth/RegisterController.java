package com.resources.auth;

/**
 * Created by tanvd on 07.11.15.
 */
import javax.servlet.http.HttpServletRequest;
import  javax.annotation.*;

import com.resources.auth.database.UserAuthority;
import com.resources.auth.database.UserDAO;
import com.resources.auth.database.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.resources.auth.database.User;

import java.util.ArrayList;

@Controller
public class RegisterController {
    @Resource(name="userService")
    private UserDAO userService;

    @Resource(name="passwordEncoder")
    PasswordEncoder passwordEncoder;

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String login(ModelMap model) {
        model.addAttribute("error", false);
        return "register";
    }

    @RequestMapping(value = "registerCheck", method = RequestMethod.POST)
    public String userCheck(ModelMap model, HttpServletRequest request) {
        String name = request.getParameter("login");
        String pwd1 = request.getParameter("pwd1");
        String pwd2 = request.getParameter("pwd2");
        ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new UserAuthority("ROLE_USER"));

        if (!pwd1.equals(pwd2)) {
            model.addAttribute("error", true);
            return "register";
        }
        User user = new User(name, passwordEncoder.encode(pwd1), authorities);
        userService.add(user);
        return "login";
    }
}

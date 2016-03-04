package com.resources.auth.Controllers;

/**
 * Created by tanvd on 07.11.15.
 */
import javax.servlet.http.HttpServletRequest;
import  javax.annotation.*;
import java.io.*;

import com.resources.auth.Database.Server.Server;
import com.resources.auth.Database.Server.ServerDAO;
import com.resources.auth.Database.Users.UserAuthority;
import com.resources.auth.Database.Users.UserDAO;
import com.resources.auth.Database.Users.User;
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
    @Resource(name="userService")
    private UserDAO userService;

    @Resource(name="serverService")
    private ServerDAO serverService;

    @Resource(name="passwordEncoder")
    PasswordEncoder passwordEncoder;

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String login(ModelMap model) {
        model.addAttribute("error", false);
        return "register";
    }

    private void broadcastToServers(User user) throws IOException
    {
        List<Server> servers = serverService.getAll();
        for(Server server : servers)
        {
            server.sendLogin(user.getUsername());
        }
    }

    @RequestMapping(value = "registerCheck", method = RequestMethod.POST)
    public String userCheck(ModelMap model, HttpServletRequest request) throws IOException{
        String name = request.getParameter("login");
        String pwd1 = request.getParameter("pwd1");
        String pwd2 = request.getParameter("pwd2");
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new UserAuthority("ROLE_USER"));

        if (!pwd1.equals(pwd2)) {
            model.addAttribute("errorPasswordsNotMatch", true);
            return "register";
        }

        List userWithSameLogin = userService.get(name);
        if (!userWithSameLogin.isEmpty())
        {
            model.addAttribute("errorLoginAlreadyRegistered", true);
            return "register";
        }

        User user = new User(name, passwordEncoder.encode(pwd1), authorities);
        userService.add(user);
        broadcastToServers(user);
        return "redirect:/";
    }
}

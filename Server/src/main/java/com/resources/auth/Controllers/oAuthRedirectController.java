package com.resources.auth.Controllers;

import com.resources.auth.Security.AuthenticatedUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by tanvd on 03.04.16.
 */
@Controller
public class oAuthRedirectController {

    @RequestMapping(value = "oauth/google", method = RequestMethod.GET)
    public String routeGoogle(ModelMap model) {
        String role = AuthenticatedUser.getAuthenticatedUserAuthority();
        if (role.contains("ROLE_ADMIN")) {
            return "redirect:/tableRegistered";
        }
        else if (role.contains("ROLE_USER")) {
            return "redirect:/userServers";
        }
        return"redirect:/";
    }
}

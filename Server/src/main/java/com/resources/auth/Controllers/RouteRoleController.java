package com.resources.auth.Controllers;

import com.resources.auth.Security.AuthenticatedUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by tanvd on 15.12.15.
 */
@Controller
public class RouteRoleController {
    @RequestMapping(value = "routeRole", method = RequestMethod.GET)
    public String routeRole(ModelMap model) {
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

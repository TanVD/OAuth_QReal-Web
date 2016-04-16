package com.resources.auth.Controllers;

import com.resources.auth.Security.Utils.AuthenticatedUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Optional;

/**
 * Created by tanvd on 03.04.16.
 */
@Controller
@RequestMapping("oauth")
public class oAuthRedirectController {

    private static final Logger logger = LoggerFactory.getLogger(oAuthRedirectController.class);


    @RequestMapping(value = {"google"},  method = RequestMethod.GET)
    public String routeGoogle(@RequestParam("redirect") Optional<String> redirect, ModelMap model) throws UnsupportedEncodingException {
        if (redirect.isPresent() && redirect.get() != "")
        {
            String redirectDecoded = URLDecoder.decode(redirect.get(), "UTF-8");
            logger.trace("User {} logged in via google and redirected to {}", AuthenticatedUser.getAuthenticatedUserName(),
                    redirectDecoded);
            return "redirect:" + redirectDecoded;
        }
        String role = AuthenticatedUser.getAuthenticatedUserAuthority();
        if (role.contains("ROLE_ADMIN")) {
            logger.trace("Admin {} logged in via google and redirected to tableRegistered", AuthenticatedUser.getAuthenticatedUserName());
            return "redirect:/tableRegistered";
        }
        else if (role.contains("ROLE_USER")) {
            logger.trace("User {} logged in via google and redirected to userServers", AuthenticatedUser.getAuthenticatedUserName());
            return "redirect:/userServers";
        }
        return "redirect:/";
    }

    @RequestMapping(value = {"github"},  method = RequestMethod.GET)
    public String routeGithub(@RequestParam("redirect") Optional<String> redirect, ModelMap model) throws UnsupportedEncodingException {
        if (redirect.isPresent() && redirect.get() != "")
        {
            String redirectDecoded = URLDecoder.decode(redirect.get(), "UTF-8");
            logger.trace("User {} logged in via github and redirected to {}", AuthenticatedUser.getAuthenticatedUserName(),
                    redirectDecoded);
            return "redirect:" + redirectDecoded;
        }
        String role = AuthenticatedUser.getAuthenticatedUserAuthority();
        if (role.contains("ROLE_ADMIN")) {
            logger.trace("Admin {} logged in via github and redirected to tableRegistered", AuthenticatedUser.getAuthenticatedUserName());
            return "redirect:/tableRegistered";
        }
        else if (role.contains("ROLE_USER")) {
            logger.trace("User {} logged in via github and redirected to userServers", AuthenticatedUser.getAuthenticatedUserName());
            return "redirect:/userServers";
        }
        return "redirect:/";
    }
}

package com.resources.auth.Controllers;

import com.resources.auth.Security.AuthenticatedUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Optional;

/**
 * Created by tanvd on 03.04.16.
 */
@Controller
public class oAuthRedirectController {

    @RequestMapping(value = {"/oauth/google"},  method = RequestMethod.GET)
    public String routeGoogle(@RequestParam("redirect") Optional<String> redirect, ModelMap model) throws UnsupportedEncodingException {
        if (redirect.isPresent() && redirect.get() != "")
        {
            String redirectDecoded = URLDecoder.decode(redirect.get(), "UTF-8");
            return "redirect:" + redirectDecoded;
        }
        String role = AuthenticatedUser.getAuthenticatedUserAuthority();
        if (role.contains("ROLE_ADMIN")) {
            return "redirect:/tableRegistered";
        }
        else if (role.contains("ROLE_USER")) {
            return "redirect:/userServers";
        }
        return "redirect:/";
    }

    @RequestMapping(value = {"/oauth/github"},  method = RequestMethod.GET)
    public String routeGithub(@RequestParam("redirect") Optional<String> redirect, ModelMap model) throws UnsupportedEncodingException {
        if (redirect.isPresent() && redirect.get() != "")
        {
            String redirectDecoded = URLDecoder.decode(redirect.get(), "UTF-8");
            return "redirect:" + redirectDecoded;
        }
        String role = AuthenticatedUser.getAuthenticatedUserAuthority();
        if (role.contains("ROLE_ADMIN")) {
            return "redirect:/tableRegistered";
        }
        else if (role.contains("ROLE_USER")) {
            return "redirect:/userServers";
        }
        return "redirect:/";
    }
}

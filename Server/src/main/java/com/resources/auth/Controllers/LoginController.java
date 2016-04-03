package com.resources.auth.Controllers;

/**
 * Created by tanvd on 06.11.15.
 */

import  javax.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.resources.auth.Database.Users.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

@Controller
public class LoginController {

    @Resource(name="userService")
    private UserDAO userService;

    //Used to retrieve inital request which could be intercepted by SpringSec
    //It is needed cause standard filter will redirect to initial url, but not oauth filters
    private @Autowired
    HttpServletRequest request;

    private @Autowired
    HttpServletResponse response;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String login(ModelMap model) throws UnsupportedEncodingException {

        SavedRequest savedRequest =  new HttpSessionRequestCache().getRequest(request, response);

        if (savedRequest != null) {
            String request = savedRequest.getRedirectUrl();

            String redirect = URLEncoder.encode(request, "UTF-8");
            model.addAttribute("redirect", redirect);
        }

        model.addAttribute("error", false);
        return "login";
    }

    @RequestMapping(value = "/logErr", method = RequestMethod.GET)
    public String loginError(ModelMap model) {
        model.addAttribute("error", true);
        return "login";
    }
}
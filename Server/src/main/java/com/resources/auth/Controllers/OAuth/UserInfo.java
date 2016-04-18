package com.resources.auth.Controllers.OAuth;

import com.resources.auth.Database.Users.User;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.*;

/**
 * Created by tanvd on 01.04.16.
 */
@Controller
@RequestMapping("oauth")
public class UserInfo {

    private static final Logger logger = LoggerFactory.getLogger(UserInfo.class);


    /**
     * This class is used to return authentication  information in JSON format.
     * @return Password and id in JSON
     * @throws IOException
     */
    @RequestMapping(value = "userInfo", method = RequestMethod.GET)
    @ResponseBody
    public String tokenString() throws IOException, JSONException {

        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<String> roles = authenticatedUser.getAuthoritiesInStringList();

        String login = authenticatedUser.getUsername();


        JSONObject jsonMap = new JSONObject();
        jsonMap.put("roles", roles);
        jsonMap.put("id", login);

        String rolesInString = "";
        for (String role : roles) {
            rolesInString += role + " ";
        }
        logger.trace("User info was requested of user {} with scopes {}", login, rolesInString);

        String resultString = jsonMap.toString();
        return resultString;

    }
}

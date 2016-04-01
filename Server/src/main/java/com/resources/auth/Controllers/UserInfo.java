package com.resources.auth.Controllers;

import com.resources.auth.Database.Users.User;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tanvd on 01.04.16.
 */
@Controller
public class UserInfo {

    @RequestMapping(value = "/oauth/userInfo")
    @ResponseBody
    public String tokenString() throws IOException {
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String password = authenticatedUser.getPassword();
        String login = authenticatedUser.getUsername();
        Map<String, String> jsonMap = new HashMap<String, String>();
        jsonMap.put("password", password);
        jsonMap.put("id", login);

        ObjectMapper mapper = new ObjectMapper();
        String jsonFromMap = mapper.writeValueAsString(jsonMap);
        return jsonFromMap;

    }
}

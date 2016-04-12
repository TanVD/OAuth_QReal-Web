package com.resources.auth.Controllers;

import com.resources.auth.Database.Users.User;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tanvd on 01.04.16.
 */
@Controller
@RequestMapping("oauth")
public class UserInfo {

    /**
     * This class is used to return authentication  information in JSON format.
     * @return Password and id in JSON
     * @throws IOException
     */
    @RequestMapping(value = "userInfo", method = RequestMethod.GET)
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

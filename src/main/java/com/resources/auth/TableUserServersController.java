package com.resources.auth;

import com.resources.auth.database.Server.Server;
import com.resources.auth.database.Server.ServerDAO;
import com.resources.auth.database.Users.User;
import com.resources.auth.database.Users.UserDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanvd on 01.12.15.
 */
@Controller
public class TableUserServersController {
    @Resource(name="serverService")
    private ServerDAO serverService;

    @RequestMapping(value = "userServers", method = RequestMethod.GET)
    public ModelAndView tableUsersPrepare(ModelMap model, HttpServletRequest request) {
        ModelAndView table = new ModelAndView("userServers");
        List<Server> result = serverService.getAll();
        ArrayList<String> ips = new ArrayList<String>();
        for (Server server : result)
        {
            ips.add(server.getIp());
        }
        table.addObject("objects", ips);
        return table;
    }
}

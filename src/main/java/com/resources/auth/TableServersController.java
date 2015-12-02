package com.resources.auth;

import com.resources.auth.database.Server.ServerDAO;
import  com.resources.auth.database.Server.Server;
import com.resources.auth.database.Users.User;
import com.resources.auth.database.Users.UserDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Created by tanvd on 30.11.15.
 */
@Controller
public class TableServersController {

    @Resource(name="serverService")
    private ServerDAO serverService;

    @Resource(name = "userService")
    private UserDAO userService;

    @RequestMapping(value = "servers", method = RequestMethod.GET)
    public ModelAndView tableServersPrepare(ModelMap model, HttpServletRequest request) {
        ModelAndView table = new ModelAndView("servers");
        List<Server> result = serverService.getAll();
        table.addObject("objects", result);
        return table;
    }

    private void broadcastUsersOnServer(Server server) throws IOException{
        List<User> users = userService.getAll();
        for (User user : users) {
            server.sendLogin(user.getUsername());
        }
    }

    @RequestMapping(value = "servers/newServerAdded", method = RequestMethod.POST)
    public String serverCheck(ModelMap model, HttpServletRequest request) throws IOException {
        String ip = request.getParameter("ip");
        String format = request.getParameter("format");
        Server server = new Server(ip, format);
        serverService.add(server);
        broadcastUsersOnServer(server);
        return "redirect:/servers";
    }
}

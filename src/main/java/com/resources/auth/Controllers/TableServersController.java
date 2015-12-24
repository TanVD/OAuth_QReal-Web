package com.resources.auth.Controllers;

import com.resources.auth.Database.Server.ServerDAO;
import  com.resources.auth.Database.Server.Server;
import com.resources.auth.Database.Users.User;
import com.resources.auth.Database.Users.UserDAO;
import com.resources.auth.Security.AuthenticatedUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanvd on 30.11.15.
 */
@Controller
public class TableServersController {

    List<Server> serversInstantiated = new ArrayList<Server>();

    @Resource(name="serverService")
    private ServerDAO serverService;

    @Resource(name = "userService")
    private UserDAO userService;



    @RequestMapping(value = "servers", method = RequestMethod.GET)
    public ModelAndView tableServersPrepare(ModelMap model, HttpServletRequest request) {
        ModelAndView table = new ModelAndView("servers");
        if (serversInstantiated == null) {
            List<Server> result = serverService.getAll();
        }
        table.addObject("haveErrors", checkErrors());
        table.addObject("objects", serversInstantiated);
        table.addObject("name", AuthenticatedUser.getAuthenticatedUserName());
        return table;
    }

    private boolean checkErrors() {
        if (serversInstantiated == null) {
            return false;
        }
        for (Server server : serversInstantiated) {
            if (server.getConnectionState() == Server.State.Error) {
                return true;
            }
        }
        return false;
    }

    private void broadcastUsersOnServer(Server server) throws IOException{
        List<User> users = userService.getAll();
        for (User user : users) {
            server.sendLogin(user.getUsername());
            if (server.getConnectionState() == Server.State.Error) {
                return;
            }
        }
    }

    @RequestMapping(value = "servers/addNewServer", method = RequestMethod.GET)
    public ModelAndView addServer(ModelMap model, HttpServletRequest request) throws IOException{
        ModelAndView modelView = new ModelAndView("addServer");
        modelView.addObject("name", AuthenticatedUser.getAuthenticatedUserName());
        return modelView;
    }

    @RequestMapping(value = "servers/newServerAdded", method = RequestMethod.POST)
    public String serverCheck(ModelMap model, HttpServletRequest request) throws IOException{
        String ip = request.getParameter("ip");
        String format = request.getParameter("format");
        String name = request.getParameter("name");
        Server server = new Server(ip, format, name);
        serverService.add(server);
        serversInstantiated.add(server);
        broadcastUsersOnServer(server);
        return "redirect:/servers";
    }



    //TODO Проверить работу
    @RequestMapping(value = "servers/refreshServer/{serverIp:.+}", method = RequestMethod.GET)//need :.+ because of truncating the extension by default
    public String serverRefresh(ModelMap model, HttpServletRequest request, @PathVariable String serverIp) throws IOException {
        for (Server server : serversInstantiated) {
            if (server.getIp().equals(serverIp))
            {
                server.setConnectionState(Server.State.StandBy);
                broadcastUsersOnServer(server);
                break;
            }
        }
        return "redirect:/servers";
    }

    @RequestMapping(value = "servers/serverConfigured", method = RequestMethod.POST)
    public String serverConfigureSave(ModelMap model, HttpServletRequest request) throws IOException {
        String ip = request.getParameter("ip");
        String format = request.getParameter("format");
        String name = request.getParameter("name");
        List<Server> server = serverService.get(ip);
        if (server.isEmpty()) {
            return "redirect:/servers";
        }
        server.get(0).setFormat(format);
        serverService.edit(server.get(0));
        for (Server serverIter : serversInstantiated) {
            if (serverIter.getIp().equals(ip))
            {
                serverIter.setConnectionState(Server.State.StandBy);
                serverIter.setFormat(format);
                serverIter.setName(name);
                broadcastUsersOnServer(serverIter);
                break;
            }
        }
        return "redirect:/servers";
    }

    @RequestMapping(value = "servers/configureServer/{serverIp:.+}", method = RequestMethod.GET)//need :.+ because of truncating the extension by default
    public ModelAndView configureServer(@PathVariable String serverIp, ModelMap model, HttpServletRequest request){
        List<Server> servers = serverService.get(serverIp);
        ModelAndView modelView = new ModelAndView("configureServer");
        if (servers.isEmpty())
        {
            return modelView;
        }
        Server server = servers.get(0);
        modelView.addObject("name", AuthenticatedUser.getAuthenticatedUserName());
        modelView.addObject("serverName",server.getName());
        modelView.addObject("ip", server.getIp());
        modelView.addObject("format", server.getFormat());
        return modelView;
    }
}

package com.resources.auth.Controllers;

import com.resources.auth.Database.Client.Client;
import com.resources.auth.Database.Client.ClientDAO;
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
import java.util.*;

/**
 * Created by tanvd on 30.11.15.
 */
@Controller
public class ClientsController {

    @Resource(name = "userService")
    private UserDAO userService;

    @Resource(name = "clientService")
    private ClientDAO clientService;



    @RequestMapping(value = "servers", method = RequestMethod.GET)
    public ModelAndView tableServersPrepare(ModelMap model, HttpServletRequest request) {
        ModelAndView table = new ModelAndView("servers");
        List<Client> clientsInBase = clientService.getAll();
        table.addObject("objects", clientsInBase);
        table.addObject("name", AuthenticatedUser.getAuthenticatedUserName());
        return table;
    }

    @RequestMapping(value = "servers/addNewServer", method = RequestMethod.GET)
    public ModelAndView addServer(ModelMap model, HttpServletRequest request) throws IOException{
        ModelAndView modelView = new ModelAndView("addServer");
        modelView.addObject("name", AuthenticatedUser.getAuthenticatedUserName());
        return modelView;
    }

    @RequestMapping(value = "servers/newServerAdded", method = RequestMethod.POST)
    public String serverCheck(ModelMap model, HttpServletRequest request) throws IOException{
        String clientId = request.getParameter("clientId");
        String scopes = request.getParameter("scopes");
        Set<String> scopesSet =  new HashSet<String>(Arrays.asList(scopes.split(" ")));
        String secret = request.getParameter("secret");
        Set<String> grantTypes = new HashSet<String>();
        grantTypes.add("authorization_code");
        Client client = new Client(clientId, true, secret, true, scopesSet, grantTypes, 64000, 64000);
        clientService.add(client);
        return "redirect:/servers";
    }

    @RequestMapping(value = "servers/configureServer/serverConfigured", method = RequestMethod.POST)
    public String serverConfigureSave(ModelMap model, HttpServletRequest request) throws IOException {
        String clientId = request.getParameter("clientId");
        String scopes = request.getParameter("scopes");
        String secret = request.getParameter("secret");
        List<Client> clients = clientService.get(clientId);

        Client client = clients.get(0);

        Set<String> scopesSet =  new HashSet<String>(Arrays.asList(scopes.split(" ")));
        client.setScope(scopesSet);

        client.setClientSecret(secret);
        if (clients.isEmpty()) {
            return "redirect:/servers";
        }
        clientService.edit(client);
        return "redirect:/servers";
    }

    @RequestMapping(value = "servers/configureServer/{clientId:.+}", method = RequestMethod.GET)//need :.+ because of truncating the extension by default
    public ModelAndView configureServer(@PathVariable String clientId, ModelMap model, HttpServletRequest request){
        List<Client> clients = clientService.get(clientId);
        ModelAndView modelView = new ModelAndView("configureServer");
        if (clients.isEmpty())
        {
            return modelView;
        }
        Client client = clients.get(0);
        modelView.addObject("name", AuthenticatedUser.getAuthenticatedUserName());
        modelView.addObject("clientId", client.getClientId());

        String scopes = "";
        Set<String> scopeSet = client.getScope();
        for (String scope : scopeSet) {
            scopes += scope + " ";
        }
        modelView.addObject("scopes", scopes);

        modelView.addObject("secret", client.getClientSecret());
        return modelView;
    }
}
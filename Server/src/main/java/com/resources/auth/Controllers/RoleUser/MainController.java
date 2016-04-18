package com.resources.auth.Controllers.RoleUser;

import com.resources.auth.Database.Client.Client;
import com.resources.auth.Database.Client.ClientDAO;
import com.resources.auth.Security.Utils.AuthenticatedUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by tanvd on 01.12.15.
 */
@Controller
public class MainController {
    @Resource(name="clientService")
    private ClientDAO clientService;

    @RequestMapping(value = "home", method = RequestMethod.GET)
    public ModelAndView tableUsersPrepare(ModelMap model, HttpServletRequest request) {
        ModelAndView table = new ModelAndView("ROLE_USER/mainView");
        List<Client> clientsInBase = clientService.getAll();
        table.addObject("clients", clientsInBase);
        table.addObject("name", AuthenticatedUser.getAuthenticatedUserName());
        return table;
    }
}

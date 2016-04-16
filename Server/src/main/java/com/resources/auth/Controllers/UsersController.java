package com.resources.auth.Controllers;

import com.resources.auth.Database.Users.User;
import com.resources.auth.Database.Users.UserAuthority;
import com.resources.auth.Database.Users.UserDAO;
import com.resources.auth.Security.Utils.AuthenticatedUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by tanvd on 08.11.15.
 */
@Controller
@RequestMapping("tableRegistered")
public class UsersController {

    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Resource(name="userService")
    private UserDAO userService;

    private ModelAndView prepareTableRegistered()
    {
        ModelAndView table = new ModelAndView("tableRegistered");
        List<User> result = userService.getAll();
        table.addObject("objects", result);
        table.addObject("name", AuthenticatedUser.getAuthenticatedUserName());
        return table;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView tableUsersPrepare(ModelMap model, HttpServletRequest request) {
        return prepareTableRegistered();
    }

    @RequestMapping(value = "grantUserAdminRights/{name}", method = RequestMethod.POST)
    public String tableUsersGrantAdmin(@PathVariable("name") String name) {
        List<User> usersList = userService.get(name);
        User user = usersList.get(0);
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new UserAuthority("ROLE_ADMIN"));
        authorities.add(new UserAuthority("ROLE_USER"));
        user.setAuthorities(authorities);
        userService.edit(user);
        logger.trace("User {} now has admin rights", user.getId());
        return "redirect:/tableRegistered";
    }

    @RequestMapping(value = "withdrawUserAdminRights/{name}", method = RequestMethod.POST)
    public String tableUsersWithdrawAdmin(@PathVariable("name") String name) {
        List<User> usersList = userService.get(name);
        User user = usersList.get(0);
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new UserAuthority("ROLE_USER"));
        user.setAuthorities(authorities);
        userService.edit(user);
        logger.trace("Admin {} lost admin rights", user.getId());
        return "redirect:/tableRegistered";
    }
}

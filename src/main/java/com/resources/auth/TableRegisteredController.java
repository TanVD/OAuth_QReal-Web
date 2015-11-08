package com.resources.auth;

import com.resources.auth.database.User;
import com.resources.auth.database.UserDAO;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.swing.text.TableView;
import java.util.List;

/**
 * Created by tanvd on 08.11.15.
 */
@Controller
public class TableRegisteredController {
    @Resource(name="userService")
    private UserDAO userService;

    @RequestMapping(value = "table", method = RequestMethod.GET)
    public ModelAndView userCheck(ModelMap model, HttpServletRequest request) {
        ModelAndView table = new ModelAndView("table");
        List<User> result = userService.getAll();
        table.addObject("objects", result);
        return table;
    }
}

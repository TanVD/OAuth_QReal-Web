package com.mkyong.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.*;

@Controller
public class BaseController {

	public void setSparklrService(SparklrService sparklrService) {
		this.sparklrService = sparklrService;
	}

	@Resource(name = "sparklrService")
	private SparklrService sparklrService;

	private static int counter = 0;
	private static final String VIEW_INDEX = "index";
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(BaseController.class);

	private static ArrayList<String> messages = new ArrayList<String>();

	@RequestMapping(value = "/CounterWebApp", method = RequestMethod.GET)
	public String welcome(ModelMap model) {


		/*if (messages == 0) {
			messages = new List<String>();
		}*/

		model.addAttribute("message", "Welcome");
		model.addAttribute("counter", ++counter);
		logger.debug("[welcome] counter : {}", counter);

		// Spring uses InternalResourceViewResolver and return back index.jsp
		return VIEW_INDEX;

	}

	@RequestMapping(value = "/CounterWebApp/{name}", method = RequestMethod.GET)
	public String welcomeName(@PathVariable String name, ModelMap model) {
		messages.add(name);
		model.addAttribute("message", "Welcome " + name);
		model.addAttribute("counter", ++counter);
		logger.debug("[welcomeName]" + name + " counter : {}", counter);
		return VIEW_INDEX;

	}

	@RequestMapping(value = "/table", method = RequestMethod.GET)
	public String showMessages(ModelMap model) {
		model.addAttribute("messages", messages);
		return "table";
	}


	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showLoginPage(ModelMap model) {
		return "loginPage";
	}

	@RequestMapping(value = "/getMyLogin")
	public String getMyLogin(ModelMap model) {
		String string = sparklrService.getLoginMy();
		model.addAttribute("str", string);
		return "myLogin";
	}

	@RequestMapping("/trusted/message")
	public String trusted(Model model) throws Exception {
		model.addAttribute("str", this.sparklrService.getTrustedMessage());
		return "myLogin";
	}

/*	@RequestMapping(value = "/getMyLogin", method = RequestMethod.GET)
	public String getMyLogin(ModelMap model) {
		String string = sparklrService.getLoginMy();
		model.addAttribute("str", string);
		return "myLogin";
	}*/

}
package com.springapp.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BotController {
	@RequestMapping(method = RequestMethod.GET, value = "/hello")
    @ResponseBody
	public String helloWorld() {
		return "world";
	}
}
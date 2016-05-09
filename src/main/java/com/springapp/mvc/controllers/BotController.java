package com.springapp.mvc.controllers;

import com.springapp.mvc.services.EchoingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class BotController {
    @Autowired private EchoingService echoingService;

	@RequestMapping(method = RequestMethod.GET, value = "/hello")
    @ResponseBody
	public String helloWorld() {
		return "world";
	}

    @RequestMapping(method = RequestMethod.GET, value = "/echo")
    @ResponseBody
    public String echo(@RequestParam(value = "hub.challenge") String hubChallenge, @RequestParam(value = "hub.verify_token") String verifyToken) {
        System.out.println(hubChallenge);
        System.out.println(verifyToken);
        if (verifyToken.equals("supersecretverifytoken")) {
            return hubChallenge;
        }
        return "Error, wrong validation token";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/echo")
    @ResponseBody
    public void echoPost(@RequestBody String request) {
        System.out.println(request);
        echoingService.echoMessages(request);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/dummyPost")
    @ResponseBody
    public void dummyPost(@RequestBody String request) {
        System.out.println(request);
    }
}
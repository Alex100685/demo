package com.example.demo.controller;

import com.example.demo.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    final GreetingService greetingService;

    public DemoController(@Autowired GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @RequestMapping(value="/hello",method= RequestMethod.GET)
    public String hello(@RequestParam String colleagueName){
        return greetingService.sayHelloMyMate(colleagueName);
    }

    @RequestMapping(value="/bye",method= RequestMethod.GET)
    public String bye(@RequestParam String colleagueName){
        return greetingService.sayByeMyMate(colleagueName);
    }

}

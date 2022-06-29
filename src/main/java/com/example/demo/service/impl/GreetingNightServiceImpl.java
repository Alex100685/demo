package com.example.demo.service.impl;

import com.example.demo.service.GreetingService;

public class GreetingNightServiceImpl implements GreetingService {

    @Override
    public String sayHelloMyMate(String colleagueName) {
        return "Good evening my mate " + colleagueName;
    }

    @Override
    public String sayByeMyMate(String colleagueName) {
        return "Bye my mate " + colleagueName + "Have a good night";
    }
}

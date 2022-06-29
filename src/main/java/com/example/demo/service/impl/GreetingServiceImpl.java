package com.example.demo.service.impl;

import com.example.demo.annotations.DeprecatedService;
import com.example.demo.annotations.ValidateParams;
import com.example.demo.service.GreetingService;
import org.springframework.stereotype.Service;

@Service
@DeprecatedService(useInstead = GreetingNightServiceImpl.class)
public class GreetingServiceImpl implements GreetingService {

    @ValidateParams
    @Override
    public String sayHelloMyMate(String colleagueName) {
        return "Good morning my mate " + colleagueName;
    }

    @ValidateParams
    @Override
    public String sayByeMyMate(String colleagueName) {
        return "Bye my mate " + colleagueName + "Have a good day";
    }
}

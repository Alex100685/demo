package com.example.demo.config;

import com.example.demo.beanfactorypostprocessor.DeprecatedServiceBeanFactoryPostProcessor;
import com.example.demo.postprocessor.ColleagueNameValidatorBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoAppConfig {

    @Bean
    ColleagueNameValidatorBeanPostProcessor colleagueNameValidatorBeanPostProcessor() {
        return new ColleagueNameValidatorBeanPostProcessor();
    }

    @Bean
    DeprecatedServiceBeanFactoryPostProcessor deprecatedServiceBeanFactoryPostProcessor() {
        return new DeprecatedServiceBeanFactoryPostProcessor();
    }

}

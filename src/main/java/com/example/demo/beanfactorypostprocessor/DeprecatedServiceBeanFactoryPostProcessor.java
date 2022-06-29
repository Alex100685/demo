package com.example.demo.beanfactorypostprocessor;

import com.example.demo.annotations.DeprecatedService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;

public class DeprecatedServiceBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        String[] beanNames = configurableListableBeanFactory.getBeanDefinitionNames();

        for (String beanName : beanNames) {
            BeanDefinition beanDefinition = configurableListableBeanFactory.getBeanDefinition(beanName);
            Class beanClass = null;
            try {
                beanClass = beanDefinition != null && beanDefinition.getBeanClassName() != null ?
                        Class.forName(beanDefinition.getBeanClassName())
                        : null;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            DeprecatedService annotation = beanClass != null ?
                    (DeprecatedService) beanClass.getAnnotation(DeprecatedService.class)
                    : null;
            if (annotation != null) {
                Class useInstead = annotation.useInstead();

                ((DefaultListableBeanFactory) configurableListableBeanFactory).removeBeanDefinition(beanName);

                GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
                genericBeanDefinition.setBeanClass(useInstead);

                ((DefaultListableBeanFactory) configurableListableBeanFactory)
                        .registerBeanDefinition(beanName, genericBeanDefinition);
            }
        }

    }
}

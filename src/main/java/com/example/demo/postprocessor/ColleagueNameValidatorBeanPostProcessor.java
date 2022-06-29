package com.example.demo.postprocessor;

import com.example.demo.annotations.ValidateParams;
import com.example.demo.exceptions.NoSuchColleagueException;
import com.example.demo.service.impl.GreetingServiceImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColleagueNameValidatorBeanPostProcessor implements BeanPostProcessor {

    private Map<String, Class> map = new HashMap<>();

    private static final List<String> COLLEAGUE_NAMES = List.of("YONGSHENG", "IGOR", "YUNJIAO", "ZHENGFEI", "CUI CUI");

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        if (clazz.getName().equals(GreetingServiceImpl.class.getName())) {
            System.out.println("Post process. Bean initialising:" + clazz.getSimpleName());
            map.put(beanName, clazz);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, String beanName) throws BeansException {
        Class clazz = map.remove(beanName);

        if (clazz != null) {
            return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), new InvocationHandler() {
                Object retVal;

                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    boolean isAnnotationOnImplementation = bean.getClass()
                            .getMethod(method.getName(), method.getParameterTypes())
                            .isAnnotationPresent(ValidateParams.class);
                    if (method.isAnnotationPresent(ValidateParams.class) || isAnnotationOnImplementation) {
                        System.out.println("Validation params before method " + method.getName() + " called");
                        try {
                            String name = (String) args[0];
                            validateParam(name);
                            retVal = method.invoke(bean, args);

                        } catch (Exception ex) {
                            throw ex;
                        }
                    } else {
                        retVal = method.invoke(bean, args);
                    }
                    return retVal;
                }
            });
        }
        return bean;
    }

    private void validateParam(String name) throws NoSuchColleagueException {
        if (!COLLEAGUE_NAMES.contains(name.toUpperCase())) {
            throw new NoSuchColleagueException("Colleague with name " + name + " Not found");
        }
    }
}

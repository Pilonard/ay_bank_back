package com.backend.ayBank;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.CrossOrigin;

/*

     */
@CrossOrigin(origins = "*")
public class SpringApplicationContext implements ApplicationContextAware {
    private static ApplicationContext CONTEXT;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            CONTEXT = applicationContext;
    }
        /*
            when we will get an object of a class
         */
    public static Object getBean(String beanName){
        return CONTEXT.getBean(beanName);
    }
}

package com.demo.springframework.test;

import com.demo.springframework.UserService;
import com.demo.springframework.beans.factory.config.BeanDefinition;
import com.demo.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.junit.Test;

public class ApiTest {
    @Test
    public void test_BeanFactory() {
        // 1.初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2.注册bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        //3.第一次获取bean
        UserService userService = (UserService)beanFactory.getBean("userService");
        userService.queryUserInfo();

        //4.第二次获取bean from Singleton
        UserService userService_singleton = (UserService) beanFactory.getBean("userService");
        userService_singleton.queryUserInfo();
    }
}

package com.demo.springframework.test;

import com.demo.springframework.BeanDefinition;
import com.demo.springframework.BeanFactory;
import com.demo.springframework.UserService;
import org.junit.Test;

public class ApiTest {
    @Test
    public void test_BeanFactory() {
        // 1.初始化 BeanFactory
        BeanFactory beanFactory = new BeanFactory();
        // 2.注册bean
        BeanDefinition beanDefinition = new BeanDefinition(new UserService());
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        //3.获取bean
        UserService userService = (UserService)beanFactory.getBean("userService");
        userService.queryUserInfo();
    }
}

package com.demo.springframework.test;

import com.demo.springframework.beans.PropertyValue;
import com.demo.springframework.beans.PropertyValues;
import com.demo.springframework.beans.factory.config.BeanReference;
import com.demo.springframework.test.bean.UserDao;
import com.demo.springframework.test.bean.UserService;
import com.demo.springframework.beans.factory.config.BeanDefinition;
import com.demo.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.junit.Test;

public class ApiTest {
    @Test
    public void test_BeanFactory() {
        // 1.初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2.UserDao注册
        beanFactory.registerBeanDefinition("userDao", new BeanDefinition(UserDao.class));

        // 3.UserService设置属性[uId、userDao]
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("uId", "10001"));
        propertyValues.addPropertyValue(new PropertyValue("userDao", new BeanReference("userDao")));

        // 4.UserService注入bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class, propertyValues);
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        // 5.UserService获取bean
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();
    }
}

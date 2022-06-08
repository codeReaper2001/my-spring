package com.demo.springframework.test;

import com.demo.springframework.context.support.ClassPathXmlApplicationContext;
import com.demo.springframework.test.bean.IUserService;
import org.junit.Test;

public class ApiTest {

    @Test
    public void test_autoProxy() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:aop-complete.xml");
        IUserService userService = applicationContext.getBean("userService", IUserService.class);
        System.out.println("测试结果：" + userService.queryUserInfo());
    }

}

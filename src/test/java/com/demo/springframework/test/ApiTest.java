package com.demo.springframework.test;

import com.demo.springframework.context.support.ClassPathXmlApplicationContext;
import com.demo.springframework.test.event.CustomEvent;
import org.junit.Test;

public class ApiTest {

    @Test
    public void test_event() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:listeners.xml");
        applicationContext.publishEvent(new CustomEvent(applicationContext, 1019129009086763L, "成功了！"));

        applicationContext.registerShutdownHook();
    }
}

package com.demo.springframework.test;

import com.demo.springframework.context.support.ClassPathXmlApplicationContext;
import com.demo.springframework.test.bean.IUserService;
import com.demo.springframework.test.forCircleDepend.Husband;
import com.demo.springframework.test.forCircleDepend.Wife;
import org.junit.Test;

public class ApiTest {

    @Test
    public void test_circular() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:for-circle-depend.xml");
        Husband husband = applicationContext.getBean("husband", Husband.class);
        Wife wife = applicationContext.getBean("wife", Wife.class);
        System.out.println("老公的媳妇：" + husband.queryWife());
        System.out.println("媳妇的老公：" + wife.queryHusband());
    }

}

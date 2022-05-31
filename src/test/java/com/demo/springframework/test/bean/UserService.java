package com.demo.springframework.test.bean;

import com.demo.springframework.beans.factory.DisposableBean;
import com.demo.springframework.beans.factory.InitializingBean;
import lombok.Data;

@Data
public class UserService implements InitializingBean, DisposableBean {
    private String uId;
    private String company;
    private String location;
    private UserDao userDao;

    UserService() {}

    public String queryUserInfo() {
        return userDao.queryUserName(uId) + "，" + company + "，" + location;
    }

    @Override
    public String toString() {
        return "UserService{" +
                "uId='" + uId + '\'' +
                ", company='" + company + '\'' +
                ", location='" + location + '\'' +
                ", userDao=" + userDao +
                '}';
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("执行：UserService.destroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("执行：UserService.afterPropertiesSet");
    }
}

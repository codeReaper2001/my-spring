package com.demo.springframework.test.bean;

import lombok.Data;

@Data
public class UserService {
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
}

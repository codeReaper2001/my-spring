package com.demo.springframework.test.bean;

import lombok.Data;

import java.util.Random;

@Data
public class UserService implements IUserService {

    private String uId;
    private String company;
    private String location;
    private IUserDao userDao;

    public UserService() {}

    public String queryUserInfo() {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "小傅哥，100001，深圳";
    }

    public String register(String userName) {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "注册用户：" + userName + " success！";
    }

}

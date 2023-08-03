package com.yc2;

public class UserDaoImpl implements UserDao{

    @Override
    public void add(String uname) {
        System.out.println("dao添加了:"+uname);
    }
}

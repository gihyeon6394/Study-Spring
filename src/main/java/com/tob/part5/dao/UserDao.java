package com.tob.part5.dao;

import com.tob.part3.vo.User;

import java.util.List;

public interface UserDao {
    void add(User user);

    User get(int seq);

    List<User> getAll();

    void deleteAll();

    int getCount();

}

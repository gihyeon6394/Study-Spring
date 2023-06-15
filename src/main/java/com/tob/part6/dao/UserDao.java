package com.tob.part6.dao;

import com.tob.part5.vo.User;

import java.util.List;

public interface UserDao {
    void add(User user);


    User get(int seq);

    List<User> getAll();

    User selectByName(String name);

    List<User> selectAll();

    void deleteAll();

    int getCount();

    void update(User user);
}

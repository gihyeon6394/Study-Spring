package com.tob.part6.service;

import com.tob.part5.vo.User;

public interface UserService {

    void add(User user);

    void upgradeLevels();

    void deleteAll();

    User selectByName(String name);
}

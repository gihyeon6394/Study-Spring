package com.tob.part4.dao;

import com.tob.part3.vo.User;

import java.util.List;

/**
 * DAO의 추상화
 * DAO를 기술 (JDBC, JPA)에 따라 구현하여 쓰면 됨
 */

public interface UserDao {
    void add(User user);

    User get(int seq);

    List<User> getAll();

    void deleteAll();

    int getCount();

}

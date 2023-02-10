package com.kghdev.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

//    순환 참조 예시
//    private final UserService userService;
//    @Autowired
//    public UserRepository(UserService userService) {
//        this.userService = userService;
//    }
}

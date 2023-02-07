package com.kghdev.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

}

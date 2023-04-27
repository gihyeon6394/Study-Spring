package com.tob.part5.service;

import com.tob.part5.dao.JDBCTemplateDAO;


/**
 * 비즈니스 로직 작성 (사용자 관리 로직)
 * service 원칙
 * - dao 구현체가 바뀌어도 service 로직이 수정되면 안됨
 *  ex. UserDAO -> UserDAO2 로 바뀌었다고해서 UserService의 로직이 바뀌면 안됨
 *
 * */
public class UserService {

    private JDBCTemplateDAO userDao;


    public void setUserDao(JDBCTemplateDAO userDao) {
        this.userDao = userDao;
    }


}

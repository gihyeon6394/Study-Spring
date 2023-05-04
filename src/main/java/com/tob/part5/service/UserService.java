package com.tob.part5.service;

import com.tob.part5.dao.JDBCTemplateDAO;
import com.tob.part5.vo.Level;
import com.tob.part5.vo.User;

import java.util.List;


/**
 * 비즈니스 로직 작성 (사용자 관리 로직)
 * service 원칙
 * - dao 구현체가 바뀌어도 service 로직이 수정되면 안됨
 * ex. UserDAO -> UserDAO2 로 바뀌었다고해서 UserService의 로직이 바뀌면 안됨
 */
public class UserService {

    private JDBCTemplateDAO userDao;


    public void setUserDao(JDBCTemplateDAO userDao) {
        this.userDao = userDao;
    }

    public void upgradeLevels() {
        List<User> userList = userDao.selectAll();
        Boolean changed = false; // 레벨 변화가 있는가

        for (User user : userList) {
            if (user.getLevel() == Level.BASIC && user.getCntLogin() >= 50) {
                user.setLevel(Level.SILVER);
                changed = true;
            } else if (user.getLevel() == Level.SILVER && user.getCntRecommend() >= 30) {
                user.setLevel(Level.GOLD);
                changed = true;
            } else if (user.getLevel() == Level.GOLD) {
                changed = false;
            } else {
                changed = false;
            }

            if (changed) {
                userDao.update(user);
            }
        }
    }

    public void add(User user) {
        if (user.getLevel() == null) {
            user.setLevel(Level.BASIC);
        }
        userDao.add(user);
    }
}

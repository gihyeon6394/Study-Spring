package com.tob.part6.service;

import com.tob.part5.vo.Level;
import com.tob.part5.vo.User;
import com.tob.part6.dao.UserDao;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;


public class UserServiceImpl implements UserService {

    private UserDao userDao;

    private PlatformTransactionManager transactionManager;
    private MailSender mailSender;


    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public static final int MIN_LOGIN_COUNT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_COUNT_FOR_GOLD = 30;


    /**
     * 문제점 : 선언적 트랜잭션 코드와 비즈니스 로직은 서로 독립적임
     * <p>
     * 1. 트랜잭션 코드와 분리했으나 트랜잭션 코드가 UserService와 강한결합 중
     * 2. Userservice인터페이스 생성 후 트랜잭션 코드 전담 구체 클래스, UserService 구현 클래스로 분리
     */
    public void upgradeLevels() {

        List<User> userList = userDao.selectAll();

        for (User user : userList) {
            if (canUpgradeLevel(user)) {
                upgradeLevel(user);
            }
        }
    }

    @Override
    public void deleteAll() {
        userDao.deleteAll();
    }

    @Override
    public User selectByName(String name) {
        return userDao.selectByName(name);
    }


    protected void upgradeLevel(User user) {

        user.upgradeLevel();
        userDao.update(user);
        sendUpgradeEmail(user);
    }


    private void sendUpgradeEmail(User user) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom("foo@email.com");
        mailMessage.setSubject("Upgrade 안내");
        mailMessage.setText("사용자님의 등급이 " + user.getLevel().name() + "로 업그레이드 되었습니다.");

        mailSender.send(mailMessage);

    }

    private boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();

        switch (currentLevel) {
            case BASIC:
                return (user.getCntLogin() >= MIN_LOGIN_COUNT_FOR_SILVER);
            case SILVER:
                return (user.getCntRecommend() >= MIN_RECOMMEND_COUNT_FOR_GOLD);
            case GOLD:
                return false;
            default:
                throw new IllegalArgumentException("Unknown Level: " + currentLevel);
        }
    }

    public void add(User user) {
        if (user.getLevel() == null) {
            user.setLevel(Level.BASIC);
        }
        userDao.add(user);
    }

}

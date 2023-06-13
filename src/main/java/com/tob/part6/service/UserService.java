package com.tob.part6.service;

import com.tob.part5.dao.JDBCTemplateDAO;
import com.tob.part5.vo.Level;
import com.tob.part5.vo.User;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.SQLException;
import java.util.List;


public class UserService {

    private JDBCTemplateDAO userDao;

    private PlatformTransactionManager transactionManager;

    private MailSender mailSender;

    public void setUserDao(JDBCTemplateDAO userDao) {
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
     */
    public void upgradeLevels() throws SQLException {

        TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            /*List<User> userList = userDao.selectAll();

            for (User user : userList) {
                if (canUpgradeLevel(user)) {
                    upgradeLevel(user);
                }
            }*/
            upgradeLevelsInternal();
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }

    private void upgradeLevelsInternal() {
        List<User> userList = userDao.selectAll();

        for (User user : userList) {
            if (canUpgradeLevel(user)) {
                upgradeLevel(user);
            }
        }
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

    /**
     * 비즈니스 도중 예외 발생 테스트 용도
     */
    public static class TestUserService extends UserService {
        private String name;

        public TestUserService(String name) {
            this.name = name;
        }

        @Override
        protected void upgradeLevel(User user) {
            if (user.getName().equals(this.name)) {
                throw new TestUserServiceException();
            }
            super.upgradeLevel(user);
        }

        public static class TestUserServiceException extends RuntimeException {
        }
    }

}

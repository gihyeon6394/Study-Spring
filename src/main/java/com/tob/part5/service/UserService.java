package com.tob.part5.service;

import com.tob.part5.dao.JDBCTemplateDAO;
import com.tob.part5.vo.Level;
import com.tob.part5.vo.User;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.SQLException;
import java.util.List;


/**
 * 비즈니스 로직 작성 (사용자 관리 로직)
 * service 원칙
 * - dao 구현체가 바뀌어도 service 로직이 수정되면 안됨
 * ex. UserDAO -> UserDAO2 로 바뀌었다고해서 UserService의 로직이 바뀌면 안됨
 */
public class UserService {

    private JDBCTemplateDAO userDao;

    private  PlatformTransactionManager transactionManager;

    public void setUserDao(JDBCTemplateDAO userDao) {
        this.userDao = userDao;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public static final int MIN_LOGIN_COUNT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_COUNT_FOR_GOLD = 30;

    /**
     * User : 본인에 대한 정보와 기능
     * Service : 비즈니스
     * Dao : 디비 접근
     */
    /*public void upgradeLevels() {
        List<User> userList = userDao.selectAll();

        for (User user : userList) {
            if (canUpgradeLevel(user)) {
                upgradeLevel(user);
            }
        }
    }*/

    /**
     * 트랜잭션 동기화를 적용한 upgradeLevels()
     * 주석처리 후 트랜잭션 서비스 추상화 구현
     */
    public void upgradeLevels() throws SQLException {
//        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource); // jdbc
//        PlatformTransactionManager transactionManager = new JtaTransactionManager(); // jta

        TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());

//        TransactionSynchronizationManager.initSynchronization();
//        Connection c = DataSourceUtils.getConnection(dataSource);
//        c.setAutoCommit(false);

        try {
            List<User> userList = userDao.selectAll();

            for (User user : userList) {
                if (canUpgradeLevel(user)) {
                    upgradeLevel(user);
                }
            }
            transactionManager.commit(status);
        } catch (Exception e) {
//            c.rollback();
            transactionManager.rollback(status);
            throw e;
        } /*finally {
            DataSourceUtils.releaseConnection(c, dataSource);
            TransactionSynchronizationManager.unbindResource(this.dataSource);
            TransactionSynchronizationManager.clearSynchronization();
        }*/
    }


    /**
     * 다음단계가 무엇인가를 여기서 판단하지 말자
     */
    protected void upgradeLevel(User user) {

        /*if (user.getLevel() == Level.BASIC) {
            user.setLevel(Level.SILVER);
        } else if (user.getLevel() == Level.SILVER) {
            user.setLevel(Level.GOLD);
        }*/
        user.upgradeLevel();
        userDao.update(user);
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

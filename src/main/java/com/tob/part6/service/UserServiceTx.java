package com.tob.part6.service;

import com.tob.part5.vo.User;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * 트랜잭션 서비스 제공
 * UserServiceTx : porxy
 * UserService : target
 */
public class UserServiceTx implements UserService {


    private UserService userService; // target object
    private PlatformTransactionManager transactionManager;

    // Userservice 비즈니스 로직을 담당할 UserService 구현 클래스 주입 받음
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public void add(User user) {
        userService.add(user);
    }

    @Override
    public void upgradeLevels() {
        TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            userService.upgradeLevels();
            this.transactionManager.commit(status);
        } catch (RuntimeException e) {
            this.transactionManager.rollback(status);
            throw e;
        }

    }

    @Override
    public void deleteAll() {
        userService.deleteAll();
    }

    @Override
    public User selectByName(String name) {
        return userService.selectByName(name);
    }


}

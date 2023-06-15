package com.tob.part6.service;

import com.tob.part5.vo.User;

public class TestUserService extends UserServiceImpl {
    private String name;

    public TestUserService(String name) {
        this.name = name;
    }

    @Override
    protected void upgradeLevel(User user) {
        if (user.getName().equals(this.name)) {
            throw new TestUserService.TestUserServiceException();
        }
        super.upgradeLevel(user);
    }

    public static class TestUserServiceException extends RuntimeException {
    }
}

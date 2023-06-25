package com.tob.part6.service;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TransactionHandler implements InvocationHandler {

    private Object target; // target object
    private PlatformTransactionManager transactionManager;
    private String pattern; // 트랜잭션을 적용할 메소드 이름 패턴

    public void setTarget(Object target) {
        this.target = target;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        if (method.getName().startsWith(pattern)) {
            return invokeInTransaction(method, objects);
        } else {
            return method.invoke(target, objects);
        }
    }

    private Object invokeInTransaction(Method method, Object[] objects) {
        TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            Object ret = method.invoke(target, objects);
            this.transactionManager.commit(status);
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            this.transactionManager.rollback(status);
            throw new RuntimeException(e);
        }
    }
}

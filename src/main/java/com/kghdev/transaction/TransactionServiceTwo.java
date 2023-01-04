package com.kghdev.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionServiceTwo {

    @Autowired
    private TransactionMapper transactionMapper;

    @Transactional
    public void updt06a() {
        // LECTURE 이름변경 2
        transactionMapper.updtA();

        throw new RuntimeException();
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updt06b() {
        transactionMapper.updtA();

        throw new RuntimeException();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updt07a() {
        transactionMapper.updtA();

        throw new RuntimeException();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updt08a() {
        transactionMapper.updtA();
    }
}

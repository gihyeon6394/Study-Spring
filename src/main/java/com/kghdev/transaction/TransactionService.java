package com.kghdev.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
public class TransactionService {

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private TransactionServiceTwo transactionServiceTwo;

    /*case 01*/
    public void updt01() {
        // LECTURE 이름 변경
        transactionMapper.updt();

        // RuntimeException 발동!
        throw new RuntimeException("new Exception!!");
    }

    /*case2*/
    // @Transactional을 사용하여 updt01을 하나의 트랜잭션으로 수행
    @Transactional
    public void updt02() {
        // LECTURE 이름 변경
        transactionMapper.updt();

        // RuntimeException 발동!
        throw new RuntimeException("new Exception!!");
    }

    /*case3*/
    @Transactional
    public void updt03() throws SQLException {
        // LECTURE 이름 변경
        transactionMapper.updt();

        // CheckedException > SQLException 발동!
        throw new SQLException();
    }

    @Transactional(rollbackFor = SQLException.class) //SQLException에 대해 rollback
    public void updt04() throws SQLException {
        // LECTURE 이름 변경
        transactionMapper.updt();

        // CheckedException > SQLException 발동!
        throw new SQLException();
    }

    @Transactional
    public void updt05() {

        try {
            // LECTURE 이름 변경
            transactionMapper.updt();

            // RuntimeException 발동!
            throw new RuntimeException("new Exception!!");
        } catch (Exception e) {
            /*
             * handle the Exception
             * ....
             *
             * */
        }
    }

    @Transactional
    public void updt06() {
        // LECTURE 이름 변경
        transactionMapper.updt();

        try {
//            transactionServiceTwo.updt06a();
            transactionServiceTwo.updt06b();
        } catch (RuntimeException e) {
            e.printStackTrace();
            /*
             * handle the Exception
             * ....
             * */
        }
    }

    @Transactional
    public void updt07() {
        transactionMapper.updt();
        transactionServiceTwo.updt07a();
    }

    @Transactional
    public void updt08() {
        transactionMapper.updt();
        transactionServiceTwo.updt08a();

        throw new RuntimeException("new outer Exception!");
    }

}

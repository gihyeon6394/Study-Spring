package com.kghdev.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService  {

    @Autowired
    private TransactionMapper transactionMapper;

    /*case 01*/
  /*  public void updt01() {
        transactionMapper.updt01();
        throw new RuntimeException("new Exception!!");
    }
*/
    @Transactional
    public void updt01() {
        transactionMapper.updt01();
        throw new RuntimeException("new Exception!!");
    }
}

package com.kghdev.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

/*
*
* @Transactional을 공부하기 위한 패키지
* */
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @RequestMapping("/updtTest")
    @ResponseBody
    public void updtTest(){

//        try {
//            transactionService.updt01();
//            transactionService.updt02();
//            transactionService.updt03();
//            transactionService.updt04();
//            transactionService.updt05();
//            transactionService.updt06();
//            transactionService.updt07();
//            transactionService.updt08();

//        } catch (Exception e) {
//            throw new (e);
//        }
    }






}

package com.kghdev.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/thr")
public class ThreadController {

    private static final Logger logger = LoggerFactory.getLogger(User.class);

    //팔린 맥북 개수
    public volatile static Integer cntMacBookSold = 0;

    @RequestMapping("/tst1")
    @ResponseBody
    public void tst1() throws InterruptedException {

//        Thread01 thread01 = new Thread01();
//        Thread02 thread02 = new Thread02();

        /*병렬 동작 테스트*/
//        thread01.start();
//        thread02.start();
//        thread02.getState();


        for (int i = 0; i < 1000; i++) {
//            User user = new User();
//            User02 user = new User02();
//            user.start();
        }

        Thread.sleep(5000);

        logger.info(" SOLD OUT !!! || 최종 맥북 판매량 : " + ThreadController.cntMacBookSold);


    }


}

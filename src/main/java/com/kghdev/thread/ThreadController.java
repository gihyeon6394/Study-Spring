package com.kghdev.thread;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/thr")
public class ThreadController {

    @RequestMapping("/tst1")
    @ResponseBody
    public void tst1() {

        Thread01 thread01 = new Thread01();
        Thread02 thread02 = new Thread02();

        /*병렬 동작 테스트*/
        thread01.start();
        thread02.start();
        thread02.getState();
    }


}

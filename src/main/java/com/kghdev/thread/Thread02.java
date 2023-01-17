package com.kghdev.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Thread02 extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(Thread02.class);

    @Override
    public void run() {

        logger.info("Thread02 run : " + Thread.currentThread().getName());

        for (int i = 0; i < 5; i++) {
            logger.info("Thread02 run : " + Thread.currentThread().getName() + " idx :" + i);
        }

        logger.info("Thread02 finished : " + Thread.currentThread().getName());
    }



}

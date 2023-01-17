package com.kghdev.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Thread01 extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(Thread01.class);

    @Override
    public void run() {

        logger.info("Thread01 run : " + Thread.currentThread().getName());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < 5; i++) {
            logger.info("Thread01 run : " + Thread.currentThread().getName() + " idx :" + i);
        }

        logger.info("Thread01 finished : " + Thread.currentThread().getName());
    }
}

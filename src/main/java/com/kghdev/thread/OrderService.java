package com.kghdev.thread;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(User02.class);

    public synchronized void orderMacBook() {
        ThreadController.cntMacBookSold++;
    }

    public synchronized void alert() {
        // 100개씩 팔릴 때마다 alert
        if (ThreadController.cntMacBookSold % 100 == 0) {
            try {
                Thread.sleep(Integer.parseInt(RandomStringUtils.randomNumeric(2))); // n초간 일시정지
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            logger.info("지금까지 맥북 판매량 : " + ThreadController.cntMacBookSold);

        }
    }
}

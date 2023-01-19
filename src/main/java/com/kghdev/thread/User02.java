package com.kghdev.thread;

import com.kghdev.utils.BeanUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*check-then-act 패턴*/
public class User02 extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(User02.class);

    @Override
    public void run() {

        try {
            Thread.sleep(Integer.parseInt(RandomStringUtils.randomNumeric(2))); // n초간 일시정지
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        OrderService orderService = (OrderService) BeanUtils.getBean("orderService");

        // 맥북 하나 팔림
        orderService.orderMacBook();

        // 판매량 알림
        orderService.alert();

    }
}

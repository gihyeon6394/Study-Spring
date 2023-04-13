package com.tob.part4;

import java.sql.SQLException;

/**
 * 예외
 *
 * 예외처리의 핵심 원칙 : 모든 예외는 적절히 복구되거나 작업을 중단시키고 관리자 (개발자)에게 알리거나
 */
public class Main {

    public static void main(String[] args) {

        /**
         * 초난감 예외 case
         * 1. catch에서 아무것도 안함
         * 2. 바깥 scope로 계속 throws
         * */

        try {
            //.. SQLException 을 던지는 코드...
            throw new SQLException();
        } catch (SQLException e) {

            // 1. catch 에서 아무것도 안함
            // 2. 콘솔에 남기고 끝
            System.out.println(e);
            e.printStackTrace();
        }
    }
}

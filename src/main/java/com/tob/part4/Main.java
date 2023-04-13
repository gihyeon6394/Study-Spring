package com.tob.part4;

import com.mysql.cj.exceptions.MysqlErrorNumbers;
import com.tob.part4.exception.DuplicatedUserIDException;

import java.io.IOException;
import java.sql.SQLException;

/**
 * 예외
 * <p>
 * 예외처리의 핵심 원칙 : 모든 예외는 적절히 복구되거나 작업을 중단시키고 관리자 (개발자)에게 알리거나
 * <p>
 * 예외의 종류와 특징
 * 1. Error
 * System 레벨에서 발생하는 에러
 * ex. {@link OutOfMemoryError}, {@link ThreadDeath}
 * 시스템 레벨의 작업이 아니라면 특별히 처리에 신경쓰지 않아도됨.
 * 2. Exception (CheckedException)
 * Exception과 그 서브클래스 CheckedException를 합쳐서 체크이셉션이라고함
 * 일반적으로 예외라면 CheckedException을 말함
 * 처리하지 않으면 컴파일 단계에서 에러
 * CheckedException이 예외처리를 강제하기 때문에 초난감 예외 Case들이 늘어남 (비난의 대상)
 * 요즘은 Java API에서는 예외를 Checked로 만들지 않는 경향이 있기도 함
 * 3. RuntimeException (UncheckedException)
 * 컴파일 단계에서 체크 안함 => catch, throws 안해도 상관 없음
 * 개발자가 부주의 해서 발생할 수 있는 예외들
 * ex. {@link NullPointerException}, {@link IllegalArgumentException}
 */

/**
 * 예외 처리 전략
 * 런타임 예외가 보편화 되가고 있다.
 * 체크 예외는 무조건 처리를 강제함
 * 언체크 예외 (Runtime)는 처리해도되고, 안해도 됨
 * java 환경이 서버 환경으로 넘어가는 추세에 굳이 예외 처리를 강제할 필요가 없어짐
 * ex. 사용자가 아이디 잘못입력한 예외를 굳이 처리를 강제하거나, 시스템이 멈춰서 복구가 필요하거나 한가? 그렇지 않음
 * 그래서 요즘은 Java 예외를 Runtime으로 정의하는 추세임
 * <p>
 * 체크 예외를 런타임으로 전환했다면, signature에 throws 키워드를 넣어주고, 명세도 자세히 해줘야 함
 * 그래야 메서드 사용자가 {@link RuntimeException}을 던지는 걸 알고, 때에 따라 적절히 처리함
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

    /**
     * 예외처리 방법 1 : 복구
     * 프로그램을 정상으로 되돌리는 것
     * 주로 CheckedExeption의 목적임 : 사용자 (개발자)에게 복구하기를 요구하는 것
     */
    public static void handleException1() throws InterruptedException {
        final int MAX_RETRY = 10;
        int cntRetry = 0;

        // pseudo code
        while (cntRetry <= MAX_RETRY) {
            try {
                throw new IOException();
                //....
            } catch (IOException e) {
                System.out.println(e.getMessage());
                Thread.sleep(100);
            } finally {
                //자원 반납
            }
        }
    }

    /**
     * 예외처리 방법 2 : 회피
     * 자신의 예외를 throws
     * 다른 오브젝트 (메서드)가 예외를 대신 처리하게 함
     * ex. 콜백 오브젝트의 메서드가 예외를 throws, 템플릿에서 처리
     * 회피는 의도가 분명해야함
     * => 긴밀한 관계의 객체사이에서 회피를 하거나, 처리 책임을 지고 처리한다는 확신을 가진상태에서 회피할 것
     */
    public static void handleException2() throws IOException {
        // pseudo code
        //... some code
        throw new IOException();
    }

    /**
     * 예외처리 방법 3 : 전환 (translation)
     * 예외를 바깥으로 throws 하되 특정 형태로 '전환'시켜 throws 한다
     * 전환의 목적 1. 내부에서 발생한 예외에 의미 부여를 하기 위해
     * ex.{@link SQLException} => {@link org.springframework.dao.DuplicateKeyException}
     * DAO 레이어에서 발생한 SQLException을 Controller가 아는게 큰 의미 없음. 어떤 의미의 예외가 났는지 해석 가능한 형태로 받아야 좋음
     * <p>
     * 전환의 목적 2. 포장 (wrapper)
     * 예외 처리를 단순하게 만들기 위해
     * 주로 Check -> Unchecked로 전환
     * 복구 못할 예외는 런타임 예외로 전환해서 던진다. 전환할때 적절한 로그, 관리자 (개발자) 알림을 사용한다. 유저에게는 적절한 알림창을 뛰운다.
     */

    /**
     * 예외를 런타임으로 전환했다면 반드시 명세를 해주길
     * 명시적으로 throws 키워드를 선언부에 넣어주는 것도 추천
     *
     * @throws DuplicatedUserIDException : 사용자가 아이디를 잘못 입력했을때 예외
     */
    public static void handleException3() throws DuplicatedUserIDException {
        // pseudo code
        try {
            // 멤버 insert 코드...
            throw new SQLException();
        } catch (SQLException e) {
            //SQLException 에 의미 부여 후 전환
            if (e.getErrorCode() == MysqlErrorNumbers.ER_DUP_ENTRY) {
                // initCause : 중첩 예외로 만들어 사용자가 원인 (SQLException)을 알 수 있게 한다
                // throw new DuplicatedUserIDException(e).initCause(e);
                throw new DuplicatedUserIDException(e);
            } else {
                throw new RuntimeException();
            }
        }


    }

}

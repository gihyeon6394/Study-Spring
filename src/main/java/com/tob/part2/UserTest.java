package com.tob.part2;

import com.tob.part2.dao.DaoFactorySpring;
import com.tob.part2.dao.GoodDAO;
import com.tob.part2.vo.User;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * 단위 테스트 (테스트 코드 작성)를 하는 이유
 * 1. 개발자가 만든 코드를 의도한대로 돌아가는지 가장 먼저 알아낸다
 * 2. 자동수행 테스트 (테스트 클래스 등)는 나중 개선작업에도 소스 수정이 정상작동하는지 확인하는데 편리 (테스트 코드 돌리면 됨)
 * 3. 객체지향적으로 설계가 가능 (테스트 코드를 반복해 가며 개선점이 보임)
 *
 * UserTest의 문제점
 * 1. 사람이 직접 test 결과를 확인해봐야함
 * 2. UserTest.main() 을 계속해서 실행하면서 테스트해봐야함 (실행을 반복해야함)
 *
 * main()을 활용한 test의 문제점
 * - 애플리케이션 규모가 커지면 테스트 개수 (main)가 많아짐
 * - 테스트를 수행하는 일이 점점 부담됨
 * - main이 테스트의 주도권을 가지고 있음
 * => JUnit : 자바 단위테스트 지원도구 (Framework)
 */
public class UserTest {

    // 문제점 2
    public static void main(String args[]) throws SQLException, ClassNotFoundException {
       /* ConnectionMaker connectionMaker = new NConnectionMaker();

        // use obeject factory in spring
        ApplicationContext ac = new AnnotationConfigApplicationContext(DaoFactorySpring.class);
        GoodDAO goodDAO2 = ac.getBean("goodDAO", GoodDAO.class); // getBean() : Dependency lookup
        User hani = goodDAO2.get(6);

        //test 검증의 자동화
        if (hani == null || !"팜하니".equals(hani.getName())) {
            System.out.println("failed");
        } else {
            System.out.println("success");
        }*/
        ApplicationContext ac = new AnnotationConfigApplicationContext(DaoFactorySpring.class);
        GoodDAO goodDAO2 = ac.getBean("goodDAO", GoodDAO.class); // getBean() : Dependency lookup
        User hani = goodDAO2.get(6);

        JUnitCore.main("com.tob.part2.UserTest");
    }

    /**
     * JUnit 적용
     * 개발자의 경우 IDE로 테스트
     * 소스 전체 테스트 (통합) : build tools (ex. maven)에 이식해서 test
     * */
    @Test
    public void tstGet() throws SQLException, ClassNotFoundException {
        ApplicationContext ac = new AnnotationConfigApplicationContext(DaoFactorySpring.class);
        GoodDAO goodDAO2 = ac.getBean("goodDAO", GoodDAO.class); // getBean() : Dependency lookup
        User hani = goodDAO2.get(6);
        assertThat(hani.getName(), is("팜하니"));



    }
}

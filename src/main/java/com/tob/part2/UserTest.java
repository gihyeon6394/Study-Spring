package com.tob.part2;

import com.tob.part2.dao.DaoFactorySpring;
import com.tob.part2.dao.GoodDAO;
import com.tob.part2.vo.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * 단위 테스트 (테스트 코드 작성)를 하는 이유
 * 1. 개발자가 만든 코드를 의도한대로 돌아가는지 가장 먼저 알아낸다
 * 2. 자동수행 테스트 (테스트 클래스 등)는 나중 개선작업에도 소스 수정이 정상작동하는지 확인하는데 편리 (테스트 코드 돌리면 됨)
 * 3. 객체지향적으로 설계가 가능 (테스트 코드를 반복해 가며 개선점이 보임)
 * <p>
 * UserTest의 문제점
 * 1. 사람이 직접 test 결과를 확인해봐야함
 * 2. UserTest.main() 을 계속해서 실행하면서 테스트해봐야함 (실행을 반복해야함)
 * <p>
 * main()을 활용한 test의 문제점
 * - 애플리케이션 규모가 커지면 테스트 개수 (main)가 많아짐
 * - 테스트를 수행하는 일이 점점 부담됨
 * - main이 테스트의 주도권을 가지고 있음
 * => JUnit : 자바 단위테스트 지원도구 (Framework)
 */
public class UserTest {

    /**
     * Fixture : 테스트에 필요한 object, info
     * */
    private  GoodDAO goodDAO;
    public static void main(String args[]) throws SQLException, ClassNotFoundException {

        JUnitCore.main("com.tob.part2.UserTest");
    }

    /**
     * @Test 이전 @Before 한번 실행
     * @Test가 3개면 @Before 3번
     * Before -> Test -> After -> Before -> Test -> After -> ...
     * @After도 마찬가지
     * */
    @Before
    public void setp(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(DaoFactorySpring.class);
        goodDAO = ac.getBean("goodDAO", GoodDAO.class); // getBean() : Dependency lookup
    }

    /**
     * JUnit 적용
     * 개발자의 경우 IDE로 테스트
     * 소스 전체 테스트 (통합) : build tools (ex. maven)에 이식해서 test
     * <p>
     * test method 하나는 하나의 검증을 목적으로만 하는 것을 지향
     * JUnit은 테스트 메서드의 실행순서를 보장하지 않음. 테스트의 결과가 테스트 순서에 의존한다면 잘못된 테스트!
     */

    // 하니가 있는지?
    @Test
    public void existHani() throws SQLException, ClassNotFoundException {
        // ApplicationContext ac = new AnnotationConfigApplicationContext(DaoFactorySpring.class);
        // GoodDAO goodDAO = ac.getBean("goodDAO", GoodDAO.class); // getBean() : Dependency lookup
        User hani = goodDAO.getUserByName("팜하니");
        assertThat(hani.getName(), is("팜하니"));

    }

    //뉴진스 멤버가 있는지?
    @Test
    public void existNewJeans() throws SQLException, ClassNotFoundException {
        // ApplicationContext ac = new AnnotationConfigApplicationContext(DaoFactorySpring.class);
        // GoodDAO goodDAO = ac.getBean("goodDAO", GoodDAO.class); // getBean() : Dependency lookup
        List<User> userList = goodDAO.getUsersByNameGroup("뉴진스");
        assertThat(userList.size(), greaterThan(0));

    }

    /**
     * TDD
     *
     * 1. 알 수 없는 그룹 호출하는 테스트 코드 작성
     * 2. 알 수 없는 그룹 호출하는 테스크 코드를 성공하게 하는 코드 작성
     *
     * 예제
     * 1. expected in test code
     * 2. throw exception in code
     */
    @Test(expected = EmptyResultDataAccessException.class) //test 중 해당 exception 을 expected 중
    public void existIVE() throws SQLException, ClassNotFoundException {
        // ApplicationContext ac = new AnnotationConfigApplicationContext(DaoFactorySpring.class);
        // GoodDAO goodDAO = ac.getBean("goodDAO", GoodDAO.class); // getBean() : Dependency lookup
        goodDAO.getUsersByNameGroup("아이브");

    }
}

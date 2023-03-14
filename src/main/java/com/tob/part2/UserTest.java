package com.tob.part2;

import com.tob.part2.dao.DaoFactorySpring;
import com.tob.part2.dao.GoodDAO;
import com.tob.part2.vo.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

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
@RunWith(SpringRunner.class) // JUnit framework의 테스트 실행방법 확장

//spring test framework, classes : ApplicationContext config 위치 지정
// method-level, class-level 에서도 컨텍스트를 공유할 수 있음 (= test class가 달라도 같은 ApplicationContext 설정 위치를 지정하면, 모두 같은 ApplicationContext 사용)
@SpringBootTest(classes = DaoFactorySpring.class)

/**
 * test method 에서 ApplicationConext 구성 (bean 간의 의존 등)을 수정하겠다는 것을 test framwork에게 명시
 * class-level, method-level 가능
 * 클래스 레벨일 경우 해당 클래스에서 ApplcationContext 공유 안됨 (=> 테스트 메서드들끼리 영향을 주지 못하도록)
 * 메서드 레벨일 경우 해당 메서드에 대해서만 ApplcationContext 새롭게 생성, 해당 메서드가 끝나면 다시 새로운 Context 생성
 * */
//@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class UserTest {

    /**
     * Fixture : 테스트에 필요한 object, info
     */
    private GoodDAO goodDAO;

    @Autowired
    private ApplicationContext ac;


    public static void main(String args[]) throws SQLException, ClassNotFoundException {

        JUnitCore.main("com.tob.part2.UserTest");
    }

    /**
     * @Test 이전 @Before 한번 실행
     * @Test가 3개면 @Before 3번
     * Before -> Test -> After -> Before -> Test -> After -> ...
     * @After도 마찬가지
     */
    @Before
    public void beforeTest() {

        /**
         * ApplicationContext을 자주 초기화하면 성능 저하
         *
         * - ApplicationContext를 생성 (new)할 떄 모든 Bean을 초기화함 -> 리소스 낭비
         * - 특정 Bean은 초기화 할떄 새로운 thread를 열 가능성도 있음
         *
         * 해결방안
         * - @BeforeClass static method 사용
         * - SrpingBootTest framework를 이용하여 테스트 시 최초에 한번 의존성 주입
         * */
//        ApplicationContext ac = new AnnotationConfigApplicationContext(DaoFactorySpring.class);
//        goodDAO = ac.getBean("goodDAO", GoodDAO.class); // getBean() : Dependency lookup
        goodDAO = this.ac.getBean("goodDAO", GoodDAO.class); // getBean() : Dependency lookup

        /**
         * DI by test Code
         * - 필요에 따라 테스트 케이스에 부합하는 의존관계를 설정해줄 수 있음
         * - 주의해서 할 것
         * */
//        goodDAO.setDataSource("....");

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
        System.out.println("existHani : "+ this.ac.toString());

    }

    //뉴진스 멤버가 있는지?
    @Test
    public void existNewJeans() throws SQLException, ClassNotFoundException {
        // ApplicationContext ac = new AnnotationConfigApplicationContext(DaoFactorySpring.class);
        // GoodDAO goodDAO = ac.getBean("goodDAO", GoodDAO.class); // getBean() : Dependency lookup
        List<User> userList = goodDAO.getUsersByNameGroup("뉴진스");
        assertThat(userList.size(), greaterThan(0));
        System.out.println("existNewJeans : "+this.ac.toString());

    }


    //뉴진스 멤버가 있는지?
    @Test
    public void existNewJeans2() throws SQLException, ClassNotFoundException {
        // ApplicationContext ac = new AnnotationConfigApplicationContext(DaoFactorySpring.class);
        // GoodDAO goodDAO = ac.getBean("goodDAO", GoodDAO.class); // getBean() : Dependency lookup
        List<User> userList = goodDAO.getUsersByNameGroup("뉴진스");
        assertThat(userList.size(), greaterThan(0));
        System.out.println("existNewJeans2 : "+ this.ac.toString());


    }

    /**
     * TDD
     * <p>
     * 1. 알 수 없는 그룹 호출하는 테스트 코드 작성
     * 2. 알 수 없는 그룹 호출하는 테스크 코드를 성공하게 하는 코드 작성
     * <p>
     * 예제
     * 1. expected in test code
     * 2. throw exception in code
     */
    @Test(expected = EmptyResultDataAccessException.class) //test 중 해당 exception 을 expected 중
    public void existIVE() throws SQLException, ClassNotFoundException {
        // ApplicationContext ac = new AnnotationConfigApplicationContext(DaoFactorySpring.class);
        // GoodDAO goodDAO = ac.getBean("goodDAO", GoodDAO.class); // getBean() : Dependency lookup
        System.out.println("existIVE : "+ this.ac.toString());
        goodDAO.getUsersByNameGroup("아이브");

    }
}

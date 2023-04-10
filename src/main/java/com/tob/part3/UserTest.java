package com.tob.part3;

import com.tob.part3.dao.GoodDAO;
import com.tob.part3.dao.JDBCTemplateDAO;
import com.tob.part3.vo.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(/*classes = DaoFactorySpring.class*/)
@ContextConfiguration("/applicationContext.xml")
public class UserTest {
    private GoodDAO goodDAO;
    private JDBCTemplateDAO jdbcTemplateDAO;

    @Autowired
    private ApplicationContext ac;

    static Set<UserTest> setUserTest = new HashSet<>(); //매번 새로운 test object를 만드는가?

    static ApplicationContext applicationContext = null; //주입받은 하나의 context만 사용하는가?


    public static void main(String args[]) throws SQLException, ClassNotFoundException {

        JUnitCore.main("com.tob.part3.UserTest");
    }

    @Before
    public void beforeTest() {
//        goodDAO = this.ac.getBean("goodDAO3", GoodDAO.class); // getBean() : Dependency lookup
        jdbcTemplateDAO = this.ac.getBean("jdbcTemplateDAO", JDBCTemplateDAO.class); // getBean() : Dependency lookup

    }

    // 하니가 있는지?
    @Test
    public void existHani() throws SQLException, ClassNotFoundException {
//
//        User hani = goodDAO.getUserByName8("팜하니");
//        assertThat(hani.getName(), is("팜하니"));

    }

    //    @Test
    public void inset() throws SQLException, ClassNotFoundException {

        User user = new User();
        user.setName("카뤼나");
        user.setNameGroup("에스퐈");
        int result = jdbcTemplateDAO.insert(user);
        assertThat(result, is(1));

    }

    //    @Test
    public void countAll() throws SQLException, ClassNotFoundException {
        // int cnt = jdbcTemplateDAO.countAll1();
        int cnt = jdbcTemplateDAO.countAll2();
        assertThat(cnt, is(not(0)));
    }

    @Test
    public void selectByName() throws SQLException, ClassNotFoundException {
        // int cnt = jdbcTemplateDAO.countAll1();
        User user = jdbcTemplateDAO.selectByName("해린");
        assertNotNull(user);
        assertThat(user.getName(), is("해린"));
    }

    /**
     * test 요구사항
     * 1. 유지민을 넣으면 유지민이 있는가
     * 2. 강해린을 넣으면 강해린이 있는가
     * 3. 가장 최신 데이터를 가져오면 강해린인가?
     */
    @Test
    public void test01() throws SQLException, ClassNotFoundException {
        // int cnt = jdbcTemplateDAO.countAll1();
        User user1 = new User();
        user1.setName("유지민");
        user1.setNameGroup("에스퐈");
        int result = jdbcTemplateDAO.insert(user1);
        assertThat(result, is(1));

        User user2 = jdbcTemplateDAO.selectByName("유지민");
        checkSameUser(user1, user2);

        User user3 = new User();
        user3.setName("강해린");
        user3.setNameGroup("뉴진스");
        result = jdbcTemplateDAO.insert(user3);
        assertThat(result, is(1));

        User user4 = jdbcTemplateDAO.selectByName("강해린");
        checkSameUser(user3, user4);

        List<User> userList = jdbcTemplateDAO.selectAll();
        assertThat(userList.get(0).getName(), anyOf(is("강해린"), is("유지민")));
    }

    /**
     * 좋은 습관 : test code에서 반복되는 코드를 메서드화 하는 것
     */
    private void checkSameUser(User user1, User user2) {
        assertThat(user1.getName(), is(user2.getName()));
        assertThat(user1.getNameGroup(), is(user2.getNameGroup()));
    }

    /**
     * negative test : 예외 상황에 대한 테스트
     * 예외 상황 : 만일 selectAll()이 비어있다면
     * 기대값 : 0을 리턴하는가
     *
     * 로드 존슨 (스프링 개발자)는 테스트 작성 시 negative test를 먼저 작성한다.
     * */

   /* @Test
    public void deleteThenCnt() {
        jdbcTemplateDAO.deleteAll();
        List<User> userList = jdbcTemplateDAO.selectAll();
        assertThat(userList.size(), is(0));
    }*/

}

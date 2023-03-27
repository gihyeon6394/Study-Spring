package com.tob.part3;

import com.tob.part3.dao.DaoFactorySpring;
import com.tob.part3.dao.GoodDAO;
import com.tob.part3.vo.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DaoFactorySpring.class)

public class UserTest {
    private GoodDAO goodDAO;

    @Autowired
    private ApplicationContext ac;

    static Set<UserTest> setUserTest = new HashSet<>(); //매번 새로운 test object를 만드는가?

    static  ApplicationContext applicationContext = null; //주입받은 하나의 context만 사용하는가?


    public static void main(String args[]) throws SQLException, ClassNotFoundException {

        JUnitCore.main("com.tob.part3.UserTest");
    }
    @Before
    public void beforeTest() {
        goodDAO = this.ac.getBean("goodDAO", GoodDAO.class); // getBean() : Dependency lookup

    }

    // 하니가 있는지?
    @Test
    public void existHani() throws SQLException, ClassNotFoundException {

        User hani = goodDAO.getUserByName6("팜하니");
        assertThat(hani.getName(), is("팜하니"));

    }

//    @Test
//    public void existNewJeans() throws SQLException, ClassNotFoundException {
//        List<User> userList = goodDAO.getUsersByNameGroup("뉴진스");
//        assertThat(userList.size(), greaterThan(0));
//
//    }
//
//    @Test
//    public void existNewJeans2() throws SQLException, ClassNotFoundException {
//
//        List<User> userList = goodDAO.getUsersByNameGroup("뉴진스");
//        assertThat(userList.size(), greaterThan(0));
//    }
//
//    @Test(expected = EmptyResultDataAccessException.class) //test 중 해당 exception 을 expected 중
//    public void existIVE() throws SQLException, ClassNotFoundException {
//
//        goodDAO.getUsersByNameGroup("아이브");
//
//        assertThat(setUserTest, not(hasItem(this))); //매번 새로운 test object를 만드는가?
//    }
}

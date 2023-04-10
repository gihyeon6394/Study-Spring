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
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

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

}

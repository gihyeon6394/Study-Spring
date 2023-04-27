package com.tob.part5;

import com.tob.part5.dao.JDBCTemplateDAO;
import com.tob.part5.vo.Level;
import com.tob.part5.vo.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


@RunWith(SpringRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class UserTest {

    private JDBCTemplateDAO jdbcTemplateDAO;

    @Autowired
    private ApplicationContext ac;

    private User user1;
    private User user2;
    private User user3;


    public static void main(String args[]) throws SQLException, ClassNotFoundException {

        JUnitCore.main("com.tob.part5.UserTest");
    }

    @Before
    public void beforeTest() {
        jdbcTemplateDAO = this.ac.getBean("jdbcTemplateDAO5", JDBCTemplateDAO.class); // getBean() : Dependency lookup
        deleteAll();
        setUp();

    }

    private void deleteAll() {
        jdbcTemplateDAO.deleteAll();
    }

    public void setUp() {
        user1 = new User.Builder().name("카리나").nameGroup("에스파").level(Level.GOLD).build();
        user2 = new User.Builder().name("지젤").nameGroup("에스파").level(Level.BASIC).build();
        user3 = new User.Builder().name("팜하니").nameGroup("뉴진스").level(Level.GOLD).build();
    }


   /* @Test
    public void countAll() throws SQLException, ClassNotFoundException {
        // int cnt = jdbcTemplateDAO.countAll1();
        int cnt = jdbcTemplateDAO.countAll2();
        assertThat(cnt, is(not(0)));
    }
*/

    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException {

        int result = jdbcTemplateDAO.add(user1);
        assertThat(result, is(1));

        checkSameUser(user1, jdbcTemplateDAO.getByUserName(user1.getName()));


        result = jdbcTemplateDAO.add(user2);
        assertThat(result, is(1));

        checkSameUser(user2, jdbcTemplateDAO.getByUserName(user2.getName()));


        result = jdbcTemplateDAO.add(user3);
        assertThat(result, is(1));

        checkSameUser(user3, jdbcTemplateDAO.getByUserName(user3.getName()));

    }

    private void checkSameUser(User user1, User user2) {
        assertThat(user1.getName(), is(user2.getName()));
        assertThat(user1.getNameGroup(), is(user2.getNameGroup()));
        assertThat(user1.getLevel(), is(user2.getLevel()));
    }

}

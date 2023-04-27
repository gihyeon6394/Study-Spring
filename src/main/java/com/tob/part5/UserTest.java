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

    private User karina;
    private User giselle;
    private User hani;


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
        karina = new User.Builder().name("카리나").nameGroup("에스파").level(Level.GOLD).build();
        giselle = new User.Builder().name("지젤").nameGroup("에스파").level(Level.BASIC).build();
        hani = new User.Builder().name("팜하니").nameGroup("뉴진스").level(Level.GOLD).build();
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

        int result = jdbcTemplateDAO.add(karina);
        assertThat(result, is(1));

        checkSameUser(karina, jdbcTemplateDAO.getByUserName(karina.getName()));


        result = jdbcTemplateDAO.add(giselle);
        assertThat(result, is(1));

        checkSameUser(giselle, jdbcTemplateDAO.getByUserName(giselle.getName()));


        result = jdbcTemplateDAO.add(hani);
        assertThat(result, is(1));

        checkSameUser(hani, jdbcTemplateDAO.getByUserName(hani.getName()));

    }

    private void checkSameUser(User user1, User user2) {
        assertThat(user1.getName(), is(user2.getName()));
        assertThat(user1.getNameGroup(), is(user2.getNameGroup()));
        assertThat(user1.getLevel(), is(user2.getLevel()));
    }

    @Test
    public void update() {
        deleteAll();
        jdbcTemplateDAO.add(karina);
        jdbcTemplateDAO.add(giselle);

        karina = new User.Builder().name("카리나").nameGroup("에스파시즌2").level(Level.GOLD).build();

        jdbcTemplateDAO.update(karina);

        User karinaUpdated = jdbcTemplateDAO.getByUserName(karina.getName());
        checkSameUser(karina, karinaUpdated);

        //원하는 User만 update 되었는지 확인
        User giselleInDB = jdbcTemplateDAO.getByUserName(giselle.getName());
        checkSameUser(giselle, giselleInDB);

    }

}

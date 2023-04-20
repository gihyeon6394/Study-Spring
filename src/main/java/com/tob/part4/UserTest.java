package com.tob.part4;

import com.tob.part3.vo.User;
import com.tob.part4.dao.JDBCTemplateDAO;
import com.tob.part4.exception.DuplicatedUserIDException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest(/*classes = DaoFactorySpring.class*/)
@ContextConfiguration("/applicationContext.xml")
public class UserTest {
    private JDBCTemplateDAO jdbcTemplateDAO;

    @Autowired
    private ApplicationContext ac;


    /**
     * 스프링의 {@link org.springframework.dao.DataAccessException}
     * JDBC, JPA, Hibernate 등 데이터 접근 API에서 발생할 수 있는 예외를 모두 추상화, 계층으로 만들어둠
     * 그래야 DAO 추상화가 가능해지니까
     * DAO에서 예외가 발생한다면?
     * JDBC는 DB에 종속적인 에러코드를 뱉지만,
     * 스프링이 예외의 계층구조에 따른 적절한 RuntimeException으로 전환하여 throws => DAO 추상화 가능!
     */
    public static void main(String args[]) throws SQLException, ClassNotFoundException {

        JUnitCore.main("com.tob.part4.UserTest");
    }

    @Before
    public void beforeTest() {
//        goodDAO = this.ac.getBean("goodDAO3", GoodDAO.class); // getBean() : Dependency lookup
        jdbcTemplateDAO = this.ac.getBean("jdbcTemplateDAO4", JDBCTemplateDAO.class); // getBean() : Dependency lookup

    }

    @Test(expected = DuplicateKeyException.class)
    public void add() {
        User user = new User();
        user.setName("강해린린");
        user.setNameGroup("뉴진스");
        user.setSeq(37);
        jdbcTemplateDAO.add(user);

    }

    @Test(expected = DuplicatedUserIDException.class)
    public void addWithCheckedException() {
        User user = new User();
        user.setName("강해린린");
        user.setNameGroup("뉴진스");
        user.setSeq(37);
        jdbcTemplateDAO.addWithCheckedException(user);
    }

}

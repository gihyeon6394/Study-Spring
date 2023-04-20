package com.tob.part4;

import com.tob.part3.vo.User;
import com.tob.part4.dao.JDBCTemplateDAO;
import com.tob.part4.dao.UserDao;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(/*classes = DaoFactorySpring.class*/)
@ContextConfiguration("/applicationContext.xml")
public class UserTestDao {
    private JDBCTemplateDAO jdbcTemplateDAO;

    @Autowired
    private ApplicationContext ac;

    @Autowired
    private UserDao userDao;

    @Autowired
    private DataSource dataSource;

    public static void main(String args[]) throws SQLException, ClassNotFoundException {

        JUnitCore.main("com.tob.part4.UserTestDao");
    }


    @Test(expected = DuplicateKeyException.class)
    public void add() {
        User user1 = new User();
        user1.setName("강해린린");
        user1.setNameGroup("뉴진스");
        user1.setSeq(40);
        userDao.add(user1);

        User user2 = new User();
        user2.setName("퐘하니");
        user2.setNameGroup("뉴진스");
        user2.setSeq(40);
        userDao.add(user1);

    }

    /**
     * DataAccessException 사용 시 주의사항
     * : 실제로 전환되는 예외의 리스트를 사전에 확인ㅎ해두자
     * - 기술 (JDBC, JPA, Hibernate, MyBatis) 별로 일관적인 DataAccessException 을 던져주진 않음
     * 즉 DuplicateKeyException 이 발생할 것이라고 예상하지만 JPA는 다른 예외를 던짐
     * 따라서 학습테스트를 통해 실제로 SQLException이 DuplicateKeyException으로 전환되는지 확인할 필요가 있음
     */

    //TODO : 실패하는 테스트임. 실패이유 분석할 것
    @Test
    public void exceptionTranslate() {
        try {
            User user1 = new User();
            user1.setName("강해린린");
            user1.setNameGroup("뉴진스");
            user1.setSeq(40);
            userDao.add(user1);

            User user2 = new User();
            user2.setName("퐘하니");
            user2.setNameGroup("뉴진스");
            user2.setSeq(40);
            userDao.add(user2);
        } catch (DuplicateKeyException e) {
            SQLException sqlException = (SQLException) e.getCause();
            SQLExceptionTranslator set = new SQLErrorCodeSQLExceptionTranslator(this.dataSource);
            assertThat(set.translate(null, null, sqlException), is(DuplicateKeyException.class));
        }

    }
}

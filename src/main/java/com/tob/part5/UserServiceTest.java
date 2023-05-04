package com.tob.part5;

import com.tob.part5.service.UserService;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(SpringRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class UserServiceTest {


    @Autowired
    private UserService userService;

    public static void main(String args[]) throws SQLException, ClassNotFoundException {

        JUnitCore.main("com.tob.part5.UserServiceTest");
    }

    /**
     * UserSerivce가 bean에 잘 등록되었는지 확인
     * */
    @Test
    public void bean() {
        assertThat(this.userService, is(notNullValue()));
    }
}

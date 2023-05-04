package com.tob.part5;

import com.tob.part5.dao.JDBCTemplateDAO;
import com.tob.part5.service.UserService;
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
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(SpringRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class UserServiceTest {


    @Autowired
    private ApplicationContext ac;

    @Autowired
    private UserService userService;

    private JDBCTemplateDAO userDao;


    private List<User> userList;

    public static void main(String args[]) throws SQLException, ClassNotFoundException {

        JUnitCore.main("com.tob.part5.UserServiceTest");
    }

    /**
     * UserSerivce가 bean에 잘 등록되었는지 확인
     */
    @Test
    public void bean() {
        assertThat(this.userService, is(notNullValue()));
    }

    @Before
    public void setUp() {
        userDao = this.ac.getBean("jdbcTemplateDAO5", JDBCTemplateDAO.class); // getBean() : Dependency lookup
        userList = Arrays.asList(new User.Builder().name("카리나").nameGroup("에스파").level(Level.GOLD).cntLogin(40).cntRecommend(20).build()
                , new User.Builder().name("지수").nameGroup("블랙핑크").level(Level.SILVER).cntLogin(50).cntRecommend(30).build()
                , new User.Builder().name("아이린").nameGroup("레드벨벳").level(Level.BASIC).cntLogin(49).cntRecommend(10).build()
                , new User.Builder().name("쯔위").nameGroup("트와이스").level(Level.BASIC).cntLogin(50).cntRecommend(30).build()
                , new User.Builder().name("수지").nameGroup("미쓰에이").level(Level.BASIC).cntLogin(50).cntRecommend(30).build()
                , new User.Builder().name("아이유").nameGroup("솔로").level(Level.BASIC).cntLogin(50).cntRecommend(30).build());
    }

    @Test
    public void upgradeLevels() {
        userDao.deleteAll();
        for (User user : userList) {
            userDao.add(user);
        }

        userService.upgradeLevels();

        checkLevel(userList.get(0), Level.GOLD);
        checkLevel(userList.get(1), Level.GOLD);
        checkLevel(userList.get(2), Level.BASIC);
        checkLevel(userList.get(3), Level.SILVER);
        checkLevel(userList.get(4), Level.SILVER);
        checkLevel(userList.get(5), Level.SILVER);
    }

    private void checkLevel(User user, Level basic) {
        User userUpdate = userDao.selectByName(user.getName());
        assertThat(userUpdate.getLevel(), is(basic));
    }
}

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
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static com.tob.part5.service.UserService.MIN_LOGIN_COUNT_FOR_SILVER;
import static com.tob.part5.service.UserService.MIN_RECOMMEND_COUNT_FOR_GOLD;
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


    @Autowired
    private DataSource dataSource;


    @Autowired
    private PlatformTransactionManager transactionManager;


    private JDBCTemplateDAO userDao;


    private List<User> userList;

    private User user;

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
        dataSource = this.ac.getBean("dataSource", DataSource.class);
        // 경계값을 사용하는 테스트
        userList = Arrays.asList(new User.Builder().name("카리나").nameGroup("에스파").level(Level.GOLD).cntLogin(MIN_LOGIN_COUNT_FOR_SILVER - 10).cntRecommend(MIN_RECOMMEND_COUNT_FOR_GOLD - 10).email("karina@email.com").build()
                , new User.Builder().name("지수").nameGroup("블랙핑크").level(Level.SILVER).cntLogin(MIN_LOGIN_COUNT_FOR_SILVER).cntRecommend(MIN_RECOMMEND_COUNT_FOR_GOLD).build()
                , new User.Builder().name("아이린").nameGroup("레드벨벳").level(Level.BASIC).cntLogin(MIN_LOGIN_COUNT_FOR_SILVER - 1).cntRecommend(MIN_RECOMMEND_COUNT_FOR_GOLD - 20).build()
                , new User.Builder().name("쯔위").nameGroup("트와이스").level(Level.BASIC).cntLogin(MIN_LOGIN_COUNT_FOR_SILVER).cntRecommend(MIN_RECOMMEND_COUNT_FOR_GOLD).email("zzzz@email.com").build()
                , new User.Builder().name("수지").nameGroup("미쓰에이").level(Level.BASIC).cntLogin(MIN_LOGIN_COUNT_FOR_SILVER).cntRecommend(MIN_RECOMMEND_COUNT_FOR_GOLD).build()
                , new User.Builder().name("아이유").nameGroup("솔로").level(Level.BASIC).cntLogin(MIN_LOGIN_COUNT_FOR_SILVER).cntRecommend(MIN_RECOMMEND_COUNT_FOR_GOLD).build());

        user = new User.Builder().name("테스트").nameGroup("테스트").level(Level.BASIC).cntLogin(1).cntRecommend(1).build();
    }

//    @Test
//    public void upgradeLevels() throws SQLException {
//        userDao.deleteAll();
//        for (User user : userList) {
//            userDao.add(user);
//        }
//
//        userService.upgradeLevels();
//
//        /**
//         * 문제점 : 일일이 기대값을 지정해야함
//         * */
//       /* checkLevel(userList.get(0), Level.GOLD);
//        checkLevel(userList.get(1), Level.GOLD);
//        checkLevel(userList.get(2), Level.BASIC);
//        checkLevel(userList.get(3), Level.SILVER);
//        checkLevel(userList.get(4), Level.SILVER);
//        checkLevel(userList.get(5), Level.SILVER);*/
//
//        checkLevelUpgraded(userList.get(0), false);
//        checkLevelUpgraded(userList.get(1), true);
//        checkLevelUpgraded(userList.get(2), false);
//        checkLevelUpgraded(userList.get(3), true);
//        checkLevelUpgraded(userList.get(4), true);
//        checkLevelUpgraded(userList.get(5), true);
//
//    }

    private void checkLevel(User user, Level levelExpected) {
        User userUpdate = userDao.selectByName(user.getName());
        assertThat(userUpdate.getLevel(), is(levelExpected));
    }

    private void checkLevelUpgraded(User user, boolean upgraded) {
        User userUpdate = userDao.selectByName(user.getName());
        if (upgraded) {
            assertThat(userUpdate.getLevel(), is(user.getLevel().getNextLevel()));
        } else {
            assertThat(userUpdate.getLevel(), is(user.getLevel()));
        }
    }

//    /**
//     * add() 호출 시
//     * 레벨이 비어있다면, BASIC
//     * 레벨이 있다면 그대로 유지
//     */
//    @Test
//    public void add() {
//        userDao.deleteAll();
//
//        User userWithLevel = userList.get(4); // BASIC
//        User userWithoutLevel = userList.get(0); // 레벨이 비어있는 사용자
//
//        userWithoutLevel.setLevel(null);
//
//        userService.add(userWithLevel);
//        userService.add(userWithoutLevel);
//
//        User userWithLevelRead = userDao.selectByName(userWithLevel.getName());
//        User userWithoutLevelRead = userDao.selectByName(userWithoutLevel.getName());
//
//        assertThat(userWithLevelRead.getLevel(), is(userWithLevel.getLevel()));
//        assertThat(userWithoutLevelRead.getLevel(), is(Level.BASIC));
//    }
//
//    @Test
//    public void upgradeLevel() {
//        Level[] levels = Level.values();
//        for (Level level : levels) {
//            if (level.getNextLevel() == null) continue;
//            user.setLevel(level);
//            user.upgradeLevel();
//            assertThat(user.getLevel(), is(level.getNextLevel()));
//        }
//    }
//
//    @Test(expected = IllegalStateException.class)
//    public void cannotUpgradeLevel() {
//        Level[] levels = Level.values();
//        for (Level level : levels) {
//            if (level.getNextLevel() != null) continue;
//            user.setLevel(level);
//            user.upgradeLevel();
//        }
//    }

    /**
     * upgradeLevel 도중 예외 발생 시 기존 트랜잭션도 rollback 되는지 테스트
     * test fail! 원자적이지 못함
     */

    @Test
    public void upgradeAllOrNothing() throws SQLException {
        UserService testUserService = new UserService.TestUserService(userList.get(3).getName());
        /**
         * bean으로 가져온거면 DI가 되어있지만, 아니기 때문에 수동 DI 해야함
         * */
        testUserService.setUserDao(userDao);
        // testUserService.setDataSource(dataSource);
        testUserService.setTransactionManager(transactionManager);
        userDao.deleteAll();
        for(User user : userList) {
            userDao.add(user);
        }
        try{
            testUserService.upgradeLevels();
        }catch (UserService.TestUserService.TestUserServiceException e) {

        }
        checkLevelUpgraded(userList.get(1), false);
    }


}

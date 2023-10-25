package com.tob.part6;

import com.tob.part5.MockMailSender;
import com.tob.part5.vo.Level;
import com.tob.part5.vo.User;
import com.tob.part6.dao.JDBCTemplateDAO;
import com.tob.part6.dao.UserDao;
import com.tob.part6.factorybean.TxProxyFactoryBean;
import com.tob.part6.service.TestUserService;
import com.tob.part6.service.UserService;
import com.tob.part6.service.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.tob.part5.service.UserService.MIN_LOGIN_COUNT_FOR_SILVER;
import static com.tob.part5.service.UserService.MIN_RECOMMEND_COUNT_FOR_GOLD;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class UserServiceTest {


    @Autowired
    private ApplicationContext ac;

    @Autowired
    private UserService userService;

    @Autowired
    private UserServiceImpl userServiceImpl;


    @Autowired
    private DataSource dataSource;


    @Autowired
    private PlatformTransactionManager transactionManager;


    @Autowired
    private MailSender mailSender;

    private JDBCTemplateDAO userDao;


    private List<User> userList;

    private User user;

    public static void main(String args[]) throws SQLException, ClassNotFoundException {

        JUnitCore.main("com.tob.part6.UserServiceTest");
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
        userDao = this.ac.getBean("userDao", JDBCTemplateDAO.class); // getBean() : Dependency lookup
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

    private void checkLevelUpgraded(User user, boolean upgraded) {
        User userUpdate = userService.selectByName(user.getName());
        if (upgraded) {
            assertThat(userUpdate.getLevel(), is(user.getLevel().getNextLevel()));
        } else {
            assertThat(userUpdate.getLevel(), is(user.getLevel()));
        }
    }

    @Test
    public void add() {
        userDao.deleteAll();

        User userWithLevel = userList.get(4); // BASIC
        User userWithoutLevel = userList.get(0); // 레벨이 비어있는 사용자

        userWithoutLevel.setLevel(null);

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        User userWithLevelRead = userDao.selectByName(userWithLevel.getName());
        User userWithoutLevelRead = userDao.selectByName(userWithoutLevel.getName());

        assertThat(userWithLevelRead.getLevel(), is(userWithLevel.getLevel()));
        assertThat(userWithoutLevelRead.getLevel(), is(Level.BASIC));
    }

    @Test
    public void upgradeLevels() throws SQLException {


        UserServiceImpl userServiceImpl = new UserServiceImpl();

        // Mock, DI
        MockUserDao mockUserDao = new MockUserDao(this.userList);
        userServiceImpl.setUserDao(mockUserDao);

        // Mock : 메일발송 여부
        MockMailSender mockMailSender = new MockMailSender();
        userServiceImpl.setMailSender(mockMailSender);

        userServiceImpl.upgradeLevels();

        List<User> updated = mockUserDao.getUpdated();
        assertThat(updated.size(), is(4));
        checkUserAndLevel(updated.get(0), "지수", Level.GOLD);
        checkUserAndLevel(updated.get(1), "쯔위", Level.SILVER);
        checkUserAndLevel(updated.get(2), "수지", Level.SILVER);
        checkUserAndLevel(updated.get(3), "아이유", Level.SILVER);

        List<String> request = mockMailSender.getRequests();

        assertThat(request.size(), greaterThan(0));

    }

    @Test
    public void upgradeLevelsMock() throws SQLException {


        UserServiceImpl userServiceImpl = new UserServiceImpl();

        // Mock, DI
        UserDao userDaoMock = Mockito.mock(UserDao.class);
        Mockito.when(userDaoMock.selectAll()).thenReturn(this.userList);

        userServiceImpl.setUserDao(userDaoMock);

        MailSender mailSenderMock = Mockito.mock(MailSender.class);
        userServiceImpl.setMailSender(mailSenderMock);

        userServiceImpl.upgradeLevels();

        Mockito.verify(userDaoMock, Mockito.times(4)).update(ArgumentMatchers.any(User.class));

        Mockito.verify(userDaoMock).update(userList.get(1));
        assertThat(userList.get(1).getLevel(), is(Level.GOLD));

        Mockito.verify(userDaoMock).update(userList.get(3));
        assertThat(userList.get(3).getLevel(), is(Level.SILVER));


        Mockito.verify(userDaoMock).update(userList.get(4));
        assertThat(userList.get(4).getLevel(), is(Level.SILVER));

        Mockito.verify(userDaoMock).update(userList.get(5));
        assertThat(userList.get(5).getLevel(), is(Level.SILVER));


        ArgumentCaptor<SimpleMailMessage> mailMessageArg = ArgumentCaptor.forClass(SimpleMailMessage.class);
        Mockito.verify(mailSenderMock, Mockito.times(4)).send(mailMessageArg.capture());

        List<SimpleMailMessage> mailMessages = mailMessageArg.getAllValues();
        assertThat(mailMessages.get(0).getTo()[0], is(userList.get(1).getEmail()));
        assertThat(mailMessages.get(1).getTo()[0], is(userList.get(3).getEmail()));


    }

    private void checkUserAndLevel(User user, String expectedID, Level expectedLevel) {
        assertThat(user.getName(), is(expectedID));
        assertThat(user.getLevel(), is(expectedLevel));
    }

    @Test
    @DirtiesContext
    public void upgradeAllOrNothing() throws Exception {
        TestUserService testUserService = new TestUserService(userList.get(3).getName());

        testUserService.setUserDao(userDao);

        MockMailSender mockMailSender = new MockMailSender();
        testUserService.setMailSender(mockMailSender);

        testUserService.setTransactionManager(transactionManager);


//        TransactionHandler transactionHandler = new TransactionHandler();
//        transactionHandler.setTarget(testUserService);
//        transactionHandler.setTransactionManager(transactionManager);
//        transactionHandler.setPattern("upgradeLevels");
//        UserService txUserService = (UserService) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{UserService.class}, transactionHandler);

        TxProxyFactoryBean txProxyFactoryBean = ac.getBean("&userService", TxProxyFactoryBean.class);
        txProxyFactoryBean.setTarget(testUserService);


        UserService txUserService = (UserService) txProxyFactoryBean.getObject();

        txUserService.deleteAll();
        userList.stream().forEach(user -> userService.add(user));

        try {
            // testUserService.upgradeLevels();
            // userServiceTx.upgradeLevels();
        } catch (TestUserService.TestUserServiceException e) {

        }
        checkLevelUpgraded(userList.get(1), false);

        List<String> request = mockMailSender.getRequests();

        assertThat(request.size(), greaterThan(0));

    }

    public static class MockUserDao implements UserDao {

        private List<User> users;
        private List<User> updated = new ArrayList<>();

        public MockUserDao(List<User> users) {
            this.users = users;
        }

        public List<User> getUpdated() {
            return updated;
        }

        public List<User> getUsers() {
            return users;
        }

        public void update(User user) {
            updated.add(user);
        }

        // 실수로 호출될 떄를 대비해 런타임 예외 발생
        @Override
        public void add(User user) {
            throw new UnsupportedOperationException();
        }

        @Override
        public User get(int seq) {
            throw new UnsupportedOperationException();

        }

        @Override
        public List<User> getAll() {
            throw new UnsupportedOperationException();

        }

        @Override
        public User selectByName(String name) {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<User> selectAll() {
            return users;
        }

        @Override
        public void deleteAll() {
            throw new UnsupportedOperationException();

        }

        @Override
        public int getCount() {
            throw new UnsupportedOperationException();
        }
    }


}

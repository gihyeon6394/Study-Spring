package com.tob.part2.dao;

import com.tob.part2.connectionMaker.ConnectionMaker;
import com.tob.part2.vo.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 초난감 DAO 개선
 */
public class GoodDAO {

    /**
     * GoodDAO 는 ConnectionMaker IF에 의존하고,
     * 런타임시 어떤 객체 (NConnectionMaker? DconnectionMaker?)를 사용할지는 외부 (스프링 IoC)로 부터 주입받아 결정.
     *
     * 장점 : 실제 구현 객체는 용도 (배포 환경, 사용자 등)에 따라 object factory에서만 설정해주면 됨.
     * */
    private ConnectionMaker connectionMaker;

    /**
     * Database conenction 전문 인터페이스 사용
     * */
    private DataSource dataSource;

    public GoodDAO() {

        /**
         * DL (dependency lookup) : 의존관계를 검색. (ex. getBean())
         * DI : 의존관계를 주입함 (메서드, 생성자를 통해)
         *
         * 의존관게 검색은 bean 이 아닌 오브젝트에서 주입 받고싶을 때 쓰면 됨.
         * 그 외에는 대부분 DI 가 유용
         * */
//        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(DaoFactory.class);
//        this.connectionMaker = ac.getBean("connectionMaker", ConnectionMaker.class);
    }
    public GoodDAO(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public GoodDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * DI by Setter
     * */
    public void setConnectionMaker(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public User getUserByName(String name) throws ClassNotFoundException, SQLException {

        Connection c = dataSource.getConnection();

        // 2. sql result
        PreparedStatement ps = c.prepareStatement("SELECT * FROM TB_USER WHERE NAME = ?");
        ps.setString(1, name);

        // 3. execute query
        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User();
        user.setSeq(rs.getString("SEQ"));
        user.setName(rs.getString("NAME"));

        // 4. close
        rs.close();
        ps.close();
        c.close();

        return user;
    }

    public List<User> getUsersByNameGroup(String nameGroup) throws SQLException {
        Connection c = dataSource.getConnection();

        // 2. sql result
        PreparedStatement ps = c.prepareStatement("SELECT * FROM TB_USER WHERE NAME_GROUP = ?");
        ps.setString(1, nameGroup);

        // 3. execute query
        ResultSet rs = ps.executeQuery();
        rs.next();

        List<User> userList = new ArrayList<>();
        while (rs.next()) {


            User user = new User();
            user.setSeq(rs.getString("SEQ"));
            user.setName(rs.getString("NAME"));
            user.setNameGroup(rs.getString("NAME_GROUP"));


            userList.add(user);
        }

        // 4. close
        rs.close();
        ps.close();
        c.close();


        if(CollectionUtils.isEmpty(userList)){
            throw new EmptyResultDataAccessException(1);
            //TODO : when list is empty
        }

        return userList;
    }
}

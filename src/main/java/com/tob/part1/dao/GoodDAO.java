package com.tob.part1.dao;

import com.tob.part1.connectionMaker.ConnectionMaker;
import com.tob.part1.connectionMaker.NConnectionMaker;
import com.tob.part1.vo.User;

import java.sql.*;

/**
 * 초난감 DAO 개선
 */
public class GoodDAO {


    /**
     * runtime DI = 느슨한 의존
     * 제 3자 (client, object factory, sprin IoC 등)에 의해 의존 객체 (NConnectionMaker)가 결정
     * */
    private ConnectionMaker connectionMaker;

    public GoodDAO() {

    }
    public GoodDAO(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }


    public User get(String id) throws ClassNotFoundException, SQLException {
        /**
         * 문제가 있는 DI
         * ConnectionMaker를 구현했지만, 실제 어떤 클래스 (NConnectionMaker)를 사용할 지 모델링 시점에 알아야함.
         *
         * */
        // ConnectionMaker connectionMaker = new NConnectionMaker();

        Connection c = connectionMaker.makeConnection();

        // 2. sql result
        PreparedStatement ps = c.prepareStatement("SELECT * FROM USER WHERE ID = ?");

        // 3. execute query
        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User();
        user.setId(rs.getString("ID"));
        user.setName(rs.getString("NAME"));
        user.setPwd(rs.getString("PWD"));

        // 4. close
        rs.close();
        ps.close();
        c.close();

        return user;
    }

}

package com.tob.part1.dao;

import com.tob.part1.connectionMaker.ConnectionMaker;
import com.tob.part1.vo.User;

import java.sql.*;

/**
 * 초난감 DAO 개선
 */
public class GoodDAO {

    private ConnectionMaker connectionMaker;

    public GoodDAO() {

    }
    public GoodDAO(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }


    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection c = connectionMaker.makeConnection();

//        2. sql result
        PreparedStatement ps = c.prepareStatement("SELECT * FROM USER WHERE ID = ?");

//        3. execute query
        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User();
        user.setId(rs.getString("ID"));
        user.setName(rs.getString("NAME"));
        user.setPwd(rs.getString("PWD"));

//        4. close
        rs.close();
        ps.close();
        c.close();

        return user;
    }

}

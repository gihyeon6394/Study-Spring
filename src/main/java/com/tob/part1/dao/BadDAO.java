package com.tob.part1.dao;

import com.tob.part1.vo.User;

import java.sql.*;

/**
 * 초난감 DAO
 */
public class BadDAO {

    public User get(String id) throws ClassNotFoundException, SQLException {

//        1. db connection
        Class.forName("com.mysql.jdbk.Driver");
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost/springbook", "srping", "book");

//        2. sql result
        PreparedStatement ps = c.prepareStatement("SELECT * FROM USER WHERE ID = ?");

//        3. execute query
        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User();
        user.setSeq(rs.getString("ID"));
        user.setName(rs.getString("NAME"));

//        4. close
        rs.close();
        ps.close();
        c.close();

        return user;
    }

}

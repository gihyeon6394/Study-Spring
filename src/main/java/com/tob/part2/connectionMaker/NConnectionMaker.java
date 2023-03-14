package com.tob.part2.connectionMaker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * NAVER company connection maker
 * */
public class NConnectionMaker implements ConnectionMaker {
    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdk.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost/springbook", "srping", "book");

    }
}

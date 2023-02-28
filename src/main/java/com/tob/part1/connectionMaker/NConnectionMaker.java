package com.tob.part1.connectionMaker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class NConnectionMaker implements ConnectionMaker{
    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbk.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost/springbook", "srping", "book");

    }
}

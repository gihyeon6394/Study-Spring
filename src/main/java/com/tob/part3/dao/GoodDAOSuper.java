package com.tob.part3.dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * GoodDAO 슈퍼 클래스
 *
 * GoodDAO에서 쓰이는 변하지 않는 부분들을 정의
 * */
public abstract class GoodDAOSuper {


    private DataSource dataSource;

    /**
     * 변하지 않는 부분은 슈퍼에 구현해두고,
     * */
    public com.tob.part3.vo.User getUserByName1(String name) {
        Connection c = null;
        com.tob.part3.vo.User user;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            c = dataSource.getConnection();

            ps = getPsForSelect(c);
            ps.setString(1, name);

            rs = ps.executeQuery();
            rs.next();
            user = new com.tob.part3.vo.User();
            user.setSeq(rs.getInt("SEQ"));
            user.setName(rs.getString("NAME"));


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

            if (ps != null) {
                try {
                    ps.close(); // 문제는 이 .close(); 도 SQLException을 던짐!!
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }


        return user;
    }

    /**
     * 변하는 부분은 구현체가 구현하도록 추상메서드로 둔다.
     * */
    abstract protected PreparedStatement getPsForSelect(Connection c) throws SQLException;
}

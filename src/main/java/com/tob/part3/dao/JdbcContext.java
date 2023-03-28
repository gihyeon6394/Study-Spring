package com.tob.part3.dao;

import com.tob.part3.vo.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * 스프링 DI는 인터페이스를 사이에 두고 인터페이스의 구현체를 런타임에서 주입해서 사용하는 것이 일반적이나,
 * JdbcContext는 인터페이스일 필요가 없다.
 * 왜냐면 기능이 바뀌는 내용이 아니니까. 즉, 확장성을 고려해서 인터페이스로 설계한뒤 구현체로 상세 구현할 필요가 없으니까
 * */
public class JdbcContext {


    private DataSource dataSource;

    public JdbcContext(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public User contextWithStrategy(PsStrategy psStrategy) {
        Connection c = null;
        com.tob.part3.vo.User user;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            c = dataSource.getConnection();

            ps = psStrategy.getPsForSelect(c);

            rs = ps.executeQuery();
            rs.next();
            user = new User();
            user.setSeq(rs.getString("SEQ"));
            user.setName(rs.getString("NAME"));


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

            if (ps != null) {
                try {
                    ps.close();
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

}

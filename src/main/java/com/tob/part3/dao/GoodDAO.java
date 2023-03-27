package com.tob.part3.dao;

import com.tob.part1.connectionMaker.ConnectionMaker;
import com.tob.part1.vo.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GoodDAO {


    private ConnectionMaker connectionMaker;

    private DataSource dataSource;

    public GoodDAO() {
//        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(DaoFactory.class);
//        this.connectionMaker = ac.getBean("connectionMaker", ConnectionMaker.class);
    }

    public GoodDAO(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public GoodDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setConnectionMaker(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public User get(int id) throws ClassNotFoundException, SQLException {

        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement("SELECT * FROM TB_USER WHERE SEQ = ?");
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User();
        user.setSeq(rs.getString("SEQ"));
        user.setName(rs.getString("NAME"));

        rs.close();
        ps.close();
        c.close();

        return user;
    }


    /**
     * 기존 GoodDAO가 가진 문제점
     * 예외를 던지고 있음 -> 그렇다고 호출한자가 적당하게 처리하고 있지도 않음 (자원 반납은 어디서 하는가?)
     * 문제점 : 예외를 올바르게 처리하지 못하고 있고, 그 결과로 중간에 예외가 발생하면 자원 (Connection, PreparedStatement)을 반납하지 못하고 있음
     *
     * solution
     * - JDBC는 try-catch-finally를 권장하고 있음.
     * - 따라서 아래와 같이 try-catch-finally를 통해 반드시 자원반납을 이룰 것.
     */
    public com.tob.part2.vo.User getUserByName(String name) /*throws ClassNotFoundException, SQLException*/ {


        Connection c = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        com.tob.part2.vo.User user;
        try {
            c = dataSource.getConnection();

            ps = c.prepareStatement("SELECT * FROM TB_USER WHERE NAME = ?");
            ps.setString(1, name);

            rs = ps.executeQuery();
            rs.next();
            user = new com.tob.part2.vo.User();
            user.setSeq(rs.getString("SEQ"));
            user.setName(rs.getString("NAME"));

            /**
             * 일반적으로 서버는 제한된 개수의 DB Connection 수를 유지하도록 한다.
             * 만일 반납하지 못한 Connection 이 쌓여가서 커넥션 풀이 가득 찬다면 서버가 멈출수도 있다!!!
             * */
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


        /**
         * 리소스 반환, close();
         * 일반적으로 Connection과 PreparedStatement 같은 자원은 풀 (pool) 개념으로 사용됨
         * 즉 pool 안에 제한된 수의 리소스를 만들어두고 요청한 자에 한해 제공하고 리소스를 돌려받아 (close();) 운용
         * 따라서 close();는 '닫는다'보다 풀에 '반환한다'의 개념으로 이해해보자
         *
         * 그리고 이렇게 pool 방식으로 운용되는 리소스는 가능하면 빨리 반환해주자. (쓸데없이 가지고 있지말고)
         *
         * */

        return user;
    }


    /**
     * 위에서 개선한 getUserByName()이 가진 문제점
     * 1. try-catch-finally 2중 이상 중첩 등으로 너무 가독성이 떨어짐
     * 2. Connection이 생성되는 메서드마다 위 구조로 짠다면 코드의 중복, 복잡도가 너무 심해짐
     *
     * solution
     * a. 변하지 않으면서 많은 곳에서 쓰이는 코드 (위 try-catch-finally)
     * b. 로직에 따라 확장, 변함이 잦은 코드
     * a와 b를 분리하자!!!!
     *
     * */



}

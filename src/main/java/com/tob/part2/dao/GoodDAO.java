package com.tob.part2.dao;

import com.tob.part2.connectionMaker.ConnectionMaker;
import com.tob.part2.vo.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public User get(int id) throws ClassNotFoundException, SQLException {
        /**
         * 문제가 있는 DI
         * ConnectionMaker를 구현했지만, 실제 어떤 클래스 (NConnectionMaker)를 사용할 지 모델링 시점에 알아야함.
         *
         * */
        // ConnectionMaker connectionMaker = new NConnectionMaker();

        // Connection c = connectionMaker.makeConnection();
        Connection c = dataSource.getConnection();

        // 2. sql result
        PreparedStatement ps = c.prepareStatement("SELECT * FROM TB_USER WHERE SEQ = ?");
        ps.setInt(1, id);

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

}

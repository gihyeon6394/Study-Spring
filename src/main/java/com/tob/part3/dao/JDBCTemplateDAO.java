package com.tob.part3.dao;

import com.tob.part3.vo.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 * 스프링의 템플릿/콜백 패턴의 대표적 : JDBCTemplate
 * 강한 응집도 : {@link JDBCTemplateDAO}
 * -> 디비 물리명이 바뀌면 여기가 다 바뀌어야함
 *
 * 약한 응집도 : {@link JDBCTemplateDAO}와 JDBCTemplate
 * -> 디비 사용방식, 예외처리, 리소스 반납은 모두 JDBCTemplate에 있음.
 * TODO : 둘 사이를 더 낮은 응집도로 두고싶다면? => JDBCTemplate을 빈으로 등록 후 {@link org.springframework.jdbc.core.JdbcOperations}을 통해 DI 해봐라
 *
 * TODO.개선점 : {@link JDBCTemplateDAO}의 개선점
 * 1. userRowMapper가 반복됨. 이거 디비 안바뀌는한 절대 안바뀌는 정보 아님?
 * 2. 강한응집도가 오히려 안좋을지도? 디비 물리명 바뀌면 소스 수정해야함
 */
public class JDBCTemplateDAO {


    /**
     * JdbcContext는 버리고,
     * JdbcTemplate을 써보자
     */
//    private  JdbcContext jdbcContext;

    private JdbcTemplate jdbcTemplate;


    // 더이상 쓰지 않고, 주입함
    // private DataSource dataSource;

    /**
     * userRowMapper 는 무상태 객체다.
     * 상태정보가 없는 객체는 multi-thread 에서 동시에 사용해도 thread-safe
     * */
    private RowMapper<User> userRowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setName(resultSet.getString("name"));
            user.setNameGroup(resultSet.getString("name_group"));
            user.setSeq(resultSet.getInt("seq"));
            return user;
        }
    };

    public JDBCTemplateDAO() {
    }


//    public void setJdbcContext(JdbcContext jdbcContext) {
//        this.jdbcContext = jdbcContext;
//    }


    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int insert(User user) {
        //이거 너무 불편함. sql만 던질 수 있게 하자
     /*   return  this.jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                return null;
            }
        });*/

        return this.jdbcTemplate.update("insert into tb_user ( NAME, DT_INS, NAME_GROUP) " +
                "values (?, now(), ?);", user.getName(), user.getNameGroup());
    }

    public int countAll1() {
        /**
         * 문제점 : 너무 복잡함
         * solution : jdbcTemplate.queryForObject() 이용
         * */
        return this.jdbcTemplate.query(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                return connection.prepareStatement("select count(*) from tb_user;");
            }
        }, new ResultSetExtractor<Integer>() {
            @Override
            public Integer extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                resultSet.next();
                return resultSet.getInt(1);
            }
        });
    }


    public int countAll2() {

        return this.jdbcTemplate.queryForObject("select count(*) from tb_user;", Integer.class);


    }

    public User selectByName(String name) {
        return this.jdbcTemplate.queryForObject("select * from tb_user where name=? ;", new Object[]{name}, userRowMapper);
    }

    public List<User> selectAll() {
        return this.jdbcTemplate.query("select * from tb_user order by dt_ins desc ;",userRowMapper);

    }

    public void deleteAll() {
        this.jdbcTemplate.execute("delete from tb_user;");
    }
}

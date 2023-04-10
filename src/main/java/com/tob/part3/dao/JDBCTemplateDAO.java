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


/**
 * 스프링의 템플릿/콜백 패턴의 대표적 : JDBCTemplate
 */
public class JDBCTemplateDAO {


    /**
     * JdbcContext는 버리고,
     * JdbcTemplate을 써보자
     */
//    private  JdbcContext jdbcContext;

    private JdbcTemplate jdbcTemplate;


    private DataSource dataSource;


    public JDBCTemplateDAO() {
    }


//    public void setJdbcContext(JdbcContext jdbcContext) {
//        this.jdbcContext = jdbcContext;
//    }


    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.dataSource = dataSource;
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
        return this.jdbcTemplate.queryForObject("select * from tb_user where name=? ;", new Object[]{name}, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setName(resultSet.getString("name"));
                user.setNameGroup(resultSet.getString("name_group"));
                user.setSeq(resultSet.getInt("seq"));
                return user;
            }
        });

    }
}

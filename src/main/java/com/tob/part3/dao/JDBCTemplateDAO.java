package com.tob.part3.dao;

import com.tob.part3.vo.User;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


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


}

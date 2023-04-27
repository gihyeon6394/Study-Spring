package com.tob.part5.dao;

import com.tob.part5.vo.Level;
import com.tob.part5.vo.User;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JDBCTemplateDAO {

    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> userRowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {

            return new User.Builder()
                    .seq(resultSet.getInt("seq"))
                    .name(resultSet.getString("name"))
                    .nameGroup(resultSet.getString("name_group"))
                    .level(Level.valueOf(resultSet.getInt("level")))
                    .build();
        }
    };

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public int add(User user) throws DuplicateKeyException {

        return this.jdbcTemplate.update("insert into tb_user (SEQ, NAME, DT_INS, NAME_GROUP, LEVEL)" +
                "values (?, ?, now(), ?, ?) ", user.getSeq(), user.getName(), user.getNameGroup(), user.getLevel().intValue());
    }


    public int countAll2() {

        return this.jdbcTemplate.queryForObject("select count(*) from tb_user;", Integer.class);


    }

    public User selectByName(String name) {
        return this.jdbcTemplate.queryForObject("select * from tb_user where name=? ;", new Object[]{name}, userRowMapper);
    }

    public List<User> selectAll() {
        return this.jdbcTemplate.query("select * from tb_user order by dt_ins desc ;", userRowMapper);

    }

    public User getByUserName(String userName) {
        return this.jdbcTemplate.queryForObject("select * from tb_user where name=? ;", new Object[]{userName}, userRowMapper);
    }

    public void deleteAll() {
        this.jdbcTemplate.execute("delete from tb_user;");
    }


}

package com.tob.part5.dao;

import com.tob.part3.vo.User;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class UserDaoJDBC implements UserDao {

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void add(User user) throws DuplicateKeyException {
        this.jdbcTemplate.update("insert into tb_user (SEQ, NAME, DT_INS, NAME_GROUP)" +
                "values (?, ?, now(), ?) ", user.getSeq(), user.getName(), user.getNameGroup());
    }

    @Override
    public User get(int seq) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public int getCount() {
        return 0;
    }
}

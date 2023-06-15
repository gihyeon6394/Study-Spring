package com.tob.part6.dao;

import com.tob.part5.vo.Level;
import com.tob.part5.vo.User;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JDBCTemplateDAO implements UserDao {

    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> userRowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {

            return new User.Builder().seq(resultSet.getInt("seq")).name(resultSet.getString("name")).nameGroup(resultSet.getString("name_group")).level(Level.valueOf(resultSet.getInt("level"))).cntLogin(resultSet.getInt("cnt_login")).cntRecommend(resultSet.getInt("cnt_recommend")).email(resultSet.getString("email")).build();
        }
    };

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    /**
     * 필드의 최초 기본값 초기화는 어디서?
     * 1. 클래스 : 최초 기본값은 최초가 아닐떄는 무의미한 정보라 클래스에서 하는 건 별로
     * 2. DAO : 여긴 디비에 값을 넣는거에만 관심이 있어야함
     * 3. service : 비즈니스적인 로직이므로 적절함!
     */
    @Override
    public void add(User user) throws DuplicateKeyException {

        this.jdbcTemplate.update("insert into tb_user (SEQ, NAME, DT_INS, NAME_GROUP, LEVEL, CNT_LOGIN, CNT_RECOMMEND, email)" + "values (?, ?, now(), ?, ?, ?, ?, ?) ", user.getSeq(), user.getName(), user.getNameGroup(), user.getLevel().intValue(), user.getCntLogin(), user.getCntRecommend(), user.getEmail());
    }

    @Override
    public User get(int seq) {
        return this.jdbcTemplate.queryForObject("select * from tb_user where seq=? ;", new Object[]{seq}, userRowMapper);
    }


    @Override
    public List<User> getAll() {
        return null;
    }


    public int countAll2() {

        return this.jdbcTemplate.queryForObject("select count(*) from tb_user;", Integer.class);


    }

    @Override
    public User selectByName(String name) {
        return this.jdbcTemplate.queryForObject("select * from tb_user where name=? ;", new Object[]{name}, userRowMapper);
    }

    @Override
    public List<User> selectAll() {
        return this.jdbcTemplate.query("select * from tb_user order by dt_ins desc ;", userRowMapper);

    }

    public User getByUserName(String userName) {
        return this.jdbcTemplate.queryForObject("select * from tb_user where name=? ;", new Object[]{userName}, userRowMapper);
    }

    @Override
    public void deleteAll() {
        this.jdbcTemplate.execute("delete from tb_user;");
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update("update tb_user set name_group = ?, level = ?, cnt_login = ?, cnt_recommend = ?, email = ?,  dt_updt = now() where name = ?;", user.getNameGroup(), user.getLevel().intValue(), user.getCntLogin(), user.getCntRecommend(), user.getEmail(), user.getName());
    }
}

package com.tob.part4.dao;

import com.tob.part3.vo.User;
import com.tob.part4.exception.DuplicatedUserIDException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * DAO를 만드는 이유
 * - 데이터 액세스 로직의 분리
 * - DAO user (SERVICE LAYER)가 DAO 내부에서 어떤 기술 (JDBC? JPA? Hibernate?)을 쓰는지 알 필요가 없음
 */
public class JDBCTemplateDAO {

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    //

    /**
     * throws 해주지 않아도 자기가 알아서 DuplicateKeyException를 던져줌
     * 즉, 스프링은 내부적으로 JDBC의 {@link java.sql.SQLException}을 런타임 {@link org.springframework.dao.DataAccessException}으로 전환해줌
     * 그래야 DAO 추상화가 가능해지니까.
     */
    public int add(User user) throws DuplicateKeyException {

        return this.jdbcTemplate.update("insert into tb_user (SEQ, NAME, DT_INS, NAME_GROUP)" +
                "values (?, ?, now(), ?) ", user.getSeq(), user.getName(), user.getNameGroup());
    }

    /**
     * 사용자 예외로 전환해줄수도 있음
     * 그런데 스프링의 에러코드 매핑을 통한 {@link org.springframework.dao.DataAccessException} 방식이 이상적임
     */

    public int addWithCheckedException(User user) {

        try {
            return this.jdbcTemplate.update("insert into tb_user (SEQ, NAME, DT_INS, NAME_GROUP)" +
                    "values (?, ?, now(), ?) ", user.getSeq(), user.getName(), user.getNameGroup());
        } catch (DuplicateKeyException e) {
            throw new DuplicatedUserIDException(e);
        }


    }
}

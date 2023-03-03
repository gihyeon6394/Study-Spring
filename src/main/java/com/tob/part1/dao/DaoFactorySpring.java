package com.tob.part1.dao;

import com.tob.part1.connectionMaker.ConnectionMaker;
import com.tob.part1.connectionMaker.NConnectionMaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

/**
 * Bean scan 위한 class
 * single tone object (bean)을 return
 */
@Configuration
public class DaoFactorySpring {


    /**
     * DI by Constructor of GoodDAO
     */
    @Bean
    public GoodDAO goodDAO() {
        // return new GoodDAO(connectionMaker());
        return new GoodDAO(dataSource());


        /**
         * DI by Setter
         * */
//        GoodDAO goodDAO = new GoodDAO();
//        goodDAO.setConnectionMaker(connectionMaker());
//        return  goodDAO;
    }

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost/tob");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }

    //    @Bean
    private ConnectionMaker connectionMaker() {
        return new NConnectionMaker();
        /**
         * ConnectionMaker를 확장한 클래스를 사용하고 싶을 때
         * 확장 클래스에 구현 클래스를 주입하여 리턴
         * */
//        return  new CountingConnectionMaker(new NConnectionMaker());
    }

}

package com.tob.part1.dao;

import com.tob.part1.connectionMaker.ConnectionMaker;
import com.tob.part1.connectionMaker.NConnectionMaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Bean scan 위한 class
 * single tone object (bean)을 return
 * */
@Configuration
public class DaoFactorySpring {


    //Bean Oject
    @Bean
    public GoodDAO goodDAO() {
        return new GoodDAO(connectionMaker());
    }

    private ConnectionMaker connectionMaker() {
        return new NConnectionMaker();
    }
}

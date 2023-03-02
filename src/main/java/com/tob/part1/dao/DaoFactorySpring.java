package com.tob.part1.dao;

import com.tob.part1.connectionMaker.ConnectionMaker;
import com.tob.part1.connectionMaker.CountingConnectionMaker;
import com.tob.part1.connectionMaker.NConnectionMaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Bean scan 위한 class
 * single tone object (bean)을 return
 * */
@Configuration
public class DaoFactorySpring {


    /**
     * DI by Constructor of GoodDAO
     * */
    @Bean
    public GoodDAO goodDAO() {
        return new GoodDAO(connectionMaker());


        /**
         * DI by Setter
         * */
//        GoodDAO goodDAO = new GoodDAO();
//        goodDAO.setConnectionMaker(connectionMaker());
//        return  goodDAO;
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

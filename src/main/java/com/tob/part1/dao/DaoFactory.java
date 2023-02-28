package com.tob.part1.dao;

import com.kghdev.di.dao.UserDAO;
import com.tob.part1.connectionMaker.NConnectionMaker;

/**
 * OBJECT factory = DI Container = IoC Container
 * 객체간의 의존관계를 설정하여 객체를 return 해준다.
 * not single tone (스프링은 IoC Container 는 single tone)
 *
 * */
public class DaoFactory {

    public GoodDAO goodDAO(){
//        return new GoodDAO();
        return new GoodDAO(new NConnectionMaker());
    }
}

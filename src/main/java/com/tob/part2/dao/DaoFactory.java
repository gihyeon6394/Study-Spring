package com.tob.part2.dao;

import com.tob.part2.connectionMaker.NConnectionMaker;

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

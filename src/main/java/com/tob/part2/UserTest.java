package com.tob.part2;

import com.tob.part2.connectionMaker.ConnectionMaker;
import com.tob.part2.connectionMaker.NConnectionMaker;
import com.tob.part2.dao.DaoFactory;
import com.tob.part2.dao.DaoFactorySpring;
import com.tob.part2.dao.GoodDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.sql.SQLException;
/**
 * 단위 테스트 (테스트 코드 작성)를 하는 이유
 * 1. 개발자가 만든 코드를 의도한대로 돌아가는지 가장 먼저 알아낸다
 * 2. 자동수행 테스트 (테스트 클래스 등)는 나중 개선작업에도 소스 수정이 정상작동하는지 확인하는데 편리 (테스트 코드 돌리면 됨)
 * 3. 객체지향적으로 설계가 가능 (테스트 코드를 반복해 가며 개선점이 보임)
 *
 *
 * UserTest의 문제점
 * 1. 사람이 직접 test 결과를 확인해봐야함
 * 2. UserTest.main() 을 계속해서 실행하면서 테스트해봐야함 (실행을 반복해야함)
 * */
public class UserTest {

    // 문제점 2
    public static void mㄱain(String args[]) throws SQLException, ClassNotFoundException {
        ConnectionMaker connectionMaker = new NConnectionMaker();

        //use constructor
        GoodDAO goodDAO = new GoodDAO(connectionMaker);

        // use object factory
        GoodDAO goodDAO1 = new DaoFactory().goodDAO();

        // use obeject factory in spring
        ApplicationContext ac = new AnnotationConfigApplicationContext(DaoFactorySpring.class);
        GoodDAO goodDAO2 = ac.getBean("goodDAO", GoodDAO.class); // getBean() : Dependency lookup

        // single tone registry
        GoodDAO goodDAO4 = new DaoFactory().goodDAO();
//        System.out.println(goodDAO1.equals(goodDAO4)); //false

        GoodDAO goodDAO3 = ac.getBean("goodDAO", GoodDAO.class);
//        System.out.println(goodDAO2.equals(goodDAO3)); //true

        // config bean by .xml
        ApplicationContext acXML = new GenericXmlApplicationContext("applicationContext.xml");
        GoodDAO goodDAOXML = acXML.getBean("goodDAO", GoodDAO.class); //
        System.out.println(goodDAOXML.get(6).toString()); //문제점 1

//        User user = goodDAO2.get(6);
//        System.out.println(user.toString());


    }
}

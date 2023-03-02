package com.tob.part1.connectionMaker;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * ConnectionMaker를 확장한 클래스
 * 확장 기능 : makeConnection() 호출 수 저장
 *
 * GoodDao -> ConnectionMaker IF (NConnectionMaker <- CountingConnectionMaker)
 */
public class CountingConnectionMaker implements ConnectionMaker {

    private ConnectionMaker realConnectionMaker;
    private int counter; //makeConenction() 호출 횟수

    public CountingConnectionMaker() {
    }

    public CountingConnectionMaker(ConnectionMaker realConnectionMaker) {
        this.realConnectionMaker = realConnectionMaker;
    }

    public int getCounter() {
        return counter;
    }

    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        this.counter++;
        return this.realConnectionMaker.makeConnection(); //실제 구현한 클래스 리턴
    }
}

package com.kghdev.di.dao;

import com.kghdev.di.vo.Car;

public class UserDAO {


    public static void main(String[] args){
        /**
         * Object의 identity, equality
         * */
        Car bmw1 = new Car("x1", "bmw");
        Car bmw2 = new Car("x1", "bmw");
        System.out.println(bmw1.equals(bmw2));


    }
}

package com.tob.part1.dao;

import com.kghdev.di.dao.UserDAO;

public class DaoFactory {

    public GoodDAO goodDAO(){
        return new GoodDAO();
    }
}

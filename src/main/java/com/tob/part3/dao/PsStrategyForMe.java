package com.tob.part3.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PsStrategyForMe implements PsStrategy{
    @Override
    public PreparedStatement getPsForSelect(Connection c) throws SQLException {
        PreparedStatement ps = c.prepareStatement("SELECT * FROM TB_USER WHERE NAME = ?");
        return ps;
    }
}

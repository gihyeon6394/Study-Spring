package com.tob.part3.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * b (변하는 부분)
 * */
public interface PsStrategy {

    PreparedStatement getPsForSelect(Connection c) throws SQLException;
}

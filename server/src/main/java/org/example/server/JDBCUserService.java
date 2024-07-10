package org.example.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUserService {
    private static final String DATABASE_URL = "jdbc:h2:mem:test;INIT=RUNSCRIPT FROM 'classpath:init.sql'";
    private static final String DB_USER_NAME = "sa";
    private static final String DB_PASSWORD = "";
    private static Connection connection;
    private static Statement statement;

    static Connection connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        connection = DriverManager.getConnection(DATABASE_URL, DB_USER_NAME, DB_PASSWORD);
        statement = connection.createStatement();
        return connection;
    }
}

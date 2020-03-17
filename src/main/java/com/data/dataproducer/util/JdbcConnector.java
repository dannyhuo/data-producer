package com.data.dataproducer.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author danny
 * @date 2020/3/16 6:59 PM
 */
public class JdbcConnector {

    /**
     * get jdbc connection
     * @param jdbcUrl
     * @param userName
     * @param password
     * @return
     */
    public static Connection getConn (String jdbcUrl, String userName, String password) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(jdbcUrl, userName, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * close connection
     * @param conn
     */
    public static void close (Connection conn) {
        if (null != conn) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * close connection and prepared statement
     * @param ps
     * @param conn
     */
    public static void close (PreparedStatement ps, Connection conn) {
        try {
            if (ps != null) {
                ps.close();
            }

            if (null != conn) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

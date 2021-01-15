package com.data.dataproducer.util;

import org.apache.kafka.common.protocol.types.Field;

import javax.security.auth.login.Configuration;
import java.sql.*;

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
    public static Connection getConn (String jdbcUrl, String userName, String password) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(jdbcUrl, userName, password);
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


    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://10.25.19.1:3306/ecommerce?useUnicode=true&zeroDateTimeBehavior=convertToNull&autoReconnect=true&characterEncoding=UTF-8&characterSetResults=UTF-8&allowMultiQueries=true&useCursorFetch=true&serverTimezone=UTC";
        String userName = "dev";
        String password = "123456";
        try {
            Connection connection = getConn(jdbcUrl, userName, password);
            String sql = "select * from mysql_numeric_type where 1 = 0";


            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                ResultSetMetaData queryMetaData = resultSet.getMetaData();
                int columnCount = queryMetaData.getColumnCount();

                for (int i = 1; i <= columnCount; i++) {
                    //列名
                    //System.out.println(queryMetaData.getColumnName(i));
                    //类型
                    System.out.println(queryMetaData.getColumnName(i) + ":" +
                            queryMetaData.getColumnTypeName(i) + "("
                            + queryMetaData.getPrecision(i)
                            + "," + queryMetaData.getScale(i) + ")");
                    //类型名称
                    System.out.println(queryMetaData.getColumnType(i));
                    //System.out.println(queryMetaData.getPrecision(i));
                    //System.out.println(queryMetaData.getScale(i));

                    //select查询展示的title 列名
                    //System.out.println(queryMetaData.getColumnLabel(i) +":" + queryMetaData.getColumnName(i));
                    //表名称(这个表只是直接表(可能是个临时的,比如子查询别名出来的表 tt),未必是真实表!!!)
                    //System.out.println(queryMetaData.getTableName(i));
                    //System.out.println(queryMetaData.getScale(i));
                    //System.out.println(queryMetaData.getPrecision(i));
                    //主键判定  无法确定列的真实对应的表, failed to do ...

                    System.out.println("=========================================");
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

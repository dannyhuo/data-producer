package com.data.dataproducer.util;

import com.data.dataproducer.entity.ImportInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 将数据上传至关系型数据库
 * @author danny
 * @date 2020/3/16 6:56 PM
 */
public class RdbmsImporterUtil {

    private static String jdbcUrl = "jdbc:mysql://nexus1:3306/wei_data";
    private static String userName = "wei_data";
    private static String password = "123456";
    private static String dataPath = "/Users/danny/works/idea/NEXUS/data-producer/src/main/resources/static/datas/for_zhuoyue_edu/wechat_user.csv";
    private static String tableName = "wechat_user";
    private static String splitChar = ",";
    private static int BATCH_SIZE = 2000;

    private static int minParamSize = 5;

    private static int count = 0;
    private static int totalCount = 0;
    private static int rowNum = 0;
    private static String defaultSplitChar = "\t";

    /**
     * 批量插入
     * @param param
     * @return
     */
    public static int import2rdbms(ImportInfo param) throws SQLException, ClassNotFoundException {
        jdbcUrl = param.getJdbcUrl();
        userName = param.getUserName();
        password = param.getPassword();
        dataPath = param.getDataPath();
        tableName = param.getTableName();
        if (null != param.getSplitChar()) {
            splitChar = param.getSplitChar();
        }

        if (null != param.getBatchSize()) {
            BATCH_SIZE = param.getBatchSize();
        }

        import2rdbms();
        return totalCount;
    }

    /**
     * 上传到关系型数据库
     */
    private static void import2rdbms() throws SQLException, ClassNotFoundException {
        String sql = buildPreparedSql(dataPath, tableName);
        final Connection conn = JdbcConnector.getConn(jdbcUrl, userName, password);
        PreparedStatement prepareStatment = null;
        try {
            conn.setAutoCommit(false);
            final PreparedStatement ps =  conn.prepareStatement(sql);
            prepareStatment = ps;
            List<String[]> data = new ArrayList<>();
            ReadUtil.read(dataPath, line -> {
                rowNum++;
                if (line != null && rowNum > 1) {
                    data.add(line.split(splitChar));
                    count++;

                    if (BATCH_SIZE == count) {
                        batchCommit(data, conn, ps);
                        data.clear();

                        totalCount += count;
                        count = 0;
                    }
                }
            });

            batchCommit(data, conn, ps);
        } finally {
            JdbcConnector.close(prepareStatment, conn);
        }
    }

    /**
     * 读取表头，构建prepared sql
     * @param dataPath
     * @param tableName
     * @return
     */
    private static String buildPreparedSql (String dataPath, String tableName) {
        String title = ReadUtil.readLine(dataPath);
        if (title.contains(defaultSplitChar)) {
            splitChar = defaultSplitChar;
        }

        String[] cols = title.split(splitChar);
        StringBuffer colStr = new StringBuffer();
        StringBuffer valueStr = new StringBuffer();
        for (int i = 0; i < cols.length; i++) {
            colStr.append(cols[i]);
            valueStr.append("?");
            if (i != cols.length - 1) {
                colStr.append(",");
                valueStr.append(",");
            }
        }

        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer
                .append("INSERT INTO ")
                .append(tableName)
                .append(" (")
                .append(colStr)
                .append(") VALUES (")
                .append(valueStr)
                .append(")");
        return sqlBuffer.toString();
    }


    /**
     * 批量提交
     * @param data
     * @param conn
     * @param preparedStatement
     */
    private static void batchCommit (List<String[]> data, Connection conn, PreparedStatement preparedStatement) {
        try {
            for (int k = 0; k < data.size(); k++) {
                String[] cols = data.get(k);
                for (int i = 0; i < cols.length; i++) {
                    preparedStatement.setString(i + 1, cols[i]);
                }

                preparedStatement.addBatch();
                preparedStatement.executeUpdate();
            }

            preparedStatement.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length >= minParamSize) {
            jdbcUrl = args[0];
            userName = args[1];
            password = args[2];
            dataPath = args[3];
            tableName = args[4];
            if (args.length == 6) {
                splitChar = args[5];
            } else if (args.length == 7) {
                BATCH_SIZE = Integer.parseInt(args[6]);
            }
        } else {
            System.out.println("The param not enough!");
        }

        try {
            import2rdbms();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

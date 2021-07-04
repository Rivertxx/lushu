package com.lushu.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String URL = "jdbc:mysql://localhost/road_book?"
            + "useUnicode = true & serverTimezone = GMT"
            + "& characterEncoding = utf8 & useSSL = false";
    private static final String NAME = "root";
    private static final String PASSWORD = "111213";
    private static Connection conn = null;

    // 静态代码块（将加载驱动、连接数据库放入静态块中）
    static{
        try {
            // 加载驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 获取数据库的连接
            conn = DriverManager.getConnection(URL, NAME, PASSWORD);
            System.out.println("Connect successful!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        return conn;
    }
}

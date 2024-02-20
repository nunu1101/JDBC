package com.ohgiraffers.section01.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Application1 {

    public static void main(String[] args) {

        /* DB 접속을 위한 Connection instance를 만들기 위한 레퍼런스 변수 선언 */
        Connection con = null;

        try {
            /* 사용할 Driver 등록 */
            Class.forName("com.mysql.cj.jdbc.Driver");

             con = DriverManager.getConnection("jdbc:mysql://localhost/menudb", "ohgiraffers","ohgiraffers");
            System.out.println("con = " + con);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

package com.ohgiraffers.section02.preparedstatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.ohgiraffers.common.JDBCTemplates.close;
import static com.ohgiraffers.common.JDBCTemplates.getConnection;

public class Application1 {
    public static void main(String[] args) {

        Connection con = getConnection();

        PreparedStatement pstmt = null;

        ResultSet rset = null;

        try {
            pstmt = con.prepareStatement("SELECT EMP_ID, EMP_NAME FROM EMPLOYEE");

            rset = pstmt.executeQuery();

            while(rset.next()){
                System.out.println(rset.getString("EMP_ID") +", "+rset.getString("EMP_NAME"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }   finally {
            close(pstmt);
            close(rset);
            close(con);
        }
    }
}

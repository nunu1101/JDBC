package com.ohgiraffers.section01.statement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplates.close;
import static com.ohgiraffers.common.JDBCTemplates.getConnection;

public class Application3 {
    public static void main(String[] args) {
        /* Connection 생성 */
        Connection con = getConnection();

        /* Statement 생성 */
        Statement stmt = null;

        /* ResultSet 생성 */
        ResultSet rset = null;

        /* Connection의 createStatement()를 이용한 Statement 인스턴스 생성 */
        try {
            stmt = con.createStatement();

            Scanner sc = new Scanner(System.in);
            System.out.print("조회하려는 사번을 입력해주세요 : ");
            String empId = sc.nextLine();
            String query = "SELECT EMP_ID, EMP_NAME FROM EMPLOYEE WHERE EMP_ID = '" +empId+ "'";

            System.out.println("query = " + query);

            rset = stmt.executeQuery(query);

            if(rset.next()){
                System.out.println(rset.getString("EMP_ID") + ", " + rset.getString("EMP_NAME"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            /* 사용한 자원 반납 */
            close(rset);
            close(stmt);
            close(con);
        }
    }
}

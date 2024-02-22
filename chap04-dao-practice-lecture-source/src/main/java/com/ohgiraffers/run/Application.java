package com.ohgiraffers.run;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application {
    public static void main(String[] args) {

        Connection con = getConnection();

        Properties prop = new Properties();

        PreparedStatement pstmt1 = null; //메뉴 마지막 번호 조회
        PreparedStatement pstmt2 = null; // 카테고리 전체 조회
        PreparedStatement pstmt3 = null; // 메뉴 추가

        ResultSet rset1 = null;
        ResultSet rset2 = null;
        int result = 0;

        int maxMenuCode = 0;
        List<Map<Integer, String>> categoryList = null;

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml"));

            String query1 = prop.getProperty("selectLastMenuCode");
            String query2 = prop.getProperty("selectAllCategoryList");
            String query3 = prop.getProperty("insertMenu");

//            System.out.println("query1 = " + query1);
//            System.out.println("query2 = " + query2);
//            System.out.println("query3 = " + query3);

            pstmt1 = con.prepareStatement(query1);
            pstmt2 = con.prepareStatement(query2);
            pstmt3 = con.prepareStatement(query3);

            rset1 = pstmt1.executeQuery();

            if(rset1.next()) {
                maxMenuCode = rset1.getInt("MAX(A.MENU_CODE)");
            }

//            System.out.println("maxMenuCode = " + maxMenuCode);

            rset2 = pstmt2.executeQuery();

            categoryList = new ArrayList<>();

            while (rset2.next()) {
                Map<Integer, String> category = new HashMap<>();
                category.put(rset2.getInt("CATEGORY_CODE"),rset2.getString("CATEGORY_NAME"));
                categoryList.add(category);
            }

//            System.out.println("categoryList = " + categoryList);

            Scanner sc = new Scanner(System.in);
            System.out.println("등록할 메뉴의 이름을 입력하세요 : ");
            String menuName = sc.nextLine();
            System.out.println("신규 메뉴의 가격을 입력하세요 : ");
            int menuPrice = sc.nextInt();
            System.out.println("카테고리를 선택해주세요(식사, 음료, 디저트, 한식, 퓨전) : ");
            sc.nextLine();
            String categoryName = sc.nextLine();
            System.out.println("바로 판매 메뉴에 적용하시겠습니까? (예/아니오) : ");
            String answer = sc.nextLine();

            int categoryCode = 0;
            switch (categoryName) {
                case "식사" : categoryCode = 1;break;
                case "음료" : categoryCode = 2;break;
                case "디저트" : categoryCode = 3;break;
                case "한식" : categoryCode = 4 ;break;
                case "퓨전" : categoryCode= 7 ;break;
            }

            String orderableStatus = "";
            switch (answer) {
                case "예" : orderableStatus = "Y";break;
                case "아니오" : orderableStatus = "N";break;
            }

            pstmt3.setInt(1, maxMenuCode + 1);
            pstmt3.setString(2, menuName);
            pstmt3.setInt(3, menuPrice);
            pstmt3.setInt(4, categoryCode);
            pstmt3.setString(5, orderableStatus);

            result = pstmt3.executeUpdate();


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rset1);
            close(rset2);
            close(pstmt1);
            close(pstmt2);
            close(pstmt3);
            close(con);
        }

        if (result > 0) {
            System.out.println("메뉴 등록 성공!");
        } else {
            System.out.println("메뉴 등록 실패!");
        }
    }
}

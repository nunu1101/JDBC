package com.ohgiraffers.section02.model.dao;

import com.ohgiraffers.section02.model.dto.MenuDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

import static com.ohgiraffers.common.JDBCTemplate.close;

public class MenuDAO {

    private Properties prop = new Properties();

    public MenuDAO(){
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int selectLastMenuCode(Connection con) {

        Statement stmt = null;
        ResultSet rset = null;

        int maxMenuCode = 0;

        String query = prop.getProperty("selectLastMenuCode");

        try {
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);

            if(rset.next()) {
                maxMenuCode = rset.getInt("MAX(A.MENU_CODE)");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rset);
            close(stmt);
        }
        return maxMenuCode;
    }

    public List<Map<Integer, String>> selectAllCategory(Connection con) {

        Statement stmt = null;
        ResultSet rset = null;

        List<Map<Integer, String>> categoryList = null;

        String query = prop.getProperty("selectAllCategoryList");

        try {
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);

            categoryList = new ArrayList<>();

            while (rset.next()){
                Map<Integer, String> category = new HashMap<>();
                category.put(rset.getInt("CATEGORY_CODE"), rset.getString("CATEGORY_NAME"));

                categoryList.add(category);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rset);
            close(stmt);
        }
        return categoryList;
    }
    public int insertNewMenu(Connection con, MenuDTO newMenu) {

        PreparedStatement pstmt = null;
        System.out.println("newMenu = " + newMenu);

        int result = 0;

        String query = prop.getProperty("insertMenu");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, newMenu.getCode());
            pstmt.setString(2, newMenu.getName());
            pstmt.setInt(3, newMenu.getPrice());
            pstmt.setInt(4, newMenu.getCategoryCode());
            pstmt.setString(5, newMenu.getOrderableStatus());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(pstmt);
        }
        return result;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.witc.recgen.db;

import edu.witc.recgen.business.Category;
import edu.witc.recgen.business.Ingredient;
import edu.witc.recgen.data.ConnectionPool;
import edu.witc.recgen.data.DbHelper;
import edu.witc.recgen.utility.DateUtil;
import static edu.witc.recgen.utility.DateUtil.getSqlDate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gerbe
 */
public class CategoryDb {
    
    public static int insert(Category category){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        int ingredientId = 0;

        String sql = "INSERT INTO category(id, category_name, active, last_mod_date)"
                + " VALUES (?,?,?,?)";
        
            try {
               ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); 
               ps.setInt(1, category.getId());
               ps.setString(2, category.getCategory_name());
               ps.setInt(3, category.getActive());
               ps.setDate(4, getSqlDate());
              
               ps.executeUpdate();
               ResultSet rs = ps.getGeneratedKeys();
               if(rs.next()){
                   ingredientId = rs.getInt(1);
               }
           } catch (SQLException e) {
               System.out.println("insertCategory: " + e);
               ingredientId = 0;
           } finally {
                DbHelper.closePreparedStatement(ps);
                pool.freeConnection(connection);
            }
            return ingredientId;
    }
    
    public static int update(Category category) throws SQLException{
        
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        int rowsEffected = 0;

        String sql = "UPDATE category "
                + "set id = ?, category_name = ?, active = ?, last_mod_date = ?"
                + " WHERE id = ?";
       
            try {
               ps = connection.prepareStatement(sql); 
               ps.setInt(1, category.getId());
               ps.setString(2, category.getCategory_name());
               ps.setInt(3, category.getActive());
               ps.setDate(4, getSqlDate());
               rowsEffected = ps.executeUpdate();
           } catch (SQLException e) {
               System.out.println("updateCategory: " + e);
               rowsEffected = 0;
           } finally {
                DbHelper.closePreparedStatement(ps);
                pool.freeConnection(connection);
            }
            return rowsEffected;
    }
    
    public static List<Category> getAllCategories(){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        
        List<Category> categories = new ArrayList<>();
        Category category = null;

        String sql = "SELECT id, category_name, active, last_mod_date "
                + "from ingredient";
        
            try {
               ps = connection.prepareStatement(sql);
               ResultSet rs = ps.executeQuery();
               if(rs.next()){
                   category = new Category();
                   do {
                       category = new Category(rs.getInt("id"),
                           rs.getString("category_name"),
                           DateUtil.getLocalDateFromSqlDate(rs.getDate("last_mod_date")),
                           rs.getInt("active"));
                           categories.add(category);
                   } while(rs.next());
               }
            } catch (SQLException e) {
               System.out.println("searchCategory: " + e);
               category = null;
            } finally {
                DbHelper.closePreparedStatement(ps);
                pool.freeConnection(connection);
            }
        return categories;
    }
}

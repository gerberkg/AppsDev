/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.witc.recgen.db;

import edu.witc.recgen.business.Favorite;
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
public class FavoriteDb {
    public static int insert(Favorite favorite){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        int favoriteId = 0;

        String sql = "INSERT INTO favorite(user_id, recipe_id, active, last_mod_date)"
                + " VALUES (?,?,?,?)";
        
            try {
               ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); 
               ps.setInt(1, favorite.getUser_id());
               ps.setInt(2, favorite.getRecipe_id());
               ps.setInt(3, favorite.getActive());
               ps.setDate(4, getSqlDate());
              
               ps.executeUpdate();
               ResultSet rs = ps.getGeneratedKeys();
               if(rs.next()){
                   favoriteId = rs.getInt(1);
               }
           } catch (SQLException e) {
               System.out.println("insertFavorite: " + e);
               favoriteId = 0;
           } finally {
                DbHelper.closePreparedStatement(ps);
                pool.freeConnection(connection);
            }
            return favoriteId;
    }
    
    public static int update(Favorite favorite) throws SQLException{
        
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        int rowsEffected = 0;

        String sql = "UPDATE favorite "
                + "set user_id = ?, recipe_id = ?, active = ?, last_mod_date = ?"
                + " WHERE id = ?";
       
            try {
               ps = connection.prepareStatement(sql); 
               ps.setInt(1, favorite.getUser_id());
               ps.setInt(2, favorite.getRecipe_id());
               ps.setInt(3, favorite.getActive());
               ps.setDate(4, getSqlDate());
               rowsEffected = ps.executeUpdate();
           } catch (SQLException e) {
               System.out.println("updateFavorite: " + e);
               rowsEffected = 0;
           } finally {
                DbHelper.closePreparedStatement(ps);
                pool.freeConnection(connection);
            }
            return rowsEffected;
    }
    
    public static List<Favorite> getFavoritesByUserId(int user_id){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        
        List<Favorite> favorites = new ArrayList<>();
        Favorite favorite = null;

        String sql = "SELECT id, user_id, recipe_id, active, last_mod_date "
                + "from favorite "
                + "WHERE user_id = ?";
        
            try {
               ps = connection.prepareStatement(sql);
               ps.setInt(1, user_id);
               ResultSet rs = ps.executeQuery();
               if(rs.next()){
                   favorite = new Favorite();
                   do {
                       favorite = new Favorite(rs.getInt("id"),
                           rs.getInt("user_id"),
                           rs.getInt("recipe_id"),
                           rs.getInt("active"),
                           DateUtil.getLocalDateFromSqlDate(rs.getDate("last_mod_date")));
                           favorites.add(favorite);
                   } while(rs.next());
               }
            } catch (SQLException e) {
               System.out.println("searchFavorite: " + e);
               favorite = null;
            } finally {
                DbHelper.closePreparedStatement(ps);
                pool.freeConnection(connection);
            }
        return favorites;
    }
}

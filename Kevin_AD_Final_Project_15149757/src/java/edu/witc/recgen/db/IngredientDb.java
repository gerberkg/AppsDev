/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.witc.recgen.db;

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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author gerbe
 */
public class IngredientDb {
    
    // insert a new ingredient into the database
    public static int insert(Ingredient ingredient)throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        int ingredientId = 0;

        String sql = "INSERT INTO ingredient(ingredient_name, active, last_mod_date)"
                + " VALUES (?,?,?)";
        
            try {
               ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); 
               ps.setString(1, ingredient.getIngredient_name());
               ps.setInt(2, ingredient.getActive());
               ps.setDate(3, getSqlDate());
              
               ps.executeUpdate();
               ResultSet rs = ps.getGeneratedKeys();
               if(rs.next()){
                   ingredientId = rs.getInt(1);
               }
           } catch (SQLException e) {
               System.out.println("insertFavorite: " + e);
               ingredientId = 0;
           } finally {
                DbHelper.closePreparedStatement(ps);
                pool.freeConnection(connection);
            }
            return ingredientId;
    }
    // insert a new ingredient into the database
    public static int insertRecipeIngredient(int ingredientId, int recipeId)throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        int recipeIngredientId = 0;
        
        String sql = "INSERT INTO recipe_ingredient(ingredient_id, recipe_id)"
                + " VALUES (?,?)";
        
            try {
               ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); 
               ps.setInt(1, ingredientId);
               ps.setInt(2, recipeId);
              
               ps.executeUpdate();
               ResultSet rs = ps.getGeneratedKeys();
               if(rs.next()){
                   recipeIngredientId = rs.getInt(1);
               }
           } catch (SQLException e) {
               System.out.println("insertFavorite: " + e);
               ingredientId = 0;
           } finally {
                DbHelper.closePreparedStatement(ps);
                pool.freeConnection(connection);
            }
            return recipeIngredientId;
    }
    
    // update the ingredient being passed in.
    public static int update(Ingredient ingredient) throws SQLException{
        
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        int rowsEffected = 0;

        String sql = "UPDATE ingredient "
                + "set ingredient_name = ?, active = ?, last_mod_date = ?"
                + " WHERE id = ?";
       
            try {
               ps = connection.prepareStatement(sql); 
               ps.setString(1, ingredient.getIngredient_name());
               ps.setInt(2, ingredient.getActive());
               ps.setDate(3, getSqlDate());
               ps.setInt(4, ingredient.getId());
               rowsEffected = ps.executeUpdate();
           } catch (SQLException e) {
               System.out.println("updateIngredient: " + e);
               rowsEffected = 0;
           } finally {
                DbHelper.closePreparedStatement(ps);
                pool.freeConnection(connection);
            }
            return rowsEffected;
    }
    
    // update the ingredient amount in the recipe_ingredient table
    public static int updateIngredientAmount(Ingredient ingredient) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        int rowsEffected = 0;

        String sql = "UPDATE recipe_ingredient "
                + "set ingredient_amount = ? "
                + "WHERE ingredient_id = ? && recipe_id = ?";
       
            try {
               ps = connection.prepareStatement(sql); 
               ps.setString(1, ingredient.getIngredient_amount());
               ps.setInt(2, ingredient.getId());
               ps.setInt(3, ingredient.getRecipeId());
               rowsEffected = ps.executeUpdate();
            } catch (SQLException e) {
               System.out.println("updateIngredientAmount: " + e);
               rowsEffected = 0;
            } finally {
                DbHelper.closePreparedStatement(ps);
                pool.freeConnection(connection);
            }
        return rowsEffected;
    }
    
    // get a list of all the ingredients 
    public static List<Ingredient> getAllIngredients(){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        
        List<Ingredient> ingredients = new ArrayList<>();
        Ingredient ingredient = null;

        String sql = "SELECT id, ingredient_name, active, last_mod_date "
                + "from ingredient";
        
            try {
               ps = connection.prepareStatement(sql);
               ResultSet rs = ps.executeQuery();
               if(rs.next()){
                   ingredient = new Ingredient();
                   do {
                       ingredient = new Ingredient(rs.getInt("id"),
                           rs.getString("ingredient_name"),
                           DateUtil.getLocalDateFromSqlDate(rs.getDate("last_mod_date")),
                           rs.getInt("active"));
                           ingredients.add(ingredient);
                   } while(rs.next());
               }
            } catch (SQLException e) {
               System.out.println("searchFavorite: " + e);
               ingredient = null;
            } finally {
                DbHelper.closePreparedStatement(ps);
                pool.freeConnection(connection);
            }
        return ingredients;
    }
    
    public static Ingredient getIngredientById(int ingredientId) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        
        Ingredient ingredient = null;
        
        String sql = "SELECT id, ingredient_name, active "
                + "FROM ingredient "
                + "WHERE id = ?";
        
        try {
               ps = connection.prepareStatement(sql);
               ps.setInt(1, ingredientId);
               ResultSet rs = ps.executeQuery();
               if(rs.next()){
                   ingredient = new Ingredient(rs.getInt("id"),
                   rs.getString("ingredient_name"),
                   rs.getInt("active"));
               } else{
                   ingredient = null;
               }
            } catch (SQLException e) {
               System.out.println("getIngredientById: " + e);
               ingredient = null;
            } finally {
                DbHelper.closePreparedStatement(ps);
                pool.freeConnection(connection);
            }
        return ingredient;
    }

    public static Ingredient getIngredientByIdAndRecId(int ingredientId, int recipeId) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        
        Ingredient ingredient = null;
        
        String sql = "SELECT i.id, i.ingredient_name, i.active, ri.ingredient_amount "
                + "FROM ingredient i "
                + "JOIN recipe_ingredient ri on i.id = ri.ingredient_id "
                + "WHERE i.id = ?  && ri.recipe_id = ?";
        
        try {
               ps = connection.prepareStatement(sql);
               ps.setInt(1, ingredientId);
               ps.setInt(2, recipeId);
               ResultSet rs = ps.executeQuery();
               if(rs.next()){
                   ingredient = new Ingredient(rs.getInt("id"),
                   rs.getString("ingredient_name"),
                   rs.getInt("active"),
                   rs.getString("ingredient_amount"));
               } else{
                   ingredient = null;
               }
            } catch (SQLException e) {
               System.out.println("getIngredientById: " + e);
               ingredient = null;
            } finally {
                DbHelper.closePreparedStatement(ps);
                pool.freeConnection(connection);
            }
        return ingredient;
    }

    // get list of ingredients linked to the recipe
    public static List<Ingredient> getIngredientsByRecipeId(int recipeId){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        
        List<Ingredient> ingredients = new ArrayList<>();
        Ingredient ingredient = null;

        String sql = "SELECT i.id, i.ingredient_name, ri.ingredient_amount "
                + "from ingredient i "
                + "JOIN recipe_ingredient ri on i.id = ri.ingredient_id "
                + "WHERE ri.recipe_id = ?";
        
            try {
               ps = connection.prepareStatement(sql);
               ps.setInt(1, recipeId);
               ResultSet rs = ps.executeQuery();
               if(rs.next()){
                   ingredient = new Ingredient();
                   do {
                        ingredient = new Ingredient(rs.getInt("i.id"),
                            rs.getString("i.ingredient_name"),
                            rs.getString("ri.ingredient_amount"));
                            ingredients.add(ingredient);
                   } while(rs.next());
               }
            } catch (SQLException e) {
               System.out.println("getIngredientByRecipeId: " + e);
               ingredient = null;
            } finally {
                DbHelper.closePreparedStatement(ps);
                pool.freeConnection(connection);
            }
        return ingredients;
    }
    
    // get list of ingredients linked to the recipe
    public static List<Ingredient> getRemainingIngredients(int recipeId, HttpServletRequest request, HttpServletResponse response){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        HttpSession session = request.getSession();
        
        List<Ingredient> ingredients = new ArrayList<>();
        Ingredient ingredient = null;
        
        List<Ingredient> recIngredients = new ArrayList<>();
        
        recIngredients = IngredientDb.getIngredientsByRecipeId(recipeId);
        //(List<Ingredient>) session.getAttribute("recipeIngredients");
        // String[] recIngrs = (String[]) recIngredients.toArray();
        StringBuilder markers = new StringBuilder();
        int[] recIngrIds = new int[recIngredients.size()];
        int i = 0;
        
        for(Ingredient recIngrId : recIngredients){
            recIngrIds[i] = recIngrId.getId();
            i++;
        }

        
        for (int j = 1; j < recIngredients.size(); j++){
            markers.append(",?");

        }

        String sql = "SELECT i.id, i.ingredient_name, ri.ingredient_amount "
                + "from ingredient i "
                + "JOIN recipe_ingredient ri on i.id = ri.ingredient_id "
                + "WHERE ri.recipe_id NOT IN (?" + markers + ")";
        
            try {
               ps = connection.prepareStatement(sql);
               for (i = 0; i < recIngrIds.length; i++){
                   ps.setInt(i + 1, recIngrIds[i]);
               }
               ResultSet rs = ps.executeQuery();
               if(rs.next()){
                   ingredient = new Ingredient();
                   do {
                        ingredient = new Ingredient(rs.getInt("i.id"),
                            rs.getString("i.ingredient_name"),
                            rs.getString("ri.ingredient_amount"));
                            ingredients.add(ingredient);
                   } while(rs.next());
               }
            } catch (SQLException e) {
               System.out.println("getRemainingIngredients: " + e);
               ingredient = null;
            } finally {
                DbHelper.closePreparedStatement(ps);
                pool.freeConnection(connection);
            }
        return ingredients;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.witc.recgen.db;

import edu.witc.recgen.business.Recipe;
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
public class RecipeDb {

    // insert a new ingredient into the database.
    public static int insert(Recipe recipe) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        int recipeId = 0;

        String sql = "INSERT INTO recipe(recipe_name, active, "
                + "serving_size, last_mod_date, instructions)"
                + " VALUES (?,?,?,?,?)";

        try {
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, recipe.getRecipe_name());
            ps.setInt(2, recipe.getActive());
            ps.setString(3, recipe.getServing_size());
            ps.setDate(4, getSqlDate());
            ps.setString(5, recipe.getInstructions());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                recipeId = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("insertRecipe: " + e);
            recipeId = 0;
        } finally {
            DbHelper.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return recipeId;
    }

    // update the existing recipe in the database
    public static int update(Recipe recipe) throws SQLException {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        int rowsEffected = 0;

        String sql = "UPDATE recipe "
                + "set recipe_name = ?, active = ?, "
                + "serving_size = ?, last_mod_date = ?, "
                + "instructions = ?"
                + " WHERE id = ?";

        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, recipe.getRecipe_name());
            ps.setInt(2, recipe.getActive());
            ps.setString(3, recipe.getServing_size());
            ps.setDate(4, getSqlDate());
            ps.setString(5, recipe.getInstructions());
            ps.setInt(6, recipe.getId());
            rowsEffected = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("updateRecipe: " + e);
            rowsEffected = 0;
        } finally {
            DbHelper.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return rowsEffected;
    }

    // get a list of all the recipes in the database
    public static List<Recipe> getAllRecipes() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        List<Recipe> recipes = new ArrayList<>();
        Recipe recipe = null;

        String sql = "SELECT id, recipe_name, "
                + "active, serving_size, last_mod_date, instructions "
                + "from recipe";

        try {
            ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                recipe = new Recipe();
                do {
                    recipe = new Recipe(rs.getInt("id"),
                            rs.getString("recipe_name"),
                            DateUtil.getLocalDateFromSqlDate(rs.getDate("last_mod_date")),
                            rs.getInt("active"),
                            rs.getString("serving_size"),
                            rs.getString("instructions"));
                    recipes.add(recipe);
                } while (rs.next());
            }
        } catch (SQLException e) {
            System.out.println("getAllRecipes: " + e);
            recipe = null;
        } finally {
            DbHelper.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return recipes;
    }

    // get recipe record based on the id being passed in.
    public static Recipe getRecipeById(int recipeId) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        Recipe recipe = null;

        String sql = "SELECT id, recipe_name, active, serving_size, instructions "
                + "FROM recipe "
                + "WHERE id = ?";

        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, recipeId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                recipe = new Recipe(rs.getInt("id"),
                        rs.getString("recipe_name"),
                        rs.getInt("active"),
                        rs.getString("serving_size"),
                        rs.getString("instructions"));
            } else {
                recipe = null;
            }

        } catch (SQLException e) {
            System.out.println("getRecipeById: " + e);
            recipe = null;
        } finally {
            DbHelper.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return recipe;
    }

    // get recipe records based on the ingredients selected.
    public static List<Recipe> getRecipesByIngredients(HttpServletRequest request, HttpServletResponse response) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        List<Recipe> recipes = new ArrayList<>();
        Recipe recipe = null;
        HttpSession session = request.getSession();

        // parse through the array of ingredient ids and append a "?" marker for each ingredient id in the array
        String[] selectedIngrs = (String[]) session.getAttribute("selectedIngredients");
        StringBuilder markers = new StringBuilder();
        for (int i = 1; i < selectedIngrs.length; i++) {
            markers.append(",?");
        }

        String sql = "SELECT DISTINCT r.id, r.recipe_name "
                + "FROM recipe r "
                + "JOIN recipe_ingredient ri on r.id = ri.recipe_id "
                + "WHERE r.id IN (SELECT ri.recipe_id FROM recipe_ingredient ri "
                + "WHERE ri.ingredient_id IN (?" + markers + "))";

        try {
            ps = connection.prepareStatement(sql);
            // loop through the array of ingredient ids and add a "ps.setInt() line for each ingredient id
            for (int i = 0; i < selectedIngrs.length; i++) {
                ps.setInt(i + 1, Integer.parseInt(selectedIngrs[i]));
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                do {
                    recipe = new Recipe(rs.getInt("id"),
                            rs.getString("recipe_name"));
                    recipes.add(recipe);
                } while (rs.next());
            }
        } catch (SQLException e) {
            System.out.println("getRecipesByIngredients: " + e);
            recipe = null;
        } finally {
            DbHelper.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return recipes;
    }

    // get recipe records based on the ingredients selected.
    public static List<Recipe> getRecipesByCategories(String inClause) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        List<Recipe> recipes = new ArrayList<>();
        Recipe recipe = null;

        String sql = "SELECT id, recipe_name, active, serving_size, instructions "
                + "FROM recipe "
                + "WHERE id IN (SELECT recipe_id FROM recipe_category "
                + "WHERE category_id IN(?))";

        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, inClause);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                do {
                    recipe = new Recipe(rs.getInt("id"),
                            rs.getString("recipe_name"),
                            rs.getInt("active"),
                            rs.getString("serving_size"),
                            rs.getString("instructions"));
                    recipes.add(recipe);
                } while (rs.next());
            }
        } catch (SQLException e) {
            System.out.println("getRecipesByCategories: " + e);
            recipe = null;
        } finally {
            DbHelper.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return recipes;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.witc.recgen.db;

import edu.witc.recgen.data.ConnectionPool;
import edu.witc.recgen.business.User    ;
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
 * @author 15149757
 */
public class UserDb {
    public static int insert(User user) throws SQLException{
        
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        int userId = 0;

        String sql = "INSERT INTO user(first_name, last_name, email_address, "
                + "user_id, password, active, isAdmin, last_mod_date)"
                + " VALUES (?,?,?,?,?,?,?,?)";
        
            try {
               ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); 
               ps.setString(1, user.getFirst_name());
               ps.setString(2, user.getLast_name());
               ps.setString(3, user.getEmail_address());
               ps.setString(4, user.getUser_id());
               ps.setString(5, user.getPassword());
               ps.setInt(6, user.getActive());
               ps.setInt(7, user.getIsAdmin());
               ps.setDate(8, getSqlDate());
              
               ps.executeUpdate();
               ResultSet rs = ps.getGeneratedKeys();
               if(rs.next()){
                   userId = rs.getInt(1);
               }
           } catch (SQLException e) {
               System.out.println("insertUser: " + e);
               userId = 0;
           } finally {
                DbHelper.closePreparedStatement(ps);
                pool.freeConnection(connection);
            }
            return userId;
    }
    
    public static int update(User user) throws SQLException{
        
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        int rowsEffected = 0;

        String sql = "UPDATE customer "
                + "set first_name = ?, last_name = ?, email_address = ?, "
                + "user_id = ?, active = ?, isAdmin, last_mod_date = ?"
                + " WHERE id = ?";
       
            try {
               ps = connection.prepareStatement(sql); 
               ps.setString(1, user.getFirst_name());
               ps.setString(2, user.getLast_name());
               ps.setString(3, user.getEmail_address());
               ps.setString(4, user.getUser_id());
               ps.setInt(5, user.getActive());
               ps.setInt(6, user.getIsAdmin());
               ps.setDate(7, getSqlDate());
               ps.setInt(8, user.getId());
               rowsEffected = ps.executeUpdate();
           } catch (SQLException e) {
               System.out.println("updateCustomer: " + e);
               rowsEffected = 0;
           } finally {
                DbHelper.closePreparedStatement(ps);
                pool.freeConnection(connection);
            }
            return rowsEffected;
    }
    
    public static List<User> getUsersByLastname(String lastname){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        
        List<User> users = new ArrayList<>();
        User user = null;

        String sql = "SELECT id, first_name, last_name, email_address, user_id, "
                + "password, isAdmin, active, isAdmin, last_mod_date "
                + "from customer "
                + "WHERE last_name = ?";
        
            try {
               ps = connection.prepareStatement(sql);
               ps.setString(1, lastname);
               ResultSet rs = ps.executeQuery();
               if(rs.next()){
                   user = new User();
                   do {
                       user = new User(rs.getInt("id"),
                           rs.getString("first_name"),
                           rs.getString("last_name"),
                           rs.getString("email_address"),
                           rs.getString("user_id"),
                           rs.getString("password"),
                           rs.getInt("isAdmin"),
                           rs.getInt("active"),
                           DateUtil.getLocalDateFromSqlDate(rs.getDate("last_mod_date")));
                           users.add(user);
                   } while(rs.next());
               }
            } catch (SQLException e) {
               System.out.println("searchCustomer: " + e);
               user = null;
            } finally {
                DbHelper.closePreparedStatement(ps);
                pool.freeConnection(connection);
            }
        return users;
    }
    
    public static User validateLogin(String username, String password) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        User user = null;

        String sql = "SELECT id, isAdmin "
                + "FROM user "
                + "WHERE user_id = ? && password = ?";
        
        try {
               ps = connection.prepareStatement(sql);
               ps.setString(1, username);
               ps.setString(2, password);
               ResultSet rs = ps.executeQuery();
               if(rs.next()){
                   user = new User(rs.getInt("id"),
                   rs.getInt("isAdmin"));
               } else {
                   user = null;
               }
               
            } catch (SQLException e) {
               System.out.println("validateLogin: " + e);
               user = null;
            } finally {
                DbHelper.closePreparedStatement(ps);
                pool.freeConnection(connection);
            }
        
        return user;
    }
    
//    public static Customer getCustomerById(int custId){
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
//        PreparedStatement ps = null;
//        Customer customer = null;
//
//        String sql = "SELECT c.id, c.first_name, c.last_name, c.phone, c.email, "
//                + "c.street_address, c.city, c.state_id, c.postal_code, c.comments, "
//                + "c.active, sl.short_desc, c.date_added, c.date_modified "
//                + "from customer c "
//                + "JOIN state_list sl on c.state_id = sl.id "
//                + "WHERE c.id = ?";
//        
//            try {
//               ps = connection.prepareStatement(sql);
//               ps.setInt(1, custId);
//               ResultSet rs = ps.executeQuery();
//               if(rs.next()){
//                   customer = new Customer(rs.getInt("c.id"),
//                        rs.getString("c.first_name"),
//                        rs.getString("c.last_name"),
//                        rs.getString("c.phone"),
//                        rs.getString("c.email"),
//                        rs.getString("c.street_address"),
//                        rs.getString("c.city"),
//                        rs.getInt("c.state_id"),
//                        rs.getString("c.postal_code"),
//                        rs.getString("c.comments"),
//                        rs.getInt("c.active"),
//                        rs.getString("sl.short_desc"),
//                        DateUtil.getLocalDateFromSqlDate(rs.getDate("c.date_added")),
//                        DateUtil.getLocalDateFromSqlDate(rs.getDate("c.date_modified")));
//               } else {
//                   customer = null;
//               }
//               
//            } catch (SQLException e) {
//               System.out.println("searchCustomer: " + e);
//               customer = null;
//            } finally {
//                DbHelper.closePreparedStatement(ps);
//                pool.freeConnection(connection);
//            }
//        return customer;
//    }
//
//    public static List<Customer> getAllCustomers() {
//        String sql = "SELECT c.id, c.first_name, c.last_name, c.phone, c.email, "
//                + "c.street_address, c.city, c.state_id, c.postal_code, c.comments, "
//                + "c.active, sl.short_desc, c.date_added, c.date_modified "
//                + "from customer c "
//                + "JOIN state_list sl on c.state_id = sl.id "
//                + "ORDER BY c.last_name";
//
//        List<Customer> customers = new ArrayList<>();
//        Customer customer;
//        
//        ConnectionPool pool = ConnectionPool.getInstance();
//        Connection connection = pool.getConnection();
//        PreparedStatement ps = null;
//
//        try {
//            ps = connection.prepareStatement(sql);
//            ResultSet rs = ps.executeQuery();
//        
//            if(rs.next()){
//                customer = new Customer();
//                do{
//                    customer = new Customer(rs.getInt("c.id"),
//                        rs.getString("c.first_name"),
//                        rs.getString("c.last_name"),
//                        rs.getString("c.phone"),
//                        rs.getString("c.email"),
//                        rs.getString("c.street_address"),
//                        rs.getString("c.city"),
//                        rs.getInt("c.state_id"),
//                        rs.getString("c.postal_code"),
//                        rs.getString("c.comments"),
//                        rs.getInt("c.active"),
//                        rs.getString("sl.short_desc"),
//                        DateUtil.getLocalDateFromSqlDate(rs.getDate("c.date_added")),
//                        DateUtil.getLocalDateFromSqlDate(rs.getDate("c.date_modified")));
//                        customers.add(customer);
//                }while (rs.next());
//            }
//        } catch (SQLException e) {
//            System.out.println("searchCustomer: " + e);
//            customer = null;
//        } finally {
//            DbHelper.closePreparedStatement(ps);
//            pool.freeConnection(connection);
//        }
//        return customers;
//    }// List user

}

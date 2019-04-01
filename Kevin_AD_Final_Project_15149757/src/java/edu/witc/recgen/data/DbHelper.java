/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.witc.recgen.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author 15149757
 */
public class DbHelper {
    public static void closeStatement(Statement s){
        try{
            if(s != null){
                s.close();
            }
        }
        catch(SQLException e){
            System.err.println(e);
        }
    }
    public static void closePreparedStatement(PreparedStatement ps){
        try{
            if(ps != null){
                ps.close();
            }
        }
        catch(SQLException e){
            System.err.println(e);
        }
    }
    public static void closeResultSet(ResultSet rs){
        try{
            if(rs != null){
                rs.close();
            }
        }
        catch(SQLException e){
            System.err.println(e);
        }
    }
    
}

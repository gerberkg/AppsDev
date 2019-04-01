/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.witc.recgen.business;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author 15149757
 */
public class User implements Serializable{
    private int id;
    private String user_id;
    private String password;
    private String first_name;
    private String last_name;
    private String email_address;
    private LocalDate last_mod_date;
    private int active;
    private int isAdmin;

    public User() {
        id = 0;
        user_id = "";
        password = "";
        first_name = "";
        last_name = "";
        email_address = "";
        last_mod_date = null;
        active = 1;
        isAdmin = 0;
    }

    public User(int id) {
        this.id = id;
    }

    public User(int id, int isAdmin) {
        this.id = id;
        this.isAdmin = isAdmin;
    }
    
    public User (String firstName, String lastName, String user_id, String password,
            String email, int active, int isAdmin){
        this.first_name = firstName;
        this.last_name = lastName;
        this.user_id = user_id;
        this.password = password;
        this.email_address = email;
        this.active = active;
        this.isAdmin = isAdmin;
    }

    public User (int id, String firstName, String lastName, String user_id, 
            String password, String email, int active, int isAdmin, 
            LocalDate last_mod_date){
        this.id = id;
        this.user_id = user_id;
        this.password = password;
        this.first_name = firstName;
        this.last_name = lastName;
        this.email_address = email;
        this.active = active;
        this.isAdmin = isAdmin;
        this.last_mod_date = last_mod_date;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public LocalDate getLast_mod_date() {
        return last_mod_date;
    }

    public void setLast_mod_date(LocalDate last_mod_date) {
        this.last_mod_date = last_mod_date;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.witc.recgen.business;

import java.time.LocalDate;

/**
 *
 * @author gerbe
 */
public class Category {
    private int id;
    private String category_name;
    private LocalDate last_mod_date;
    private int active;

    public Category() {
        id=0;
        category_name = "";
        last_mod_date=null;
        active=0;
    }

    public Category(int id, String category_name, LocalDate last_mod_date, int active) {
        this.id = id;
        this.category_name = category_name;
        this.last_mod_date = last_mod_date;
        this.active = active;
    }

    public Category(String category_name, LocalDate last_mod_date, int active) {
        this.category_name = category_name;
        this.last_mod_date = last_mod_date;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
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

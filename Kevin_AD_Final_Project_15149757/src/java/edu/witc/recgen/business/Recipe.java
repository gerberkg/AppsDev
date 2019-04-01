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
 * @author gerbe
 */
public class Recipe implements Serializable{
    private int id;
    private String recipe_name;
    private int category_id;
    private LocalDate last_mod_date;
    private int active;
    private String serving_size;

    public Recipe() {
        id = 0;
        recipe_name = "";
        category_id = 0;
        last_mod_date = null;
        active = 0;
        serving_size = "";
    }

    public Recipe(String recipe_name, int category_id, LocalDate last_mod_date, int active, String serving_size) {
        this.recipe_name = recipe_name;
        this.category_id = category_id;
        this.last_mod_date = last_mod_date;
        this.active = active;
        this.serving_size = serving_size;
    }

    
    public Recipe(int id, String recipe_name, int category_id, LocalDate last_mod_date, int active, String serving_size) {
        this.id = id;
        this.recipe_name = recipe_name;
        this.category_id = category_id;
        this.last_mod_date = last_mod_date;
        this.active = active;
        this.serving_size = serving_size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecipe_name() {
        return recipe_name;
    }

    public void setRecipe_name(String recipe_name) {
        this.recipe_name = recipe_name;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
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

    public String getServing_size() {
        return serving_size;
    }

    public void setServing_size(String serving_size) {
        this.serving_size = serving_size;
    }
    
    
}

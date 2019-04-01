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
public class Favorite implements Serializable {
    private int id;
    private int user_id;
    private int recipe_id;
    private int active;
    private LocalDate last_mod_date;
    
    public Favorite () {
        id = 0;
        user_id = 0;
        recipe_id = 0;
        active = 0;
        last_mod_date = null;        
    }
    
    public Favorite (int id, int user_id, int recipe_id, int active, LocalDate last_mod_date){
        this.id = id;
        this.user_id = user_id;
        this.recipe_id = recipe_id;
        this.active = active;
        this.last_mod_date = last_mod_date;
    }
    
    public Favorite (int user_id, int recipe_id, int active){
        this.user_id = user_id;
        this.recipe_id = recipe_id;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
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

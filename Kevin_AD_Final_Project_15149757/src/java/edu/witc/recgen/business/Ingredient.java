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
public class Ingredient {
    private int id;
    private String ingredient_name;
    private LocalDate last_mod_date;
    private int active;

    public Ingredient() {
        id=0;
        ingredient_name = "";
        last_mod_date = null;
        active = 0;
    }

    public Ingredient(int id, String ingredient_name, LocalDate last_mod_date, int active) {
        this.id = id;
        this.ingredient_name = ingredient_name;
        this.last_mod_date = last_mod_date;
        this.active = active;
    }

    public Ingredient(String ingredient_name, LocalDate last_mod_date, int active) {
        this.ingredient_name = ingredient_name;
        this.last_mod_date = last_mod_date;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIngredient_name() {
        return ingredient_name;
    }

    public void setIngredient_name(String ingredient_name) {
        this.ingredient_name = ingredient_name;
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

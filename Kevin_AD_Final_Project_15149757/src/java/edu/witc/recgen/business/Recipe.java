/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.witc.recgen.business;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author gerbe
 */
public class Recipe implements Serializable{
    private int id;
    private String recipe_name;
    private LocalDate last_mod_date;
    private int active;
    private String serving_size;
    private List<Category> categories;
    private List<Ingredient> ingredients;
    private String instructions;

    public Recipe() {
        id = 0;
        recipe_name = "";
        last_mod_date = null;
        active = 0;
        serving_size = "";
        instructions = "";
    }

    public Recipe(String recipe_name, LocalDate last_mod_date, int active, 
            String serving_size, String instructions) {
        this.recipe_name = recipe_name;
        this.last_mod_date = last_mod_date;
        this.active = active;
        this.serving_size = serving_size;
        this.instructions = instructions;
    }
    
    public Recipe(int id, String recipe_name, LocalDate last_mod_date, 
            int active, String serving_size, String instructions) {
        this.id = id;
        this.recipe_name = recipe_name;
        this.last_mod_date = last_mod_date;
        this.active = active;
        this.serving_size = serving_size;
        this.instructions = instructions;
    }

    public Recipe(int id, String recipe_name) {
        this.id = id;
        this.recipe_name = recipe_name;
    }

    public Recipe(int id, String recipe_name, int active, String serving_size, 
            String instructions) {
        this.id = id;
        this.recipe_name = recipe_name;
        this.active = active;
        this.serving_size = serving_size;
        this.instructions = instructions;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
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

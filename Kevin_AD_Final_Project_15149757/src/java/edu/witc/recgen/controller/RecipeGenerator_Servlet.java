/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.witc.recgen.controller;

import edu.witc.recgen.business.Category;
import edu.witc.recgen.business.Ingredient;
import edu.witc.recgen.business.Recipe;
import edu.witc.recgen.business.User;
import edu.witc.recgen.db.CategoryDb;
import edu.witc.recgen.db.IngredientDb;
import edu.witc.recgen.db.UserDb;
import edu.witc.recgen.utility.ValidatorUtil;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static java.util.Objects.isNull;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author 15149757
 */
public class RecipeGenerator_Servlet extends HttpServlet {
    
//    private void goToURL(HttpServletRequest request, HttpServletResponse response, String url)
//            throws ServletException, IOException {
//        
//    //sendRedirect does NOT resubmit on a post BUT needed data MUST be in a session object
//    //sendRedirect's url cannot start with a slash.
//        if("POST".equalsIgnoreCase(request.getMethod())){
//            if(url.startsWith("/")) {
//                url = url.substring(1);
//            }
//            response.sendRedirect(url);
//        }
//        else{
//             getServletContext().getRequestDispatcher(url)
//                    .forward(request, response);
//        }
//    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ServletHelper.getActionAndParms(request, response);
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        String message = "";
        String url = "";
        session.removeAttribute("message");

        switch (action) {
            case "home":  // The Home link in the main menu
                ServletHelper.doHome(request, response);
                break;
            case "register": // The User Registration link in the main menu
                ServletHelper.clearSession(request, "user");
                url = "/user_registration.jsp";
                ServletHelper.goToURL(url, request, response);
                break;
            case "login": // The Login link in the main menu
                url = "/login.jsp";
                ServletHelper.goToURL(url, request, response);
                break;
            case "logOut":  // The Logout link in the main menu
                session.invalidate();
                ServletHelper.doHome(request, response);
                break;
            case "searchRecipe":  // The Search Recipes link in the main menu
                url = "/recipe_search.jsp";
                ServletHelper.goToURL(url, request, response);
                break;
            case "newCategory": // The New Category link in the main menu
                session.removeAttribute("category");
                url = "/category_detail.jsp";
                ServletHelper.goToURL(url, request, response);
                break;
            case "newIngredient":  // The New Ingredient link in the main menu
                session.removeAttribute("ingredient");
                session.setAttribute("isNewIngr", true);
                url = "/ingredient_detail.jsp";
                ServletHelper.goToURL(url, request, response);
                break;
            case "newRecipe":  //The New Recipe link in the main menu
                session.removeAttribute("recipe");
                session.removeAttribute("categories");
                session.removeAttribute("ingredients");
                ServletHelper.getAllIngredients(request, response);
                url = "/recipe_edit.jsp";
                ServletHelper.goToURL(url, request, response);
                break;

            case "editRecIngr":
                // get the ingredient from the database using the recipeId and ingredientID from the lookup table
                int ingredientId = Integer.parseInt(request.getAttribute("ingredientId").toString());
                int recipeId = Integer.parseInt(request.getAttribute("recipeId").toString());
                session.setAttribute("isOnRecipe", true);

                Ingredient ingredient = IngredientDb.getIngredientByIdAndRecId(ingredientId, recipeId);
                session.setAttribute("ingredient", ingredient);
                url = "/ingredient_detail.jsp";
                ServletHelper.goToURL(url, request, response);
            break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // get the action being performed
        String action = request.getParameter("action");
        String ingredientName = "";
        String ingredientAmount = "";
        HttpSession session = request.getSession();
        String message = "";
        String url = "";
        Ingredient ingredient = null;
        int ingredientId = 0;
        int recipeId = 0;
        session.removeAttribute("message");
        
        ServletHelper.getActionAndParms(request, response);
        
        switch (action) {
            case "home": // Home button click
                ServletHelper.doHome(request, response);
            break;
            case "registerUser": // Save button click on registration form
                doUserRegistration(request, response);
            break;
            case "processLogin": // Login button on the login form
                doUserLogin(request, response);
            break;
            case "search":  // Search button on the recipe search form
                doSearchType(request, response);
            break;
            case "selectRecipe": // select button on the recipe list form
                ServletHelper.getRecipeInfo(request, response);
                User user  = (User) session.getAttribute("user");
                if (user.getIsAdmin() == 1) {
                    url = "/recipe_edit.jsp";
                } else {
                    url = "/recipe_detail.jsp";
                }
                ServletHelper.goToURL(url, request, response);
            break;
            case "editRecIngr":
                // get the ingredient from the database using the recipeId and ingredientID from the lookup table
                ingredientId = Integer.parseInt(request.getAttribute("ingredientId").toString());
                recipeId = Integer.parseInt(request.getAttribute("recipeId").toString());

                ingredient = IngredientDb.getIngredientByIdAndRecId(ingredientId, recipeId);
                session.setAttribute("ingredient", ingredient);
                url = "/ingredient_detail.jsp";
                ServletHelper.goToURL(url, request, response);
            break;
            case "updateRecIngr":
                ingredientId = Integer.parseInt(request.getAttribute("ingredientId").toString());
                recipeId = Integer.parseInt(request.getAttribute("recipeId").toString());
                ingredient = IngredientDb.getIngredientById(ingredientId);
                // get the ingredient name and amount from the detail form
                ingredientName = request.getParameter("ingredient_name");
                ingredientAmount = request.getParameter("ingredient_amount");
                // update the ingredient name in the ingredient table
                ingredient.setIngredient_name(ingredientName);
                ingredient.setIngredient_amount(ingredientAmount);
                ingredient.setId(ingredientId);
                ingredient.setRecipeId(recipeId);
                // update the ingredient in the ingredient table
                int rowsEffected = ServletHelper.updateIngredient(ingredient);
                if(rowsEffected == 0){
                    message = "Ingredient update failed.  Please contact the administrator.";
                    request.setAttribute("message", message);
                    url = "/ingredient_detail.jsp";
                    ServletHelper.goToURL(url, request, response);
                } else {
                    // update the ingredient amount in the recipe_ingredient table
                    rowsEffected = ServletHelper.updateIngredientAmount(ingredient);
                    if(rowsEffected == 0){
                        message = "Ingredient amount update failed.  Please contact the administrator.";
                        request.setAttribute("message", message);
                        url = "/ingredient_detail.jsp";
                        ServletHelper.goToURL(url, request, response);
                    } else {
                        message = "Ingredient updated successfully.";
                        request.setAttribute("message", message);
                        ServletHelper.getRecipeInfo(request, response);
                        url = "/recipe_edit.jsp";
                        ServletHelper.goToURL(url, request, response);
                    }
                }                
            break;
            case "newIngr": 
                doIngredient(request, response);
            break;
            case "selectIngredients":  // select button on the ingredient list form
                // get the array of selected recipeIngredients
                String[] selectedIngredients = request.getParameterValues("checkIngredient");
                                
                session.setAttribute("selectedIngredients", selectedIngredients);
                String requestingPage = session.getAttribute("callingPage").toString();
                // System.out.println("Request URI: " + requestingPage);

                if (requestingPage.equalsIgnoreCase("/list_ingredients.jsp")) {
                    ServletHelper.getRecipesByIngredient(request, response);
                    url = "/list_recipes.jsp";
                    ServletHelper.goToURL(url, request, response);
                } else if (requestingPage.equalsIgnoreCase("/recipe_edit.jsp")) {
                    Recipe recipe = new Recipe();
                    String recipeName = request.getParameter("recipe_name");
                    request.setAttribute("recipeName", recipeName);
                    String servingSize = request.getParameter("serving_size");
                    request.setAttribute("servingSize", servingSize);
                    recipe.setRecipe_name(recipeName);
                    recipe.setServing_size(servingSize);
                    
                    List<Ingredient> ingredients = new ArrayList<>();
                    // create a list of ingredient objects for each id in the selectedIngredients Array
                    for(String ingredientIdStr : selectedIngredients){
                        ingredient = IngredientDb.getIngredientById(Integer.parseInt(ingredientIdStr));
                        ingredients.add(ingredient);
                    }
                    recipe.setIngredients(ingredients);
                    session.setAttribute("ingredients", ingredients);
                    url = "/recipe_edit.jsp";
                    ServletHelper.goToURL(url, request, response);
                }
            break;
            case "selectCategories":  // select button on the category list form
                String[] selectedCategories = request.getParameterValues("checkCategory");

                url = "/list_recipes.jsp";
                ServletHelper.goToURL(url, request, response);
            break;
            case "updateCategory":  // save button on the category detail form
                doCategory(request, response);
            break;
            case "updateRecipe":  // save button on the recipe detail form
                doRecipe(request, response);
            break;
            case "updateIngredient":  // save button on the ingredient detail form
                doIngredient(request, response);
            break;
        }
    }

    // perform the appropriate action on the ingredient (insert or update)
    private void doIngredient(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // get the session and initialize variables
        HttpSession session = request.getSession();
        String message = "";
        String url = "";
        int ingredientId = 0;

        // get the ingredient name from the detail form
        String ingredientName = request.getParameter("ingredient_name");
        // check if the ingredient name field has text entered into it
        if (!ValidatorUtil.hasText(ingredientName)) {
            message = "Ingredient Name is required.";
            request.setAttribute("message", message);
            url = "/ingredient_detail.jsp";
            ServletHelper.goToURL(url, request, response);
        }
        // set the ingredient name into a request attribute
        request.setAttribute("ingredientName", ingredientName);
        
        // get the ingredient amount from the detail form
        String ingredientAmount = request.getParameter("ingredientAmount");
        //set the ingredient amount into a request attribute
        request.setAttribute("ingredientAmount", ingredientAmount);

        // get the ingredient id being passed as a hidden attribute from the detail form
        String ingredientIdStr = request.getParameter("ingredientId");
        // Check to see if the id is null, if not parse the string into an integer value
        if (!isNull(ingredientIdStr) && ingredientIdStr != "") {
            ingredientId = Integer.parseInt(ingredientIdStr);
        }

        // get the activeStr value passed from the detail form
        String activeStr = request.getParameter("status");
        // parse the string to an integer value
        int active = Integer.parseInt(activeStr);
        // set the activeStr value into a request attribute
        request.setAttribute("active", active);

        if (ingredientId > 0) { // this is an existing ingredient so do an update.
            // get the ingredient object from the session
            Ingredient ingredient = (Ingredient) session.getAttribute("ingredient");

            // set the values to be updated
            ingredient.setIngredient_name(ingredientName);
            ingredient.setActive(active);
            ingredient.setIngredient_amount(ingredientAmount);
            // call the update method to update the ingredient record in the Db
            ingredientId = ServletHelper.updateIngredient(ingredient);
            if (ingredientId == 0) {
                message = "Ingredient update failed, please try again.";
                request.setAttribute("message", message);
                url = "/ingredient_detail.jsp";
            } else {
                message = "Ingredient update successfull.";
                request.setAttribute("message", message);
                ServletHelper.getAllIngredients(request, response);
                session.removeAttribute("ingredient");
                url = "/list_ingredients.jsp";
            }
            ServletHelper.goToURL(url, request, response);
        } else {  // this is a new ingredient, so do an insert
            // create a new instance of the Ingredient object
            Ingredient ingredient = new Ingredient();
            // set the values to be inserted
            ingredient.setIngredient_name(ingredientName);
            ingredient.setActive(active);
            // call the insert ingredient method
            ingredientId = ServletHelper.insertIngredient(ingredient);
            if (ingredientId == 0) {
                message = "Ingredient creation failed, please try again.";
                request.setAttribute("message", message);
                url = "/ingredient_detail.jsp";
            } else {
                session.setAttribute("ingredient", ingredient);
                message = "Ingredient added successfully.";
                session.setAttribute("message", message);
                ServletHelper.getAllIngredients(request, response);
                session.removeAttribute("ingredient");
                url = "/list_ingredients.jsp";
            }
            ServletHelper.goToURL(url, request, response);
        }
    }

    // perform the appropriate category action (insert, update)
    private void doCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // get the session and initalize variables
        HttpSession session = request.getSession();
        String message = "";
        String url = "";
        int categoryId = 0;

        // get the category name from the detail form
        String categoryName = request.getParameter("category_name");
        // set the category name into a request attribute
        request.setAttribute("categoryName", categoryName);
        // verify the category name field has text
        if (!ValidatorUtil.hasText(categoryName)) {
            message = "Category Name is required.";
            request.setAttribute("message", message);
            url = "/category_detail.jsp";
            ServletHelper.goToURL(url, request, response);
        }

        // get the category id from the hidden field on the form
        String categoryIdStr = request.getParameter("categoryId");
        // parse the string value into an integer if it is not null
        if (!isNull(categoryIdStr) && categoryIdStr != "") {
            categoryId = Integer.parseInt(categoryIdStr);
        }

        // get the Active flag from the detail form
        String activeStr = request.getParameter("status");
        // parse the string to an integer value 
        int active = Integer.parseInt(activeStr);
        // set the value into a request attribute
        request.setAttribute("active", active);

        if (categoryId > 0) { // this is an existing ingredient so do an update.
            // instantiate the category object from the session attribute
            Category category = (Category) session.getAttribute("category");
            // set the values to be updated
            category.setCategory_name(categoryName);
            category.setActive(active);
            // call the update category method
            categoryId = ServletHelper.updateCategory(category);
            if (categoryId == 0) {
                message = "Category update failed, please try again.";
                request.setAttribute("message", message);
                url = "/category_detail.jsp";
            } else {
                message = "Category update successfull.";
                request.setAttribute("message", message);
                ServletHelper.getAllCategories(request, response);
                session.removeAttribute("category");
                url = "/list_categories.jsp";
            }
            ServletHelper.goToURL(url, request, response);
        } else {  // this is a new ingredient, so do an insert
            // instantiat a new category object
            Category category = new Category();
            // populate the values to be inserted
            category.setCategory_name(categoryName);
            category.setActive(active);
            // call the category insert method
            categoryId = ServletHelper.insertCategory(category);
            if (categoryId == 0) {
                message = "Category creation failed, please try again.";
                request.setAttribute("message", message);
                url = "/category_detail.jsp";
            } else {
                session.setAttribute("category", category);
                message = "Category added successfully.";
                request.setAttribute("message", message);
                ServletHelper.getAllCategories(request, response);
                session.removeAttribute("category");
                url = "/list_categories.jsp";
            }
            ServletHelper.goToURL(url, request, response);
        }
    }

    // perform the appropriate recipe action (insert, update)
    private void doRecipe(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // get the session and initialize variables
        HttpSession session = request.getSession();
        String message = "";
        String url = "";
        int recipeId = 0;
        int rowsAffected = 0;
        int active = 0;

        // get the information from the detail form and set into request attributes
        String recipeName = request.getParameter("recipe_name");
        request.setAttribute("recipeName", recipeName);
        String servingSize = request.getParameter("serving_size");
        request.setAttribute("servingSize", servingSize);
        String instructions = request.getParameter("instructions");
        request.setAttribute("instructions", instructions);
        String activeStr = request.getParameter("status");
        request.setAttribute("status", activeStr);

        String recipeIdStr = request.getParameter("recipeId");

        // prevent numberFormatException error if recipeIdStr is empty
        if (!isNull(recipeIdStr) && recipeIdStr != "") {
            recipeId = Integer.parseInt(recipeIdStr);
        }
        
        if (!isNull(activeStr)){
            active = Integer.parseInt(activeStr);
        }

        // validate the form has been populated correctly before doing anything
        if (validateRecipeForm(request, response)) { 
                // get the array of selected recipeIngredients
                String[] selectedIngredients = request.getParameterValues("checkIngredient");
                if(selectedIngredients.length > 0){
                    session.setAttribute("selectedIngredients", selectedIngredients);
                    List<Ingredient> recipeIngredients = new ArrayList<>();
                    // create a list of ingredient objects for each id in the selectedIngredients Array
                    for(String ingredientIdStr : selectedIngredients){
                        Ingredient ingredient = IngredientDb.getIngredientById(Integer.parseInt(ingredientIdStr));
                        recipeIngredients.add(ingredient);
                    }
                    // set the list of ingredient objects into the sesssion attribute
                    session.setAttribute("recipeIngredients", recipeIngredients);
                }

            if (recipeId > 0) {  // this is an existing recipe, do an update
                
                // instantiate the recipe object from the session attribute
                Recipe recipe = (Recipe) session.getAttribute("recipe");
                // set the values to be udated
                recipe.setRecipe_name(recipeName);
                recipe.setServing_size(servingSize);
                recipe.setInstructions(instructions.trim());
                recipe.setActive(active);
                // call the recipe update method
                rowsAffected = ServletHelper.updateRecipe(recipe);
                if (rowsAffected == 0) {
                    message = "Recipe update failed, please try again.";
                    request.setAttribute("message", message);
                    url = "/recipe_edit.jsp";
                } else {
                    for(String ingredientIdStr : selectedIngredients){
                        int ingredientId = Integer.parseInt(ingredientIdStr);
                        int recIngId = ServletHelper.insertRecipeIngredient(ingredientId, recipeId);
                        if(recIngId == 0){
                            message = "Recipe-Ingredient insert failed.";
                            request.setAttribute("message", message);
                            url = "/recipe_edit.jsp";
                        }
                    }

                    session.setAttribute("recipe", recipe);
                    message = "Recipe updated successfully.";
                    request.setAttribute("message", message);
                    ServletHelper.getAllRecipes(request, response);
                    url = "/list_recipes.jsp";
                }
                ServletHelper.goToURL(url, request, response);
            } else {  // this is a new recipe, so do an insert
                // clear any existing cateory and ingredient list from the session
                session.removeAttribute("categories");
                // session.removeAttribute("recipeIngredients");

                // instantiate a new recipe object
                Recipe recipe = new Recipe();
                // set the values to be inserted
                recipe.setRecipe_name(recipeName);
                recipe.setServing_size(servingSize);
                recipe.setInstructions(instructions.trim());
                recipe.setActive(active);

                // call the recipe insert method
                recipeId = ServletHelper.insertRecipe(recipe);
                session.setAttribute("recipeId", recipeId);
                if (recipeId == 0) {
                    message = "Recipe creation failed, please try again.";
                    request.setAttribute("message", message);
                    url = "/recipe_edit.jsp";
                } else {
                    for(String ingredientIdStr : selectedIngredients){
                        int ingredientId = Integer.parseInt(ingredientIdStr);
                        int recIngId = ServletHelper.insertRecipeIngredient(ingredientId, recipeId);
                        if(recIngId == 0){
                            message = "Recipe-Ingredient insert failed.";
                            request.setAttribute("message", message);
                            url = "/recipe_edit.jsp";
                        }
                    }
                    session.setAttribute("recipe", recipe);
                    message = "Recipe added successfully.";
                    request.setAttribute("message", message);
                    ServletHelper.getAllRecipes(request, response);
                    session.removeAttribute("recipe");
                    url = "/list_recipes.jsp";
                }
                ServletHelper.goToURL(url, request, response);
            }
        } else {
            message = request.getAttribute("message").toString();
            url = "/recipe_edit.jsp";
            ServletHelper.goToURL(url, request, response);
        }
    }

    // perform the selected search
    public void doSearchType(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // get the session and initialize variables
        HttpSession session = request.getSession();
        String message;
        String url = "";
        Recipe customer = new Recipe();

        // Get the type of search to perform and set the request attribute
        String searchType = request.getParameter("search_type");
        request.setAttribute("searchType", searchType);

        if (searchType.equalsIgnoreCase("ingredient")) { // Search by recipeIngredients
//            List<Ingredient> recipeIngredients = new ArrayList();
//            recipeIngredients = IngredientDb.getAllIngredients();
//            session.setAttribute("recipeIngredients", recipeIngredients);
            ServletHelper.getAllIngredients(request, response);
            url = "/list_ingredients.jsp";
            session.setAttribute("callingPage", url);
        } else if (searchType.equalsIgnoreCase("category")) { // Search by ingredient
            List<Category> categories = new ArrayList<>();
            categories = CategoryDb.getAllCategories();
            session.setAttribute("categories", categories);
            url = "/list_categories.jsp";
            session.setAttribute("callingPage", url);
        } else if (searchType.equalsIgnoreCase("name")) { // Search by recipe name
            ServletHelper.getAllRecipes(request, response);
            url = "/list_recipes.jsp"; 
        }
        // Load the page indicated by the 'url' variable
        ServletHelper.goToURL(url, request, response);
    }

    // attempt to login the user
    public void doUserLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // get the session and intialize variables
        HttpSession session = request.getSession();
        String message = "";
        String url = "";
        
        // get the user name and password and set the request attributes
        String userName = request.getParameter("username");
        request.setAttribute("username", userName);
        String password = request.getParameter("password");
        request.setAttribute("password", password);

        // verify user name and password are populated.
        if (!ValidatorUtil.hasText(userName) || !ValidatorUtil.hasText(password)) {  // username or password is empty
            message = "Username and Password are required!";
            session.setAttribute("message", message);
            url = "/login.jsp";
        } else {
            try {
                User user = UserDb.validateLogin(userName, password);
                if (!isNull(user)) {
                    session.setAttribute("isLoggedIn", true);
                    session.setAttribute("user", user);
                    session.setAttribute("isAdmin", user.getIsAdmin());
                    message = "Login was successfull";
                    session.setAttribute("message", message);
                    url = "/recipe_search.jsp";
                } else { // invalid login credentials
                    message = "Username or Password is invalid.  Please try again.";
                    session.setAttribute("message", message);
                    request.removeAttribute(userName);
                    request.removeAttribute(password);
                    url = "/login.jsp";
                }
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
        message = session.getAttribute("message").toString();
        ServletHelper.goToURL(url, request, response);
    }

    // register a new user
    public void doUserRegistration(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // get the session and initialize variables
        HttpSession session = request.getSession();
        String message = "";
        String url = "";
        int userId = 0;
        int isAdmin = 0;

        // get the values from the user registration form and set the request attributes
        String firstName = request.getParameter("first_name");
        request.setAttribute("firstName", firstName);
        String lastName = request.getParameter("last_name");
        request.setAttribute("lastName", lastName);
        String email = request.getParameter("email");
        request.setAttribute("email", email);
        String userID = request.getParameter("userID");
        request.setAttribute("userID", userID);
        String password = request.getParameter("password");
        request.setAttribute("password", password);
        String verifyPassword = request.getParameter("verifyPassword");
        request.setAttribute("verifyPassword", verifyPassword);
        String isAdminStr = request.getParameter("isAdmin");
        if (!isNull(isAdminStr)) {
            isAdmin = Integer.parseInt(isAdminStr);
            request.setAttribute("isAdmin", isAdmin);
        }

        if (validateUserForm(request, response)) {  // form is valid
            // instantiate a new user object
            User user = new User();
            // set the values to be inserted
            user.setFirst_name(firstName);
            user.setLast_name(lastName);
            user.setEmail_address(email);
            user.setUser_id(userID);
            user.setPassword(password);
            user.setActive(1);
            user.setIsAdmin(0);
            user.setLast_mod_date(LocalDate.MAX);
            // call the insert user method
            userId = ServletHelper.insertUser(user);
            if (userId == 0) {
                message = "User creation failed, please try again.";
                request.setAttribute("message", message);
                url = "/user_registration.jsp";
            } else {
                session.setAttribute("user", user);
                message = "User added successfully.";
                request.setAttribute("message", message);

                url = "/recipe_search.jsp";
            }
        } else {
            message = request.getAttribute("message").toString();
            url = "/user_registration.jsp";
        }
        ServletHelper.goToURL(url, request, response);
    }

    // validate the form has information
    public boolean validateRecipeForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String message = "";
        String url = "";
        boolean isValid = true;

        String recipeName = request.getAttribute("recipeName").toString();
        String servingSize = request.getAttribute("servingSize").toString();

        if (!ValidatorUtil.hasText(recipeName) || !ValidatorUtil.hasText(servingSize)) {
            message = "Recipe Name and Serving Size are required";
            request.setAttribute("message", message);
            isValid = false;
        }
        return isValid;
    }

    // validate the register user form has required information
    public boolean validateUserForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String message = "";
        String url = "";
        boolean isValid = true;

        String firstName = request.getAttribute("firstName").toString();
        String lastName = request.getAttribute("lastName").toString();
        String email = request.getAttribute("email").toString();
        String userID = request.getAttribute("userID").toString();
        String password = request.getAttribute("password").toString();
        String verifyPassword = request.getAttribute("verifyPassword").toString();

        if (!ValidatorUtil.hasText(firstName) || !ValidatorUtil.hasText(lastName)) {
            message = "First Name and Last Name are required";
            request.setAttribute("message", message);
            isValid = false;
        } else if (ValidatorUtil.hasText(email) && !ValidatorUtil.isValidEmail(email)) {
            message = "Please enter a valid email address.";
            request.setAttribute("message", message);
            isValid = false;
        } else if (!ValidatorUtil.hasText(userID)) {
            message = "User ID is required";
            request.setAttribute("message", message);
            isValid = false;
        } else if (!ValidatorUtil.hasText(password) || !ValidatorUtil.hasText(verifyPassword)) {
            message = "Password and Verify Password are required";
            request.setAttribute("message", message);
            isValid = false;
        } else if (!password.equals(verifyPassword)) {
            message = "Passwords do not match, please try again.";
            request.setAttribute("message", message);
            isValid = false;
        }
        return isValid;
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

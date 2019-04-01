
package edu.witc.recgen.controller;

import edu.witc.recgen.business.Category;
import edu.witc.recgen.business.Ingredient;
import edu.witc.recgen.business.Recipe;
import edu.witc.recgen.business.User;
import edu.witc.recgen.db.CategoryDb;
import edu.witc.recgen.db.IngredientDb;
import edu.witc.recgen.db.RecipeDb;
import edu.witc.recgen.db.UserDb;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class ServletHelper extends HttpServlet {

    // insert a new user record
    public static int insertUser(User user){
        int newlyInsertedRowId = 0;
        try{
            newlyInsertedRowId = UserDb.insert(user);
        } catch (SQLException ex){
            System.out.println(ex.toString());
        }
        return newlyInsertedRowId;
    }

////////////////// Recipe related methods. //////////////////    
    // insert a new recipe
    public static int insertRecipe(Recipe recipe){
        int newlyInsertedRowId = 0;
        try{
            newlyInsertedRowId = RecipeDb.insert(recipe);
        } catch (SQLException ex){
            System.out.println(ex.toString());
        }
        return newlyInsertedRowId;
    }

    // update an existing recipe
    public static int updateRecipe(Recipe recipe){
        int updatedRowId = 0;
        try{
            updatedRowId = RecipeDb.update(recipe);
        } catch (SQLException ex){
            System.out.println(ex.toString());
        }
        return updatedRowId;
    }
    
    // get a list of all the recipes
    public static List<Recipe> getAllRecipes(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        List<Recipe> recipes = new ArrayList();
        recipes = RecipeDb.getAllRecipes();
        session.setAttribute("recipes", recipes);
        return recipes;
    }

    // get a list of recipes by selected recipeIngredients
    public static List<Recipe> getRecipesByIngredient(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        List<Recipe> recipes = new ArrayList();
        recipes = RecipeDb.getRecipesByIngredients(request, response);
        session.setAttribute("recipes", recipes);
        return recipes;
    }

    // get a list of recipes by selected categories
    public static List<Recipe> getRecipesByCategory(HttpServletRequest request, HttpServletResponse response){
        String inClause = request.getAttribute("inClause").toString();
        HttpSession session = request.getSession();
        List<Recipe> recipes = new ArrayList();
        recipes = RecipeDb.getRecipesByCategories(inClause);
        session.setAttribute("recipes", recipes);
        return recipes;
    }
    
    // get recipe info.
    public static void getRecipeInfo(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        int recipeId = Integer.parseInt(request.getAttribute("recipeId").toString());
        Recipe recipe = RecipeDb.getRecipeById(recipeId);
        session.setAttribute("recipe", recipe);
        List<Category> categories = new ArrayList<>();
        categories = CategoryDb.getCategoriesByRecipeId(recipeId);
        session.setAttribute("categories", categories);
        List<Ingredient> recipeIngredients = new ArrayList();
        recipeIngredients = IngredientDb.getIngredientsByRecipeId(recipeId);
        session.setAttribute("recipeIngredients", recipeIngredients);
        
        List<Ingredient> remainingIngredients = new ArrayList();
        remainingIngredients = IngredientDb.getRemainingIngredients(recipeId, request, response);
        session.setAttribute("allIngredients", remainingIngredients);
    }
    
/////////// Category related methods. ///////////////
    // insert a new category
    public static int insertCategory(Category category){
        int newlyInsertedRowId = 0;
        try{
            newlyInsertedRowId = CategoryDb.insert(category);
        } catch (SQLException ex){
            System.out.println(ex.toString());
        }
        return newlyInsertedRowId;
    }

    // update an existing category
    public static int updateCategory(Category category){
        int updatedRowId = 0;
        try{
            updatedRowId = CategoryDb.update(category);
        } catch (SQLException ex){
            System.out.println(ex.toString());
        }
        return updatedRowId;
    }

    // get a list of all the categories
    public static List<Category> getAllCategories(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        List<Category> categories = new ArrayList();
        categories = CategoryDb.getAllCategories();
        session.setAttribute("categories", categories);
        return categories;
    }

////////////////  Ingredient related methods. ////////////////////////////
    // insert a new ingredient
    public static int insertIngredient(Ingredient ingredient){
        int newlyInsertedRowId = 0;
        try{
            newlyInsertedRowId = IngredientDb.insert(ingredient);
        } catch (SQLException ex){
            System.out.println(ex.toString());
        }
        return newlyInsertedRowId;
    }

    // insert a new ingredient
    public static int insertRecipeIngredient(int ingredientId, int recipeId){
        int newlyInsertedRowId = 0;
        try{
            newlyInsertedRowId = IngredientDb.insertRecipeIngredient(ingredientId, recipeId);
        } catch (SQLException ex){
            System.out.println(ex.toString());
        }
        return newlyInsertedRowId;
    }

    // update an existing ingredient
    public static int updateIngredient(Ingredient ingredient){
        int updatedRowId = 0;
        try{
            updatedRowId = IngredientDb.update(ingredient);
        } catch (SQLException ex){
            System.out.println(ex.toString());
        }
        return updatedRowId;
    }
    
    // update an existing ingredient amount
    public static int updateIngredientAmount(Ingredient ingredient){
        int updatedRowId = 0;
        try{
            updatedRowId = IngredientDb.updateIngredientAmount(ingredient);
        } catch (SQLException ex){
            System.out.println(ex.toString());
        }
        return updatedRowId;
    }

    // get a list of all recipeIngredients
    public static void getAllIngredients(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        List<Ingredient> ingredients = new ArrayList();
        ingredients = IngredientDb.getAllIngredients();
        session.setAttribute("ingredients", ingredients);
    }

///////////////////// Miscelaneous methods. ////////////////////////////////////////

    // get the passed in action parameter as well as any ingredient, recipe, or category parameters
    public static void getActionAndParms(HttpServletRequest request, HttpServletResponse response) {
        
        // get the recipeId from the post
        String recipeIdStr = request.getParameter("recipeId");
        // set the recipeIdStr to null, if nothing is passed in, 
        // to avoid null pointer exceptions later in the code
        if (recipeIdStr == "") {
            recipeIdStr = null;
        }
        
        // get the categoryId from the post
        String categoryIdStr = request.getParameter("categoryId");
        // set the categoryIdStr to null, if nothing is passed in, 
        // to avoid null pointer exceptions later in the code
        if (categoryIdStr == "") {
            categoryIdStr = null;
        }
        
        // get the ingredientId from the post
        String ingredientIdStr = request.getParameter("ingredientId");
        // set the ingredientIdStr to null, if nothing is passed in, 
        // to avoid null pointer exceptions later in the code
        if (ingredientIdStr == "") {
            ingredientIdStr = null;
        }

        // get the session and initialize variables
        HttpSession session = request.getSession();
        String message = "";
        String url = "";
        String inClause = "";
        int i = 0;
        int categoryId = 0;
        int recipeId = 0;
        int ingredientId = 0;
        boolean isNewIngr = false;
        boolean isOnRecipe = false;
        
        Ingredient ingredient = null;
        session.setAttribute("ingredient", ingredient);
        Category category = null;
        session.setAttribute("category", category);
        Recipe recipe = null;
        session.setAttribute("recipe", recipe);

        // check if a recipeId is being passed in.  If so, set the recipe object into session
        if (!isNull(recipeIdStr)) {
            recipeId = Integer.parseInt(recipeIdStr);
            request.setAttribute("recipeId", recipeId);
            recipe = RecipeDb.getRecipeById(recipeId);
            session.setAttribute("recipe", recipe);
        }
        // check if a CategoryId is being passed in.  If so, set the category object into session
        if (!isNull(categoryIdStr)) {
            categoryId = Integer.parseInt(categoryIdStr);
            request.setAttribute("categoryId", categoryId);
            category = CategoryDb.getCategoryById(categoryId);
            session.setAttribute("category", category);
        }
        // check if an ingredientId is being passed in.  If so, set the ingredient object into session
        if (!isNull(ingredientIdStr)) {
            ingredientId = Integer.parseInt(ingredientIdStr);
            request.setAttribute("ingredientId", ingredientId);
            ingredient = IngredientDb.getIngredientById(ingredientId);
            session.setAttribute("ingredient", ingredient);
        }
    }

    // go to the error page is there is a page not found
    public void doError(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "/error_404.jsp";
        goToURL(url, request, response);
    }

    // go to the proper home page
    public static void doHome(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "";
        HttpSession session = request.getSession();
        
        if(session.getAttribute("isLoggedIn") == null) { // if user is not logged in, go to the index page
            url = "/index.jsp";           
        } else if ((session.getAttribute("isLoggedIn").equals(true))){ // else go to the recipe search page.
            url = "/recipe_search.jsp";           
        }
        goToURL(url, request, response);
    }
    
    // register a new user
    public static void doRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "/user_registration.jsp";
        goToURL(url, request, response);
    }
    

    // go to the specified url
    public static void goToURL(String url, HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
    //sendRedirect does NOT resubmit on a post BUT needed data MUST be in a session object
    //sendRedirect's url cannot start with a slash.
    HttpSession session = request.getSession();
        if("POST".equalsIgnoreCase(request.getMethod())){
            if(url.startsWith("/")){
                url = url.substring(1);
            }
            response.sendRedirect(url);
        }
        else{
             request.getServletContext().getRequestDispatcher(url)
                    .forward(request, response);
        }
            System.out.println("Returning from the sendRedirect.");
            //session.removeAttribute("message");
    }  
    
//    //clear the session
    public static void clearSession(HttpServletRequest request, String whichOne){
        HttpSession session = request.getSession(false);
        if (session != null){
            switch (whichOne){
                case "user":
                    session.removeAttribute("user");
                    session.removeAttribute("lastName");
                    session.removeAttribute("firstName");
                break;
            }
        }
    }
}

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
import edu.witc.recgen.utility.ValidatorUtil;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static java.util.Objects.isNull;

/**
 *
 * @author 15149757
 */
public class RecipeGenerator_Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { 
        
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        String message = "";
        String url = "";
        
        switch(action) {
            case "home":
                ServletHelper.doHome(request, response);
            break;
            case "register":
                ServletHelper.clearSession(request, "user");
                url = "/user_registration.jsp";
                ServletHelper.goToURL(url, request, response);
            break;
            case "login":
                url = "/login.jsp";
                ServletHelper.goToURL(url, request, response);
            break;
            case "logOut":
                session.invalidate();
                ServletHelper.doHome(request, response);
            break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        String message = "";
        String url = "";
       
        switch(action) {
            case "home":
                ServletHelper.doHome(request, response);
            break;
            case "registerUser":
                doUserRegistration(request, response);
            break;
            case "processLogin":
                doUserLogin(request, response);
            break;
            case "logOut":
                session.invalidate();
                ServletHelper.doHome(request, response);
            break;
            case "search":
                doSearchType(request, response);
        }
    }
    
    public void doSearchType(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String message;
        String url = "";
        Recipe customer = new Recipe();

        String searchType = request.getParameter("search_type");
        request.setAttribute("searchType", searchType);
        
        HttpSession session = request.getSession();

        if(searchType.equalsIgnoreCase("ingredient")){
            List<Ingredient> ingredients = new ArrayList();
            ingredients = IngredientDb.getAllIngredients();
            session.setAttribute("ingredients", ingredients);
            url = "/list_ingredients.jsp";
        } else if (searchType.equalsIgnoreCase("category")){
            List<Category> categories = new ArrayList<>();
            categories = CategoryDb.getAllCategories();
            session.setAttribute("categories", categories);
            url = "/list_categories.jsp";    
        }
        ServletHelper.goToURL(url, request, response);
//        else if (searchType.equalsIgnoreCase("nameCat")){
//            List<Customer> customers = new ArrayList<>();
//            customers = CustomerDb.getAllCustomers();
//            session.setAttribute("customers", customers);
//            url = "/list_customer.jsp";
//            ServletHelper.goToURL(url, request, response);
//        }
    }

    public void doUserLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = "";
        String url = "";
        boolean isValidLogin = false;
                
        HttpSession session = request.getSession();
        
        String userName = request.getParameter("username");
        request.setAttribute("username", userName);
        String password = request.getParameter("password");
        request.setAttribute("password", password);
        
        if(!ValidatorUtil.hasText(userName) || !ValidatorUtil.hasText(password)){
            message = "Username and Password are required!";
            request.setAttribute("message", message);
            url = "/login.jsp";
        } else if (isValidLogin = ServletHelper.validateUserLogin(userName, password)){
            session.setAttribute("isLoggedIn", true);
            message = "Login was successfull";
            request.setAttribute("message", message);
            url = "/recipe_search.jsp";
        } else {
            message = "Username or Password is invalid.  Please try again.";
            request.setAttribute("message", message);
            request.removeAttribute(userName);
            request.removeAttribute(password);
            url = "/login.jsp";
        } 
        message = request.getAttribute("message").toString();
        ServletHelper.goToURL(url, request, response);
    }

    public void doUserRegistration(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = "";
        String url = "";
        int userId = 0;
        int isAdmin = 0;
        HttpSession session = request.getSession();
        
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
        if(!isNull(isAdminStr)){
            isAdmin = Integer.parseInt(isAdminStr);
            request.setAttribute("isAdmin", isAdmin);
        }
        
        
        if (validateUser(request, response)){
            User user = new User();
            user.setFirst_name(firstName);
            user.setLast_name(lastName);
            user.setEmail_address(email);
            user.setUser_id(userID);
            user.setPassword(password);
            user.setActive(1);
            user.setIsAdmin(0);
            user.setLast_mod_date(LocalDate.MAX);
            userId = ServletHelper.insertUser(user);
            if (userId == 0){
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

        
    public boolean validateUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        
        String message = "";
        String url = "";
        boolean isValid = true;
        
        String firstName = request.getAttribute("firstName").toString();
        String lastName = request.getAttribute("lastName").toString();
        String email = request.getAttribute("email").toString();
        String userID = request.getAttribute("userID").toString();
        String password = request.getAttribute("password").toString();
        String verifyPassword = request.getAttribute("verifyPassword").toString();
        
        if (!ValidatorUtil.hasText(firstName) || !ValidatorUtil.hasText(lastName)){
            message = "First Name and Last Name are required";
            request.setAttribute("message", message);
            isValid = false;
        } else if (ValidatorUtil.hasText(email) && !ValidatorUtil.isValidEmail(email)){
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
        } else if (!password.equals(verifyPassword)){
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


package edu.witc.recgen.controller;

import edu.witc.recgen.business.User;
import edu.witc.recgen.db.UserDb;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static java.util.Objects.isNull;

/**
 *
 * @author 15149757
 */
public class ServletHelper extends HttpServlet {

    public static int insertUser(User user){
        int newlyInsertedRowId = 0;
        try{
            newlyInsertedRowId = UserDb.insert(user);
        } catch (SQLException ex){
            System.out.println(ex.toString());
        }
        return newlyInsertedRowId;
    }

    static boolean validateUserLogin(String userName, String password) {
        boolean isValidLogin = false;
        User user = null;
        try {
            user = UserDb.validateLogin(userName, password);
            if (!isNull(user)){
                isValidLogin = true;
            }  
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return isValidLogin;
    }

    public void doError(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "/error_404.jsp";
        goToURL(url, request, response);
    }

    public static void doHome(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "/index.jsp";
        goToURL(url, request, response);
    }
    public static void doRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "/user_registration.jsp";
        goToURL(url, request, response);
    }
    
    public static void goToURL(String url, HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        request.getServletContext()
            .getRequestDispatcher(url)
            .forward(request, response);
    }    
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

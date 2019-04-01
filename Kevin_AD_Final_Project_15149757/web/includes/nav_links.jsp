<%-- 
    Document   : nav_links
    Created on : February 7, 2019, 10:50:45 AM
    Author     : 15149757
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<input type="checkbox" id="nav-toggle" class="nav-toggle">
<nav>
    <ul>
        <li><a class="link" href="<c:url value='/recipeGenerator?action=home' />">Home</a></li>
            <c:choose>
                <c:when test="${isLoggedIn == true}">
                    <li><a class="link" href="<c:url value='/recipeGenerator?action=searchRecipe' />">Search Recipes</a></li>
                    <c:choose>
                        <c:when test="${isAdmin == 1}">
                        <li><a class="link" href="<c:url value='/recipeGenerator?action=newCategory' />">New Category</a></li>
                        <li><a class="link" href="<c:url value='/recipeGenerator?action=newIngredient' />">New Ingredient</a></li>
                        <li><a class="link" href="<c:url value='/recipeGenerator?action=newRecipe' />">New Recipe</a></li> 
                        </c:when>
                    </c:choose>
                    <li><a class="link" href="<c:url value='/recipeGenerator?action=logOut' />">Logout</a></li> 
                </c:when>
                <c:otherwise>
                <li><a class="link" href="<c:url value='/recipeGenerator?action=login' />">Login</a></li>
                <li><a class="link" href="<c:url value='/recipeGenerator?action=register' />">User Registration</a></li>
                </c:otherwise>
            </c:choose>            
    </ul>
</nav>
<label for="nav-toggle" class="nav-toggle-label">
    <span></span>
</label>
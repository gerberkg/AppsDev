<%-- 
    Document   : ingredient
    Created on : Feb 18, 2019, 9:34:20 AM
    Author     : 15149757
--%>

<title>AD Final Project Recipe Generator</title>

<%@page import="java.util.List" %>
<%@ include file="/includes/header.jsp" %>


<form action="recipeGenerator" method="post">
    <p class="error"><i><c:out value="${message}" default="" /></i></p>
    <label>Ingredient Name:</label>
    <c:choose>
        <c:when test="${ingredient_name != null}">
            <input type="text" name="ingredient_name" 
                   value="${ingredient_name}" autofocus="yes"/><br>
        </c:when>
        <c:otherwise>
            <input type="text" name="ingredient_name" 
                   value="${ingredient.ingredient_name}" autofocus="yes"/><br>
        </c:otherwise>
    </c:choose>

    <c:choose>
        <c:when test="${isOnRecipe == true}">
            <label>Ingredient Amount:</label>
            <c:choose>
                <c:when test="${ingredient_amount != null}">
                    <input type="text" name="ingredient_amount"
                           value="${ingredient_amount}" autofocus="yes"/><br>
                </c:when>
                <c:otherwise>
                    <input type="text" name="ingredient_amount"
                           value="${ingredient.ingredient_amount}"/><br>
                </c:otherwise>
            </c:choose>
        </c:when>
    </c:choose>

    <label>Status:</label>
    <select name="status">
        <option value="1" ${ingredient.active == "1" ? 'selected="selected"' : ''}>Active</option>
        <option value="0" ${ingredient.active == "0" ? 'selected="selected"' : ''}>Inactive</option>
    </select><br>
    
    <input type="hidden" name="ingredientId" value="${ingredient.id}">
    <input type="hidden" name="recipeId" value="${recipe.id}">
    <c:choose> 
        <c:when test="${isNewIngr == true}">
            <input type="hidden" name="action" value="newIngr">
        </c:when>
        <c:otherwise>
            <input type="hidden" name="action" value="updateRecIngr">
        </c:otherwise>
    </c:choose>
    <input class="submit_user" type="submit" value="Save"/><br>

</form>
<c:import url="/includes/footer.jsp"></c:import>

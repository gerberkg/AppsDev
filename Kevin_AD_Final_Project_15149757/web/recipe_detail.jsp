<%-- 
    Document   : index
    Created on : Feb 18, 2019, 9:34:20 AM
    Author     : 15149757
--%>

<title>AD Final Project Recipe Generator</title>

<%@ include file="/includes/header.jsp" %>

<form action="recipeGenerator" method="post">
    <main>

        <p class="error"><i><c:out value="${message}" default="" /></i></p>
        <label>Recipe Name:</label>
        <c:choose>
            <c:when test="${recipe_name != null}">
                <input type="text" name="recipe_name" disabled 
                       value="${recipe_name}" autofocus="yes"/><br>
            </c:when>
            <c:otherwise>
                <input type="text" name="recipe_name" disabled 
                       value="${recipe.recipe_name}" autofocus="yes"/><br>
            </c:otherwise>
        </c:choose>

        <label>Service Size:</label>
        <c:choose>
            <c:when test="${serving_size != null}">
                <input type="text" name="serving_size" disabled
                       value="${serving_size}" autofocus="yes"/><br>
            </c:when>
            <c:otherwise>
                <input type="text" name="serving_size" disabled
                       value="${recipe.serving_size}" autofocus="yes"/><br>
            </c:otherwise>
        </c:choose>
        <label>Ingredients:</label>
        <table border="0">
            <tr>
                <th colspan="2">Name</th>
                
                <th>Amount</th>
            </tr>
            <c:forEach var="ingredient" items="${ingredients}">
                <tr>
                    <td colspan="2"><c:out value="${ingredient.ingredient_name}"/></td>
                    <td><c:out value="${ingredient.ingredient_amount}"/></td>
                </tr>
            </c:forEach>
        </table>

        <label>Instructions:</label>
        <c:choose>
            <c:when test="${instructions != null}">
                <textarea cols="100" rows="10" maxlength="1000" disabled 
                          name="instructions" placeholder="Recipe details go here." autofocus="yes">${instructions}</textarea><br>
            </c:when>
            <c:otherwise>
                <textarea cols="100" rows="10" maxlength="1000" disabled 
                          name="instructions" placeholder="Recipe details go here." autofocus="yes">${recipe.instructions}</textarea><br>
            </c:otherwise>
        </c:choose>

        <label>Category List:</label>
        <table border="1">
            <tr>
                <th>Name</th>
            </tr>
            <c:forEach var="category" items="${categories}">
                <tr>
                    <td><c:out value="${category.category_name}"/></td>
                </tr>
            </c:forEach>
        </table>
    </main>
</form>
<c:import url="/includes/footer.jsp"></c:import>

<%-- 
    Document   : index
    Created on : Feb 18, 2019, 9:34:20 AM
    Author     : 15149757
--%>

<title>AD Final Project Recipe Generator</title>

<%@ include file="/includes/header.jsp" %>

<form name="mainForm" action="recipeGenerator" method="post">
    <main>

        <p class="error"><i><c:out value="${message}" default="" /></i></p>
        <label>Recipe Name:</label>
        <c:choose>
            <c:when test="${recipe_name != null}">
                <input type="text" name="recipe_name" 
                       value="${recipe_name}" autofocus="yes"/><br>
            </c:when>
            <c:otherwise>
                <input type="text" name="recipe_name" 
                       value="${recipe.recipe_name}" autofocus="yes"/><br>
            </c:otherwise>
        </c:choose>

        <label>Serving Size:</label>
        <c:choose>
            <c:when test="${serving_size != null}">
                <input type="text" name="serving_size" 
                       value="${serving_size}" autofocus="yes"/><br>
            </c:when>
            <c:otherwise>
                <input type="text" name="serving_size" 
                       value="${recipe.serving_size}" autofocus="yes"/><br>
            </c:otherwise>
        </c:choose>
        <table border="1">
        <label>Add Ingredients:</label>
        <tr>
            <th colspan="2">Name</th>                
            <th>Amount</th>
            <th>Id</th>
            <th>Select</th>
        </tr>
        <c:forEach var="ingredient" items="${allIngredients}">
            <tr>
                <td colspan="2"><c:out value="${ingredient.ingredient_name}"/></td>
                <td><c:out value="${ingredient.ingredient_amount}"/></td>
                <td><c:out value="${ingredient.id}"/></td>
                <td>
                    <input type="checkbox" name="checkIngredient" value="${ingredient.id}"/>
                </td>
            </tr>
        </c:forEach>
        </table>
        <br>
        <table border="1">    
        <label>Edit Ingredients:</label>
            <tr>
                <th colspan="2">Name</th>                
                <th>Amount</th>
                <th>Id</th>
                <th>Edit</th>
            </tr>
        <c:forEach var="ingredient" items="${recipeIngredients}">
            <tr>
                <td colspan="2"><c:out value="${ingredient.ingredient_name}"/></td>
                <td><c:out value="${ingredient.ingredient_amount}"/></td>
                <td><c:out value="${ingredient.id}"/></td>
                <td>
                    <a class="link" 
                        href="<c:url value='/recipeGenerator?action=editRecIngr&ingredientId=${ingredient.id}&recipeId=${recipe.id}' />">Edit</a>
                </td>
            </tr>
        </c:forEach>
        </table>

        <br>

        <label>Instructions:</label>
        <c:choose>
            <c:when test="${instructions != null}">
                <textarea cols="100" rows="10" maxlength="1000" 
                          name="instructions" placeholder="Recipe details go here." autofocus="yes">${instructions}</textarea><br>
            </c:when>
            <c:otherwise>
                <textarea cols="100" rows="10" maxlength="1000" 
                          name="instructions" placeholder="Recipe details go here." autofocus="yes">${recipe.instructions}</textarea><br>
            </c:otherwise>
        </c:choose>

        <label>Status:</label>
        <select name="status">
            <option value="1" ${recipe.active == "1" ? 'selected="selected"' : ''}>Active</option>
            <option value="0" ${recipe.active == "0" ? 'selected="selected"' : ''}>Inactive</option>
        </select><br>

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

        <input type="hidden" name="recipeId" value="${recipe.id}">
        <input type="hidden" name="action" value="updateRecipe">
        <input class="submit_user" type="submit" value="Save"/><br>
    </main>

</form>
<c:import url="/includes/footer.jsp"></c:import>

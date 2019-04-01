<%-- 
    Document   : list_customer
    Created on : Nov 29, 2018, 3:27:32 PM
    Author     : 15149757
--%>

<%@ include file="/includes/header.jsp" %>
<title>Ingredient List</title>
<header>
    <h1>Select Ingredients</h1>
</header>
<main>
    <section>
        <div class="column1">
            <table border="1">
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Status</th>
                    <th>Manage</th>
                </tr>

                <c:forEach var="ingredient" items="${ingredients}">
                <tr>
                    <td><c:out value="${ingredient.id}"/></td>
                    <td><c:out value="${ingredient.ingredient_name}"/></td>
                    <c:choose>
                        <c:when test="${ingredient.active == 1}">
                            <td><c:out value="Active"/></td>
                        </c:when>
                    <c:otherwise>
                            <td><c:out value="Inactive"/></td>
                    </c:otherwise>
                    </c:choose>            

                    <td>
                        <form action="recipeGenerator" method="post">
                            <input type="hidden" name="action" value="selectIngredient">
                            <input type="hidden" name="ingredientId" value="${ingredient.id}">
                            <input type="hidden" name="ingredient_name" value="${ingredient.ingredient_name}">
                            <input type="submit" class="edit" value="Select">
                        </form>
                    </td>
                </tr>
                </c:forEach>
            </table>
        </div>
    </section>
</main>
<c:import url="/includes/footer.jsp"></c:import>

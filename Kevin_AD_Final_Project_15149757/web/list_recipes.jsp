<%-- 
    Document   : list_customer
    Created on : Nov 29, 2018, 3:27:32 PM
    Author     : 15149757
--%>

<%@ include file="/includes/header.jsp" %>
<title>Recipe List</title>
    <h1>Select Recipe</h1>
<main>
    <section>
        <div class="column1">
            <p class="error"><i><c:out value="${message}" default="" /></i></p>
            <table border="1">
                <tr>
<!--                    <th>ID</th>-->
                    <th>Name</th>
<!--                    <th>Status</th>-->
                    <th>Manage</th>
                </tr>

                <c:forEach var="recipe" items="${recipes}">
                <tr>
<!--                    <td><c:out value="${recipe.id}"/></td>-->
                    <td><c:out value="${recipe.recipe_name}"/></td>
<!--                    <c:choose>
                        <c:when test="${recipe.active == 1}">
                            <td><c:out value="Active"/></td>
                        </c:when>
                    <c:otherwise>
                            <td><c:out value="Inactive"/></td>
                    </c:otherwise>
                    </c:choose>            -->

                    <td>
                        <form action="recipeGenerator" method="post">
                            <input type="hidden" name="action" value="selectRecipe">
                            <input type="hidden" name="recipeId" value="${recipe.id}">
                            <input type="hidden" name="recipe_name" value="${recipe.recipe_name}">
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

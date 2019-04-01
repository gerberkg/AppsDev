<%-- 
    Document   : list_customer
    Created on : Nov 29, 2018, 3:27:32 PM
    Author     : 15149757
--%>

<%@ include file="/includes/header.jsp" %>
<title>Category List</title>

<h1>Select Category</h1>

<main>
    <section>
        <div class="column1">
            <form action="recipeGenerator" method="post">
                <p class="error"><i><c:out value="${message}" default="" /></i></p>
                <table border="1">
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Status</th>
                        <th>Select?</th>
                    </tr>

                    <c:forEach var="category" items="${categories}">
                        <tr>
                            <td><c:out value="${category.id}"/></td>
                            <td><c:out value="${category.category_name}"/></td>
                            <c:choose>
                                <c:when test="${category.active == 1}">
                                    <td><c:out value="Active"/></td>
                                </c:when>
                                <c:otherwise>
                                    <td><c:out value="Inactive"/></td>
                                </c:otherwise>
                            </c:choose>            

                            <td>
                                <input type="checkbox" name="checkCategory" value="${category.id}"/>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <input type="hidden" name="action" value="selectCategories">
                <input type="submit" class="edit" value="Select">
            </form>
        </div>
    </section>
</main>
<c:import url="/includes/footer.jsp"></c:import>

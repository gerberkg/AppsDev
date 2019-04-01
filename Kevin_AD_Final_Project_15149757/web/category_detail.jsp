<%-- 
    Document   : index
    Created on : Feb 18, 2019, 9:34:20 AM
    Author     : 15149757
--%>

<title>AD Final Project Recipe Generator</title>

<%@ include file="/includes/header.jsp" %>

<form action="recipeGenerator" method="post">
    <p class="error"><i><c:out value="${message}" default="" /></i></p>
    <label>Category Name:</label>
    <c:choose>
        <c:when test="${category_name != null}">
            <input type="text" name="category_name" 
                   value="${category_name}" autofocus="yes"/><br>
        </c:when>
        <c:otherwise>
            <input type="text" name="category_name" 
                   value="${category.category_name}" autofocus="yes"/><br>
        </c:otherwise>
    </c:choose>
    <label>Status:</label>
    <select name="status">
        <option value="1" ${category.active == "1" ? 'selected="selected"' : ''}>Active</option>
        <option value="0" ${category.active == "0" ? 'selected="selected"' : ''}>Inactive</option>
    </select><br>
    

    <input type="hidden" name="categoryId" value="${category.id}">
    <input type="hidden" name="action" value="updateCategory">
    <input class="submit_user" type="submit" value="Save"/><br>

</form>
<c:import url="/includes/footer.jsp"></c:import>

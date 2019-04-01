<%-- 
    Document   : index
    Created on : Feb 18, 2019, 9:34:20 AM
    Author     : 15149757
--%>

<title>AD Final Project Recipe Generator</title>

<%@ include file="/includes/header.jsp" %>

<form action="recipeGenerator" method="post">
    <p class="error"><i><c:out value="${message}" default="" /></i></p>
    <label>First Name:</label>
        <c:choose>
            <c:when test="${firstName != null}">
                <input type="text" name="first_name" 
                       value="${firstName}" autofocus="yes"/><br>
            </c:when>
            <c:otherwise>
                <input type="text" name="first_name" 
                       value="${user.firstName}" autofocus="yes"/><br>
            </c:otherwise>
        </c:choose>
        <label>Last Name:</label>
        <c:choose>
            <c:when test="${lastName != null}">
                <input type="text" name="last_name" 
                       value="${lastName}"/><br>
            </c:when>
            <c:otherwise>
                <input type="text" name="last_name" 
                       value="${user.lastName}"/><br>                
            </c:otherwise>
        </c:choose>
        <label>Email Address:</label>
        <c:choose>
            <c:when test="${email != null}">
                <input type="text" name="email" placeholder="Optional" 
                       value="${email}" /><br>
            </c:when>
            <c:otherwise>
                <input type="text" name="email" placeholder="Optional"
                       value="${user.email}" /><br>
            </c:otherwise>
        </c:choose>

        <label>User ID:</label>
        <c:choose>
            <c:when test="${userId != null}">
                <input type="text" name="userID" value="${userId}" /><br>
            </c:when>
            <c:otherwise>
                <input type="text" name="userID" value="${user.userId}" /><br>
            </c:otherwise>
        </c:choose>
        <label>Password:</label>
        <c:choose>
            <c:when test="${password != null}">
                <input type="text" name="password" value="${password}" /><br>
            </c:when>
            <c:otherwise>
                <input type="text" name="password" value="${user.password}" /><br>
            </c:otherwise>
        </c:choose>
        <label>Verify Password:</label>
        <c:choose>
            <c:when test="${verifyPassword != null}">
                <input type="text" name="verifyPassword" value="${verifyPassword}" /><br>
            </c:when>
            <c:otherwise>
                <input type="text" name="verifyPassword" value="${user.verifyPassword}" /><br>
            </c:otherwise>
        </c:choose>

        <input type="hidden" name="userId" value="${user.id}">
        <input type="hidden" name="isAdmin" value="0">
        <input type="hidden" name="action" value="registerUser">
        <input class="submit_user" type="submit" value="Save"/><br>

</form>
<c:import url="/includes/footer.jsp"></c:import>

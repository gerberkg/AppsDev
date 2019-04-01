<%-- 
    Document   : login
    Created on : March 1, 2019, 3:06:12 PM
    Author     : 15149757
--%>

<title>AD Recipe Generator</title>
<%@ include file="/includes/header.jsp" %>

<main>
    <section>   
        <h1>Login Form</h1><br>
        <p>Please enter your username and password to continue</p><br>
        <p class="error"><i><c:out value="${message}" default="" /></i></p>
        <form action="recipeGenerator" method="post"> 
            <label>Username: </label>
            <input type="text" name="username"><br>
            <label>Password: </label>
            <input type="password" name="password"><br>

            <input type="hidden" name="action" value="processLogin">
            <input type="submit" value="Login">
        </form>
    </section>
</main>

<c:import url="/includes/footer.jsp"></c:import>

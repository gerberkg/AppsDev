<%-- 
    Document   : index
    Created on : Feb 18, 2019, 9:34:20 AM
    Author     : 15149757
--%>

<title>AD Final Project Recipe Generator</title>

<%@ include file="/includes/header.jsp" %>

<form action="recipeGenerator" method="post">
    <section>
        <h2 class="welcome">Welcome to the recipe generator!</h2>
        
        <p>You must be a registered user in order to use this site.</p>
        <p>Please click the Login option above to login, or the User Registration option 
            to register as a new user.</p>
    </section>
</form>
<c:import url="/includes/footer.jsp"></c:import>

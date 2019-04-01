<%-- 
    Document   : index
    Created on : Feb 18, 2019, 9:34:20 AM
    Author     : 15149757
--%>

<title>AD Final Project Recipe Generator</title>

<%@ include file="/includes/header.jsp" %>

<form action="recipeGenerator" method="post">
    <section>
        <p class="error"><i><c:out value="${message}" default="" /></i></p>
        <h2 class="welcome">Welcome to the recipe generator!</h2>
        <h1>Search Form still under construction!</h1>
        <fieldset>
            <legend>Search Type</legend>
                    <label class="container" for="ingredient">Ingredient List
                    <input type="radio" id="ingredient" name="search_type" value="ingredient" checked="checked">
                    <span class="checkmark"></span>
                </label> 
                <label class="container" for="category">Category
                    <input type="radio" id="category" name="search_type" value="category">
                    <span class="checkmark"></span>
                </label>
                <label class="container" for="nameCat">Name
                    <input type="radio" id="nameCat" name="search_type" value="name">
                    <span class="checkmark"></span>
                </label>
                <br><br>

                <!--<input class="search_input" type="text" name="search_criteria">-->
                <input type="hidden" name="action" value="search">
                <input class="search_btn" type="submit" value="Search"><br><br>
         </fieldset>
   
    </section>
</form>
<c:import url="/includes/footer.jsp"></c:import>

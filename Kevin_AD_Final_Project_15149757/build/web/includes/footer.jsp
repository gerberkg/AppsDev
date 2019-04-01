<%-- 
    Document   : footer
    Created on : Feb 18, 2019, 9:28:16 AM
    Author     : 15149757
--%>
<%@page import="java.util.GregorianCalendar, java.util.Calendar" %>
<%
    GregorianCalendar currentDate = new GregorianCalendar();  
    int currentYear = currentDate.get(Calendar.YEAR);
%>

<footer>
    <form action="recipeGenerator" method="post">
        <input type="hidden" name="action" value="home">
        <input class="homeBtn" type="submit" value="Home">
    </form>
    <p>
        <span class="copyright">&copy;&nbsp;&nbsp; <%= currentYear %> , WITC / Kevin Gerber</span>
        <span class="customerServiceEmail">Customer Service email:  ${initParam.custServEmail}</span> 
    </p>
</footer>

    </body>
<!--        <script>
        const navHamburger = document.querySelector('#navHamburger');
        
        navHamburger.addEventListener('click', (e) => {
            navHamburger.parentElement.classList.toggle('active');
        });
    </script>-->

</html>

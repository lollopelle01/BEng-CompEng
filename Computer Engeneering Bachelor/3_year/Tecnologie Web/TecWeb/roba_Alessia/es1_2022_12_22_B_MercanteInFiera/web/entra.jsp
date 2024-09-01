<%@ page session="true"%>
<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<!-- import di classi Java -->
<%@ page import="Beans.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.google.gson.Gson"%>

<!-- metodi richiamati nel seguito -->
<%!//%>

<!-- codice html restituito al client -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
        <!-- per non avere problema di cache -> aggiungo nell'header delle pagine HTML -->
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="Pragma" content="no-cache"/>
        <meta http-equiv="Expires" content="-1"/>

        <title> Entra </title>

        <link rel="stylesheet" href="scripts/default.css" type="text/css"/>
		
		<script type="text/javascript" src="scripts/utils.js"></script>
        <script type="text/javascript" src="scripts/integrale.js"></script>
        
		<%
			Utente user = (Utente) session.getAttribute("currentUser"); 
			if(user == null){
            %>
                <jsp:forward page="login.jsp"></jsp:forward>
            <%
			}
		 %>
    </head>
    <body>
       	<h3> Sei pronto per giocare <%=user.getUsername()%>?</h3>
    	<h4><a href="./wait.jsp">ENTRA</a></h4>    	
    </body>
</html>
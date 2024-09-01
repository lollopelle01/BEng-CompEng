<!-- STRUTTURA PAGINA HTML -->
<%@ page session="true"%>

<%@ page errorPage="../errors/failure.jsp"%>

<!-- import di classi Java -->

<%@ page import="java.util.*"%>
<%@ page import="Beans.*"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
        <!-- per non avere problema di cache -> aggiungo nell'header delle pagine HTML -->
        <meta http-equiv="Pragma" content="no-cache"/>
        <meta http-equiv="Expires" content="-1"/>
        <meta http-equiv="refresh" content="5"/>
	
		<title> Entra nell'asta </title>
	
		</head>
    <body>
    	<%
			Gioco game = (Gioco) this.getServletContext().getAttribute("game");
    		
    		if(game.getUsers().size() < 3) {
   		%>
   				<h3> Attendiamo che si logghino altri utenti.</h3>
   		<% 	
   			} else {
		%>		
				<jsp:forward page="/fiera.jsp"></jsp:forward>
    	<% 	
    		}
		%>
    </body>
</html>
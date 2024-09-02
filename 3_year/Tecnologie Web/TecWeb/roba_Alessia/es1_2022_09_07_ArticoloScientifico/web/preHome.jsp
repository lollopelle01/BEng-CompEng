<!-- STRUTTURA PAGINA HTML -->
<%@ page session="true"%>

<%@ page errorPage="../errors/failure.jsp"%>

<!-- import di classi Java -->

<%@ page import="java.util.*"%>
<%@ page import="Beans.Articolo"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
        <!-- per non avere problema di cache -> aggiungo nell'header delle pagine HTML -->
        <meta http-equiv="Pragma" content="no-cache"/>
        <meta http-equiv="Expires" content="-1"/>
	
		<title> Home </title>
	
		</head>
    <body>
    	<%
    		String utente = (String) session.getAttribute("success");
    	%>
    
    	<h3><%=utente%>, benvenuto!</h3>
    	<h4><a href="./home.jsp">Clicca qui per la visualizzazione dell'articolo scientifico.</a></h4>
    </body>
</html>
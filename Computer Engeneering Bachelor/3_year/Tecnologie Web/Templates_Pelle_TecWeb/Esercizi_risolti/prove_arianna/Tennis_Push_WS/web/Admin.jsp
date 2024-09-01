<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<%@ page session="true"%>
<%@ page import="beans.Utente"%>
<%@ page import="beans.DBUtenti"%>
<%@ page import="java.util.*"%>

<html>
	<head>
		<title>Admin</title>
		<link rel="stylesheet" href="styles/default.css" type="text/css"></link>
		<script type="text/javascript" src="scripts/json.js"></script>
		<script type="text/javascript" src="scripts/my_functions.js"></script>
		
	</head>
	<body>
       	
       	<%
       	DBUtenti dbUtenti = (DBUtenti) application.getAttribute("DBUtenti");
       	
       	for (Utente u : dbUtenti.getUtentiAttivi()){
       		if(u.getNome().equals("admin")){
       			continue;
       		}
       		%>
       		<p>Username: <%= u.getNome() %></p>
       		<p>Timestamp di iscrizione: <%= u.getStart() %></p>
       	<%
       	}
		%>
		
	</body>
</html>

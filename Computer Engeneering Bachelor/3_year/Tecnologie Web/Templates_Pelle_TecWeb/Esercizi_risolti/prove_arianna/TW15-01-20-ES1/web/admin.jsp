<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>

	<head>
		<meta charset="ISO-8859-1">
		    <%@ page import="java.util.*" %>
		    <%@ page import="it.unibo.tw.web.beans.*" %>
		<title>Amdmin Page</title>
	</head>
	
	<body>
	<% 	Gruppo gruppo1 = (Gruppo) application.getAttribute("Gruppo1");
		Gruppo gruppo2 = (Gruppo) application.getAttribute("Gruppo2");
		Gruppo gruppo3 = (Gruppo) application.getAttribute("Gruppo3");
	
		if(gruppo1 != null) { %>
			<div>
			<%=gruppo1.getNomeGruppo() %><br>
			<% for(Utente u : gruppo1.getUtenti()) { %>
		    	<p> <%=u.toString() %> </p>
		    <% } %>
			</div> <br>
	 <% } 
	 	if(gruppo2 != null) { %>
			<div>
			<%=gruppo2.getNomeGruppo() %><br>
			<% for(Utente u : gruppo2.getUtenti()) { %>
		    	<p> <%=u.toString() %> </p>
		    <% } %>
			</div> <br>
	 <% } 
	 	if(gruppo3 != null) { %>
			<div> 
			<%=gruppo3.getNomeGruppo() %><br>
			<% for(Utente u : gruppo3.getUtenti()) { %>
		    	<p> <%=u.toString() %> </p>
		    <% } %>
			</div> <br>
	 <% } %>
	
	</body>
</html>

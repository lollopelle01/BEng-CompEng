<%@page import="com.google.gson.Gson"%>
<%@page import="beans.Result"%>
<%@page import="beans.Giocatore"%>
<%@page import="java.util.*"%>
<%@ page import="java.time.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<header><h1>CARTA VINCITRICE</h1></header>
	
	<main>
	<% Random random = new Random(); 
		int vincitrice = random.nextInt(20) +1;
	%>
		<div>La carta vicnitrice di questo mercante in fiera è la <%=vincitrice %></div>
	</main>
</body>
</html>
<%@page import="com.google.gson.Gson"%>
<%@page import="beans.*"%>
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
<%Map<String, Giocatore> giocatori = (Map<String, Giocatore>) getServletContext().getAttribute("giocatori");

%>
<body>
	<header><h1>CARTA VINCITRICE</h1></header>
	
	<main>
	<% Random random = new Random(); 
		int vincitrice = random.nextInt(30) +1;
		
		String vincitore = "";
		
		for(String s: giocatori.keySet()){
			for(int carta: giocatori.get(s).getCarte()){
				if(carta==vincitrice){
					vincitore = s;
					break;
				}
			}
			if(vincitore!="")
				break;
		}
	%>
		<div>La carta vincitrice di questo mercante in fiera è la <%=vincitrice %></div>
		<div>Congratulazioni a <%=vincitore %> per aver vinto</div>
	</main>
</body>
</html>
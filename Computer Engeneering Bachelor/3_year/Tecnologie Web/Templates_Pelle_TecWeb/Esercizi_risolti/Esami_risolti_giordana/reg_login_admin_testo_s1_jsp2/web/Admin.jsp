<%@page import="servlets.SessionListener"%>
<%@page import="beans.CounterRichieste"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./styles/home.css">
<title>Administrator</title>
</head>
<body>
	<%
		if(session.getAttribute("logged") == null || !((String) session.getAttribute("logged")).equals("Admin.")) {
			response.sendRedirect("./login.jsp");
		}
		
		Map<String, Integer> richiestePerSessione = (Map<String, Integer>) getServletContext().getAttribute("richieste");
		CounterRichieste richiesteUltimaOra = (CounterRichieste) getServletContext().getAttribute("counter");
		int counter = SessionListener.getCounter();
	%>
	
	        <header><h1>HOMEPAGE</h1></header>
	        <main>
	        	<div>Sessioni attualmente attive: <%=counter %></div>
	        	<div>Richieste nell'ultima ora: <%=richiesteUltimaOra.getCounter() %></div>
	        	<table>
	        		<tr>
	        			<th>Session ID</th>
	        			<th>Numero richieste</th>
	        		</tr>
	        		<%for(String sessionID : richiestePerSessione.keySet()) {%>
	        			<tr>
		        			<td><%=sessionID %></td>
		        			<td><%=richiestePerSessione.get(sessionID) %></td>
	        			</tr>
	        		<%} %>
	        	</table>
	        </main>
</body>
</html>
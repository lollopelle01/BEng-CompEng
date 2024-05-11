<%@page import="servlets.SessionListener"%>
<%@page import="beans.CounterRichieste"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Refresh" content= "2"/>
<link rel="stylesheet" href="./styles/home.css">
<title>Administrator</title>
</head>
<body>
	<%
		if(session.getAttribute("logged") == null || !((String) session.getAttribute("logged")).equals("Admin.")) {
			response.sendRedirect("./login.jsp");
		}
		
		Map<String, Integer> richiestePerSessione = (Map<String, Integer>) getServletContext().getAttribute("richieste_per_sessione");
		CounterRichieste richiesteUltimaOra = (CounterRichieste) getServletContext().getAttribute("counter_ultimaOra_nonAdmin");
		int counter = (int) getServletContext().getAttribute("counter_admin");
	%>
	
	        <header><h1>HOMEPAGE</h1></header>
	        <main>
	        	<div>Richieste fatte dall'admin in generale: <%=counter %></div>
	        	<div>Richieste nell'ultima ora da utenti non autenticati: <%=richiesteUltimaOra.getCounter() %></div>
	        	<table>
	        		<tr>
	        			<th>Session ID</th>
	        			<th>Numero richieste per questa sessione</th>
	        		</tr>
	        		<%for(String sessionID : richiestePerSessione.keySet()) {%>
	        			<tr>
		        			<td><%=sessionID %></td>
		        			<td><%=richiestePerSessione.get(sessionID) %></td>
	        			</tr>
	        		<%} %>
	        	</table>
	        	<div class="richieste">Vuoi ritorare nella home.jsp? <a href="home.jsp">Clicca qui</a></div><!-- senza il ./ -->
	        </main>
</body>
</html>
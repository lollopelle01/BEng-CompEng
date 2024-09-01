<%@page import="servlets.SessionListener"%>
<%@page import="beans.CounterRichieste"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.time.Duration"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.Instant"%>
<%@page import="java.util.TimeZone"%>
<%@page import="java.time.LocalDateTime"%>
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
		Map<String, Integer> richiestePerSessione = (Map<String, Integer>) getServletContext().getAttribute("richieste");
		CounterRichieste richiesteUltimaOra = (CounterRichieste) getServletContext().getAttribute("counter");
		int counter = SessionListener.getCounter();
		List<HttpSession> sessioni_attive = (List<HttpSession>) getServletContext().getAttribute("sessioni_attive");
	%>
	
	        <header><h1>HOMEPAGE</h1></header>
	        <main>
	        	<div>Sessioni attualmente attive: <%=counter %></div>
	        	<div>Richieste nell'ultima ora: <%=richiesteUltimaOra.getCounter() %></div>
	        	<div>Lista delle sessioni che sono state attive negli ultimi 30 giorni: <br>
	        		<ul>
	        			<%for(int i=0; i<sessioni_attive.size(); i++){
	        				LocalDateTime triggerTime =LocalDateTime.ofInstant(Instant.ofEpochMilli(sessioni_attive.get(i).getLastAccessedTime()), TimeZone.getDefault().toZoneId());
	        				Duration duration = Duration.between(triggerTime, LocalDateTime.now());
	        				if(duration.toDays() < 30) {%>
	        					<li><%=sessioni_attive.get(i).getId() %></li>
	        				<%} %>
	        			<%} %>
	        		</ul>
	        	</div>
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
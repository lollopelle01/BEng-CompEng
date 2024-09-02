<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="beans.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>Administrator</title>
	</head>
	<body>
	
	<!-- FORNIAMO VISUALE PRENOTAZIONI -->
	<h1>Ciao Admin!<br>Elenco delle prenotazioni:</h1>
	<%
		ArrayList<Prenotazione> lista_prenotazioni = (ArrayList<Prenotazione>) this.getServletContext().getAttribute("lista_prenotazioni_ufficiali");
		for ( Prenotazione p : lista_prenotazioni){%>
			Hotel: <%= p.getId_hotel()%>
			Check-in: <%= p.getCheck_in()%>
			Check-out: <%= p.getCheck_out()%>
			Prezzo: <%= p.getPrezzo_finale()%>
		<%}%>
	
	</body>
	<script>
		setTimeout(function(){
			   window.location.reload();
			}, 5000);
	</script>
</html>
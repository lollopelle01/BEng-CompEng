<%@page import="beans.*"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>Administrator</title>
	</head>
	<body>
	
	<% // In quanto admin ho sempre accesso ai tavoli
		Map<Integer, ArrayList<Cliente>> tavoli = (HahsMap<Integer, ArrayList<Cliente>>) getServletContext().getAttribute("tavoli");
	%>

	<h1> Ciao Admin! </h1>

	<form action="service" method="get">
        <select id="richiesta">
            <option value="visualizza_tavoli">Visualizza i tavoli</option>
            <option value='chiusura_bar'>Chiudi il bar</option>
        </select>        
        <button type="submit">
    </form>

<%if((boolean)getServletContext().getAttribute("chiusura_tavolo")==true){%>

	<div id="lista_tavoli">
		<% for(int tavolo : this.tavoli.keySet()) {%>
			<h1> Tavolo <%= tavolo%> </h1>
			<%for(Cliente c : this.tavoli.get(tavolo)) {%>
				<p> <%= c.toString() %> 
			<%}%>
		<%}%>
	</div>

<%}%>


	</body>
	<script>
		// Così avrà una visione aggiornata della lista, se la vuole
		setTimeout(function(){ 
			   window.location.reload();
			}, 5000);
	</script>
</html>
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

	<h1> Ciao Cameriere! </h1>

<%if((boolean)getServletContext().getAttribute("chiusura_tavolo")!=true){%>
	
	Tra questi drink:
	<div id="lista_tavoli">
		<% for(int tavolo : this.tavoli.keySet()) {%>
			<h1> Tavolo <%= tavolo%> </h1>
			<%for(Cliente c : this.tavoli.get(tavolo)) {%>
				<p> <%= c.toString() %> 
			<%}%>
		<%}%>
	</div>

	Quali hai consegnao?
    <h1> CONSEGNA DRINK </h1>
    <form action="service" method="post">
        
        <label> Nome drink <br>
            <input type="text" name="nome_drink" id="nome_drink"> <br>
        </label>
        <label> Costo drink <br>
            <input type="text" name="costo_drink" id="costo_drink"> <br>
        </label>
		<label> Utente <br>
            <input type="text" name="utente" id="utente"> <br>
        </label>
		<label> Tavolo <br>
            <input type="text" name="numTavolo" id="numTavolo"> <br>
        </label>
        
        <input type="text" name="richiesta" id="richiesta" style="display: none;" value="cameriere"> <br>

        <button type="submit">
    </form>
<%}
	else {%>
		Bar chiuso, vai a dormire!!
<%}%>

	</body>
	<script>
		// Così avrà una visione aggiornata della lista, se la vuole
		setTimeout(function(){ 
			   window.location.reload();
			}, 5000);
	</script>
</html>
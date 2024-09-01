<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<%@ page session="true"%>
<%@ page import="beans.Utente"%>
<%@ page import="beans.Gioco"%>
<%@ page import="java.util.*"%>

<%
	Utente u = (Utente) session.getAttribute("utente");
	if (u.isSessioneAttiva() != true){
%>
<html>
	<head>
		<title>mainUser</title>
		<link rel="stylesheet" href="styles/default.css" type="text/css"></link>
	</head>
	<body>
       	NON PUOI PARTECIPARE AL GIOCO
	</body>
</html>
<%
	} 
	else 
	{
%>

<html>
  <head>
		<title>mainUser</title>
		<link rel="stylesheet" href="styles/default.css" type="text/css"></link>
		<script type="text/javascript" src="scripts/json.js"></script>
		<script type="text/javascript" src="scripts/my_functions.js"></script>
  </head>
	
  <body>
	
	<script type="text/javascript">
		function onChange() {
			document.forms["vendita"].submit();
		}
	</script>
	
	<%
		Gioco gioco = (Gioco) application.getAttribute("gioco");
		if(gioco.isGiocoCominciato()){
			if(!gioco.isVendita()){
	%>
	       	<fieldset><legend>Vendita</legend>
				<form name="vendita" action="j1.jsp" method="post"> 
				
					<label for="numero">Numero</label>
					<input type="text" id="numero" name="numero" placeholder="numero" required onChange="onChange()"> <br />
				
				</form>
			</fieldset>
		
		<%	} else {
				int cartaVenduta = gioco.utenteVenditore().getCartaVendita();
				int offerta = gioco.utenteVincitoreOfferta().getOfferta();
			%>
				<h1>Carta venduta: <%= cartaVenduta %></h1>
				
				<fieldset><legend>Offerta</legend>
					<form name="offerta" action="S2" method="post"> 
					
						<label for="numero">Denaro</label>
						<input type="text" id="numero" name="numero" placeholder="numero" required> <br />
						<input type="submit" value="offerta">
					
					</form>
				</fieldset>
				
				<h1>Offerta piu' alta: <%= offerta %></h1>
		<%
			}
		}
		
		//sotto mostro comunque la lista degli utenti e l'evoluzione del gioco
		for(Utente item : gioco.getUtenti()){
			String nome = item.getNome();
		%>
			<h1><%=nome %></h1>
			<p>Denaro disponibile: <%=item.getDenari() %></p> <br>
			<p><%= item.getCarte().toString() %></p>
		<%
		}
		List<String> messaggio = gioco.getMessaggio();
		%>
		<fieldset><legend>Messaggi</legend>
		<% 
		for(String mes : messaggio){
		%>
			<p><%= mes %></p> <br>
		<%
		}
		%>
		</fieldset>
  </body>
</html>
<%
	}
%>
<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<!-- accesso alla sessione -->
<%@ page session="true"%>

<!-- import di classi Java -->
<%@ page import="beans.Socio"%>
<%@ page import="beans.Torneo"%>
<%@ page import="beans.Circolo"%>
<%@ page import="java.util.*"%>

<!-- codice html restituito al client -->
<!DOCTYPE HTML PUBLIC>

<%
	Torneo torneo = (Torneo) session.getAttribute("torneo");
	if (torneo.isStarted() != true){
%>
<html>
	<head>
		<title>Welcome</title>
		<link rel="stylesheet" href="styles/default.css" type="text/css"></link>
	</head>
	<body>
       	I posti sono esauriti e il torneo è già iniziato!
       	Attendi il prossimo torneo
	</body>
</html>
<%
	} 
	else 
	{
%>

<html>
  <head>
		<title>Welcome</title>
		<link rel="stylesheet" href="styles/default.css" type="text/css"></link>
		<script type="text/javascript" src="scripts/json.js"></script>
  </head>
	
  <body>
  	<h1>Torneo</h1>
	
       	<fieldset>
       	<legend>Iscrizione</legend>
			<form name="iscrizione" action="J1.jsp" method="post"> 
				<input type="submit" value="iscrivimi">
			</form>
		</fieldset>
		
		<%	if(!torneo.isFaseiscrizione()) {
				int[] sem1 = torneo.getSemifinale1();
				int[] sem2 = torneo.getSemifinale2();
		%>
				<h5>Accoppiamento delle 2 semifinali:</h5>
				Squadra<%=sem1[0]%> vs Squadra<%=sem1[1]%><br>
				Squadra<%=sem2[0]%> vs Squadra<%=sem2[1]%><br>
				
				<p>Avete 24h per lo svolgimento delle semifinali</p><br>
			
			<%	
				Socio s = (Socio)session.getAttribute("username");
				if(s.isCapitano()) {
			%>
					<p>Al termine, comunicate il risultato qui sotto<p>
			
					<fieldset>
					<legend>Risultato della semifinale</legend>

						<form name="Semifinal" action="S1">
							<label for="semifinale">Semifinale numero</label>
							<input type="text" id="semifinale" name="semifinale" size="2" required> <br />
						
							<label for="vincitore">Vincitore (numero squadra)</label>
							<input type="text" id="vincitore" name="vincitore" size="2" required> <br />
							
							
							<label for="risultato">Risultato</label>
							<input type="text" id="risultato" name="risultato" size="5" required> <br />
							
							<input type="submit" id="riferisci" value="riferisci" >
						</form>
						
					</fieldset>
					
					<% 	String inc = null;
						if( (inc = (String)request.getAttribute("incorretto")) != null) { %>
							<p><%=inc%></p><br>
					<%	}
					%>
			<% }
				
				if(session.getServletContext().getAttribute("done") != null) {
					String done = (String) session.getServletContext().getAttribute("done"); %>
					<p><%=done%></p><br>
			<% }
		}%>
  </body>
</html>
<% 
}
%>

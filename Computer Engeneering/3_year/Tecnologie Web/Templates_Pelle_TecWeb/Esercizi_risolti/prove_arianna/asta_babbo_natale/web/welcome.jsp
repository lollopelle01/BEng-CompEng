<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<!-- accesso alla sessione -->
<%@ page session="true"%>

<!-- import di classi Java -->
<%@ page import="java.util.*"%>
<%@ page import="beans.*"%>

<!-- codice html restituito al client -->
<!DOCTYPE HTML PUBLIC>
<html>
	<head>
	
		<meta http-equiv="Pragma" content="no-cache"/>
		<meta http-equiv="Expires" content="-1"/>

		<title>Asta di babbo natale!</title>
	</head>

	<body>
	
	<%
		Asta asta = (Asta)getServletContext().getAttribute("asta");
		if (!asta.isStart()) { %>
			<p>Il numero di partecipanti non è ancora sufficiente per l'inizio dell'asta</p>
	<% 	} 
		else {
	%>
			<h3>Benvenuti all'asta di Babbo natale!</h3>
			
			<p>L'attuale regalo in vendita è: <%=asta.getInvendita().toString()%></p><br/>
			
			
			<fieldset>
			<form action="J1.jsp">
				<label for="offerta">Fai la tua offerta:</label><br>
		     	<input type="text" id="offerta" name="offerta" required  size="3">
			</form>
			</fieldset>
			
			<%
				String msg = (String)session.getAttribute("msg");
				if(msg != null) %>
					<p><%=msg%></p><br/>
					
			<% 	String fine = (String)getServletContext().getAttribute("fine");
				if(fine != null) %>
				<p><%=fine%></p><br/>
	
	<%} %>
		
	</body>
</html>

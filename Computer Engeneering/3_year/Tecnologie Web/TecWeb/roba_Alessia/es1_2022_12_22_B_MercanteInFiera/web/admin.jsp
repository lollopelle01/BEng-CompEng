<%@ page session="true"%>
<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<!-- import di classi Java -->
<%@ page import="Beans.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.google.gson.Gson"%>


<!-- metodi richiamati nel seguito -->
<%!//%>

<!-- codice html restituito al client -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
        <!-- per non avere problema di cache -> aggiungo nell'header delle pagine HTML -->
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="Pragma" content="no-cache"/>
        <meta http-equiv="Expires" content="-1"/>

		<title> Pagina Gestione Amministratore </title>

        <link rel="stylesheet" href="scripts/default.css" type="text/css"/>
		
		<script type="text/javascript" src="scripts/utils.js"></script>
        <script type="text/javascript" src="scripts/integrale.js"></script>
        
		
        <% 
			<!-- controllo autenticazione è stata fatta o meno -->
			String auth = (String) session.getAttribute("auth");
			Utente user = (Utente) session.getAttribute("user"); 
			
			if(auth==null || auth.equals("-1") || user==null || !user.getUsername().equals("admin")){
            %>
                <jsp:forward page="login.jsp"></jsp:forward>
            <%
			}

			<!-- creo una lista riempita con le prenotazioni effettuate da poi visualizzare -->
			List<Prenotazione> pre = (List<Prenotazione>)this.getServletContext().getAttribute("prenotazioneTerminata");

		 %>
    </head>
    <body>
		<h3> Prenotazioni effettuate </h3>
        <%
		for(Prenotazione p : pre) {
			%>
			<div>
				Codice Hotel: <%=p.getID() %><br>
				CheckIn: <%=p.getCheckIn() %><br>
				CheckOut: <%=p.getCheckOut() %><br>
				Prezzo: <%=p.getPrezzo() %><br><br>
			</div>
			<% } %>
    </body>
</html>
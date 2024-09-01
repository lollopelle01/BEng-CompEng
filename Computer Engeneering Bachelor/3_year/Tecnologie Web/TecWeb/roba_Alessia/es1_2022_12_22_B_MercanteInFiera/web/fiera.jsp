<%@page import="java.time.LocalTime"%>
<%@ page session="true"%>
<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<!-- import di classi Java -->
<%@ page import="Beans.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.google.gson.Gson"%>

<!-- metodi richiamati nel seguito -->

<!-- codice html restituito al client -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
        <!-- per non avere problema di cache -> aggiungo nell'header delle pagine HTML -->
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="Pragma" content="no-cache"/>
        <meta http-equiv="Expires" content="-1"/>

        <title> Mercante in fiera </title>

        <link rel="stylesheet" href="scripts/default.css" type="text/css"/>
		
		<%
			Utente user = (Utente) session.getAttribute("currentUser"); 
			if(user == null){
            %>
                <jsp:forward page="login.jsp"></jsp:forward>
            <%
			}
		 %>
    </head>
    <body>
       	<%
       		Gioco game = (Gioco) this.getServletContext().getAttribute("game");	
       		Utente utente = (Utente) session.getAttribute("currentUser");	
			
       		System.out.println(utente.getCarte());
       		
       		if(game.getStartGame() == null) {
       			//inizializzo il gioco
       			game.setStartGame(LocalTime.now());
      		}
		%>
       		<h3>Carte assegnate:</h3>
       		
       	<%		
       		
       		if (request.getParameter("vendi") != null && request.getParameter("vendi").equals("vendi")) {
					
    			int numero = Integer.parseInt(request.getParameter("numero"));
    			
    			
			} else if (request.getParameter("vendi") != null && request.getParameter("vendi").equals("vendi")) {

			}
      	%>
	       	<div id="utenti">
				<p><b>Utenti connessi</b></p>
				<table>
		<%
						for(Utente u : game.getUsers()) {
		%>
						<tr>
							<td><%=u.getUsername()%></td>
						</tr>
		<%		
						}
		%>
				</table>
		    </div>
       		<hr>
       		<div id="vendita">
       			<p><b>Quale carta vuoi vendere?</b></p>
       			<table>
       	<%
       		for(Carta carta : utente.getCarte()) {
       	%>

					<tr>
						<td><button name="numero" value="<%=carta.getNumero()%>"></button></td>
						<td><input type="submit" id="<%=carta.getNumero()%>" name="vendi" value="Vendi"/></td>
       				<tr>
       	<%		
				}
		%>
       			</table>
       		</div>
       	<%
			if(game.getCartaInVendita() != null) {
		%>	
       	
       		<div id="carte in vendita">
       			<script>
       				document.getElementById("vendita").disabled = true;	
       			</script>
				<p><b>Carta in vendita</b></p>
				<table>
		<%
						Carta cartaInVend = game.getCartaInVendita();
		%>
						<tr>
							<td>Numero carta:</td>
							<td><%=cartaInVend.getNumero()%></td>
						</tr>
						<tr>
							<td>Utente che possiede la carta:</td>
							<td><%=cartaInVend.getUtente().getUsername()%></td>
						</tr>
				</table>
		    </div>
		<%		
			}
		%>
    </body>
</html>
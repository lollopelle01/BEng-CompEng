<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<!-- accesso alla sessione -->
<%@ page session="true"%>

<!-- import di classi Java -->
<%@ page import="beans.Utente"%>
<%@ page import="beans.UtentiAutenticati"%>
<%@ page import="java.util.*"%>

<!-- codice html restituito al client -->
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Admin page</title>
	</head>
<body>

	<h1>Admin page</h1>
	<form>
		<button name="add" value="list">Show users</button>
	</form>
	
	<div>
		<%
			String listUtenti="";
			
			if ( request.getParameter("add") != null && request.getParameter("add").equals("list") ) {
				
				UtentiAutenticati utenti=(UtentiAutenticati)this.getServletContext().getAttribute("UtentiAutenticati"); 
				listUtenti+="Username \tLoggato\n ";
				
				for(Utente u: utenti.getUtenti()) {
					listUtenti+=u.getUsername()+"\t"+u.isLogged()+"\n";
					// i loggati sono quelli con le sessioni attive
				}
			}
			
			//per stampare le richieste di ogni sessione vado a prendere la variabile countRequest di ogni utente loggato
			
			//per stampare tutte le richieste degli ultimi 60 minuti, vado a prendere la lista di LocalDateTime e 
			//conto quelle che sono state fatte non prima di 60 minuti rispetto all'ora di adesso
		%>
		<textarea id="listaUtenti" rows="30" cols="55" style="border: 1px solid black;" readonly ><%=listUtenti%></textarea>
	</div>


</body>

</html>
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

	<% List<HttpSession> sessioniattive = (List<HttpSession>)this.getServletContext().getAttribute("sessioniattive"); %>
	
	<form name="Invalidator">
	<%	for(HttpSession s : sessioniattive) { %>
			<input type="text"><% s.getId() %></input>
	<%	}
	%>
		<button name="add" value="list">Show users</button>
	</form>
	
	<div>
		
		<textarea id="listasessioni" rows="30" cols="55" style="border: 1px solid black;" readonly ><%=sessioniattive%></textarea>
		
		<%
		
			
			String listUtenti="";
			
			if ( request.getParameter("add") != null && request.getParameter("add").equals("list") ) {
				
				UtentiAutenticati utenti=(UtentiAutenticati)this.getServletContext().getAttribute("UtentiAutenticati"); 
				listUtenti+="Username \tLoggato\n ";
				
				for(Utente u: utenti.getUtenti()) {
					listUtenti+=u.getUsername()+"\t"+u.isLogged()+"\n";
				}
			}
		%>
	</div>


</body>

</html>
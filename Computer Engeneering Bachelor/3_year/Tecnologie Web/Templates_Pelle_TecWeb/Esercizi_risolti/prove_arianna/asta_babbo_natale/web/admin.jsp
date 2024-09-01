<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<!-- accesso alla sessione -->
<%@ page session="true"%>

<!-- import di classi Java -->
<%@ page import="beans.*"%>
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
			Asta asta = (Asta)getServletContext().getAttribute("asta");
		
			String daeliminare = request.getParameter("daeliminare");
			
			if(daeliminare != null) {
				for(int i=0; i<asta.getRegaliDavendere().size(); i++) 
				{
					if(asta.getRegaliDavendere().get(i).getNome().equals(daeliminare)) {
						asta.getRegaliDavendere().remove(i);
						getServletContext().getRequestDispatcher("/S1").forward(request, response);
					}
				}
			}
		
			String listaRegali = "";
			
			for(int i=0; i<asta.getRegaliDavendere().size(); i++) {
				listaRegali += asta.getRegaliDavendere().get(i).toString();
			}
			
		%>
		<p>A seguire trovi la lista dei regali</p><br/>
		<textarea id="listaRegali" rows="30" cols="55" style="border: 1px solid black;" readonly ><%=listaRegali%></textarea>
		
		<br/><br/>
		
		<form action="#">
			<p>Se vuoi eliminare un regalo, specifica il nome:  </p> 
			<input type="text" id="daeliminare" name="daeliminare" required>
		</form>
	</div>


</body>

</html>
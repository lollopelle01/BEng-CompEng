<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<%@ page session="true"%>
<%@ page import="beans.Utente"%>
<%@ page import="beans.DBUtenti"%>

<%
	Utente u = (Utente) session.getAttribute("utente");
	if( (u.isSessioneAttiva() != true) || (!u.getNome().equals("admin"))){
%>
<html>
	<head>
		<title>mainAdmin</title>
		<meta http-equiv="Refresh" content= "5; URL=login.jsp"/>
		<link rel="stylesheet" href="styles/default.css" type="text/css"></link>
	</head>
	<body>
       	<h1>NON SEI UN ADMIN</h1>
       	<p>A breve verrai reindirizzato alla pagina che ti spetta :)</p>
		<img alt="wait" title="wait" src="images/wait.gif"/>
	</body>
</html>
<%
	} 
	else 
	{
%>

<html>
	<head>
		<title>mainAdmin</title>
		<link rel="stylesheet" href="styles/default.css" type="text/css"></link>
		<script type="text/javascript" src="scripts/json.js"></script>
		<script type="text/javascript" src="scripts/my_functions.js"></script>
		
	</head>
	<body>
       	
       	<%
       	DBUtenti dbUtenti = (DBUtenti) application.getAttribute("DBUtenti");
       	for (Utente item : dbUtenti.getUtentiAttivi()){
       		String username = item.getNome();
       		boolean sessioneAttiva = item.isSessioneAttiva();
       		String sessioneAttivaStringa = "false";
       		if(sessioneAttiva==true){
       			sessioneAttivaStringa = "true";
       		}
       		%>
       		<p>Username: <%= username %></p>
       		<p>SessioneAttiva: <%= sessioneAttivaStringa %></p>
       	<%
       	}
		%>
		<fieldset><legend>Termina Sessione</legend>
		<form name="terminaSessione" action="<%=request.getContextPath()%>/terminaSessioneServlet" method="post"> <!-- !!!CAMBIA ACTION CON L'URL DELLA SERVLET -->
			<p>Termina la sessione del seguente utente:</p>
			<label for="username">Username</label> 
			<input type="text" name="username" placeholder="username" required> <br /> 
			<input type="submit" value="termina">
		</form>
		</fieldset>
		
	</body>
</html>
<%
	}
%>
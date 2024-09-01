<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<%@ page session="true"%>
<%@ page import="beans.Utente"%>

<%
	Utente u = (Utente) session.getAttribute("utente");
	if( (u.isSessioneAttiva() != true) || (u.getNome()=="admin")){
%>
<html>
	<head>
		<title>mainUser</title>
		<meta http-equiv="Refresh" content= "3; URL=login.jsp"/>
		<link rel="stylesheet" href="styles/default.css" type="text/css"></link>
	</head>
	<body>
       	<h1>NON SEI UN USER</h1>
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
		<title>mainUser</title>
		<link rel="stylesheet" href="styles/default.css" type="text/css"></link>
		<script type="text/javascript" src="scripts/json.js"></script>
		<script type="text/javascript" src="scripts/my_functions.js"></script>
	</head>
	<body>
       	
		<fieldset><legend>Tombola</legend>
		
		<input type="button" name="gioca" value="gioca" onclick="gioca()"> <br />
		<input type="button" name="cincquina" value="cinquina" onclick="cinquina()"> <br />
		<input type="button" name="decina" value="decina" onclick="decina()"> <br /> 
		<input type="button" name="tombola" value="tombola" onclick="tombola()"> <br />
		<input type="button" name="abbandona" value="abbandona" onclick="abbandona()"> <br />
		
		<textArea id="risultato"></textArea>  <!-- tutti i messaggi vengono visualizzati qui -->
		</fieldset>
		
	</body>
</html>
<%
	}
%>
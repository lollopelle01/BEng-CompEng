<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<%@ page session="true"%>
<%@ page import="beans.Utente"%>

<html>
	<head>
		<title>User</title>
		<link rel="stylesheet" href="styles/default.css" type="text/css"></link>
		<script type="text/javascript" src="scripts/json.js"></script>
		<script type="text/javascript" src="scripts/clientWS.js"></script>
	</head>
	<body>

		<fieldset><legend>Iscrizione al servizio di notifica automatica</legend>
		<input type="button" name="iscrivimi" value="iscrivimi" onclick="iscrizione()"> <br />
		
		<input type="text" id="iscritto" name="iscritto" readonly>
		
		<textArea id="aggiornamenti"></textArea>  
		</fieldset>
		
	</body>
</html>
<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<!-- accesso alla sessione -->
<%@ page session="true"%>

<!-- import di classi Java -->
<%@ page import="java.util.*"%>

<!-- codice html restituito al client -->
<!DOCTYPE HTML PUBLIC>
<html>
	<head>
	
		<meta http-equiv="Pragma" content="no-cache"/>
		<meta http-equiv="Expires" content="-1"/>
		
		<!-- script necessari: -->
   		<script type="text/javascript" src="scripts/utils.js"></script>
		<script type="text/javascript" src="scripts/text.js"></script>

		<title>Elaborazione testo</title>
	</head>

	<body>
		Inserisci il testo da elaborare [100-1000]:
		<textarea id="myText" name="myText" rows="50" cols="50" maxlength="1000" required onKeyUp="checkText();"></textarea><br><br>
		
		<div id="risultato">
   		</div>
		
	</body>
</html>

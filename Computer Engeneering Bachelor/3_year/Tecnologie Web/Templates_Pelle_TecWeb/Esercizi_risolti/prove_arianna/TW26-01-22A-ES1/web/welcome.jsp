<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<!-- accesso alla sessione -->
<%@ page session="true"%>

<!-- import di classi Java -->
<%@ page import="java.util.*"%>
<%@ page import="com.google.gson.Gson" %>

<!-- codice html restituito al client -->
<!DOCTYPE HTML PUBLIC>
<html>
	<head>
		
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<link type="text/css" href="<%=request.getContextPath()%>/styles/default.css" rel="stylesheet"></link>
		<meta http-equiv="Pragma" content="no-cache"/>
		<meta http-equiv="Expires" content="-1"/>
		
		<!-- script necessari: -->
   		<script type="text/javascript" src="scripts/utils.js"></script>
		<script type="text/javascript" src="scripts/file.js"></script>

		<title>Welcome page</title>
	</head>

	<body>
	
		<h1>Welcome page</h1>
	
		<label for="nomeFile">Filename:</label><br>
	    <input type="text" id="fileName" name="fileName" onKeyUp="checkName();">
		
		<div>
			<p>Il risultato del conteggio &egrave;: </p><input type="text" id="conteggio" readonly>
			<textarea id="fileChanged" name="fileChanged" rows="50" cols="50"></textarea>
		</div>
		
	</body>
</html>

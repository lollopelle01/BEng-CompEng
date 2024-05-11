<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<%@ page session="true"%>
<%@ page import="beans.Utente"%>

<html>
	<head>
		<title>Chat multi-utente</title>
		<link rel="stylesheet" href="styles/default.css" type="text/css"></link>
		<script type="text/javascript" src="scripts/json.js"></script>
		<script type="text/javascript" src="scripts/clientWS.js"></script>
	</head>
	<body>
       	
		<fieldset><legend>Connettiti alla chat</legend>
			<input type="button" id="connect" value="connect me" onClick="connect();"><br>
		</fieldset>
		
		<div id="divChat" style="display:none" >
		
			<h5>Chat di gruppo</h5>
			<textarea id="chat" ></textarea>
			
			<hr>
			<p>Scrivi un messaggio sulla chat e premi invio</p>
			<br/>
				<input type="text" id="msg" name="msg" >
				<input type="button" id="send" value="send" onClick="sendMsg();" ><br>
	
		</div>
		
	</body>
</html>

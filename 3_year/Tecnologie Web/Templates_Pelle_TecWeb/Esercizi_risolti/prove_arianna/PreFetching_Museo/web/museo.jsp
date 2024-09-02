<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<!-- accesso alla sessione -->
<%@ page session="true"%>

<!-- import di classi Java -->
<%@ page import="java.util.*"%>
<%@ page import="com.google.gson.Gson" %>

<html>
   <head>
		
      <title>Museo</title>     
	  <link type="text/css" href="styles/default.css" rel="stylesheet"></link>
	  
	  <!-- script necessari: -->
   		<script type="text/javascript" src="scripts/utils.js"></script>
		<script type="text/javascript" src="scripts/museo.js"></script>
		
   </head>
<body>

	<h3>Inserisci la tua posizione corrente</h3>
	
	<label for="stanza">Stanza</label>
	<input type="text" id="stanza" name="stanza" size="2" required onKeyup="checkStanza();" > <br />
	
	<fieldset>
		<label for="result" >Opere della stanza attuale:</label>
		<div id="result"></div>
	</fieldset>
	
</body>
</html>


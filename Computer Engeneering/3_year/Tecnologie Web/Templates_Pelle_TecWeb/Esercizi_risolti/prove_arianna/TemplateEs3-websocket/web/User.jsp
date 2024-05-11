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
		<title>User</title>
		<link rel="stylesheet" href="styles/default.css" type="text/css"></link>
		<script type="text/javascript" src="scripts/json.js"></script>
		<script type="text/javascript" src="scripts/ws_basic.js"></script>
	</head>
	<body>
       	
       	<!-- es.1 -->
		<fieldset><legend>Tombola</legend>
		
		<input type="button" name="gioca" value="gioca" onclick="gioca()"> <br />
		<input type="button" name="cincquina" value="cinquina" onclick="cinquina()"> <br />
		<input type="button" name="decina" value="decina" onclick="decina()"> <br /> 
		<input type="button" name="tombola" value="tombola" onclick="tombola()"> <br />
		<input type="button" name="abbandona" value="abbandona" onclick="abbandona()"> <br />
		
		<textArea id="risultato"></textArea>  <!-- tutti i messaggi vengono visualizzati qui -->
		</fieldset>
		
		
		<!-- es.2 -->
		<div>User: <input type="text" id="user" size="10"></div>
    	<div id="calcolatrice">
    		Operando 1: <input type="text" id="op1" size="3" onkeyup="myUpdate('op1');"><br>
    		Operando 2: <input type="text" id="op2" size="3" onkeyup="myUpdate('op2');"><br>
    		<p>Please select operator:</p>
  			<input type="radio" id="add" name="op" value="add"><label for="add">+</label><br>
  			<input type="radio" id="sott" name="op" value="sott"><label for="sott">-</label><br>  
  			<input type="radio" id="mol" name="op" value="mol"><label for="mol">*</label><br>
  			<input type="radio" id="div" name="op" value="div"><label for="div">/</label><br><br>
    		<input type="button" onclick="myFunction();" value="=">
    	</div>
    	<div>
    	 Risultato: <input id="risultato" type="text" size="5" readonly><br><br>
    	</div>
    	<div>
    	 Risultato Precedente: <input id="oldRis" type="text" size="5" readonly><br><br>
    	</div>
	
		
	</body>
</html>
<%
	}
%>
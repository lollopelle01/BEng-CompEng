<!-- STRUTTURA PAGINA HTML -->
<%@ page session="true"%>

<%@ page errorPage="../errors/failure.jsp"%>

<!-- import di classi Java -->
<%@ page import="java.util.*"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
    <head>
        <!-- per non avere problema di cache -> aggiungo nell'header delle pagine HTML -->
        <meta http-equiv="Pragma" content="no-cache"/>
        <meta http-equiv="Expires" content="-1"/>
	
		<script type="text/javascript" src="scripts/controllo.js"></script>
        <script type="text/javascript" src="scripts/utils.js"></script>
	
		<title> Pagina Login </title>
		
		</head>
    <body>
        <div>
        	<h5>Benvenuto, inserisci un testo:</h5>
        	<table>
       			<tr>
       				<td><textarea cols="40" rows="5" id="text_area" name="testo" onKeyUp="myfunction()" placeholder="Inserire un testo di lunghezza compresa fra 100 e 1000 caratteri"></textarea></td>
       				<td><input type="submit" name="send" id="send" value="Invia" style="display: none" onClick="send()"/></td>
       			</tr>
        	</table>
        </div>
        <div id="send" style="display: none">
        	<form id="toServlet" action="servletS1" method="POST">
        		<input type="text" id="formatoJSON" name="json"/>
	        </form>
        </div>
	</body>
</html>



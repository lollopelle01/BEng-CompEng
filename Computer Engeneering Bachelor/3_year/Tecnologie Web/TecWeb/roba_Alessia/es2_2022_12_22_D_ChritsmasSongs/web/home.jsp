<%@ page session="true"%>
<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<!-- codice html restituito al client -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
        <!-- per non avere problema di cache -> aggiungo nell'header delle pagine HTML -->
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="Pragma" content="no-cache"/>
        <meta http-equiv="Expires" content="-1"/>

        <title> Start </title>

		<link rel="stylesheet" href="./styles/default.css" type="text/css" />

		<script type="text/javascript" src="scripts/utils.js"></script>
        <script type="text/javascript" src="scripts/canzoni.js"></script>

	</head>
    <body>
        <h3> Benvenuto, inserisci un numero tra 3 e 4 </h3>
        <form name="Login" action="servletLogin" method="POST">
        	<table>
				<tr>
					<td> <input type="number" id="numero" name="numero" size="30" onKeyUp="myfunction()" placeholder="Numero"></td>
				</tr>
				<tr>
					<td> <input type="submit" style="display: none"></td>
				</tr>
			</table>
        </form>
		<br>
		<br><hr><br>
		<div id="res">
        </div>
    </body>
</html>



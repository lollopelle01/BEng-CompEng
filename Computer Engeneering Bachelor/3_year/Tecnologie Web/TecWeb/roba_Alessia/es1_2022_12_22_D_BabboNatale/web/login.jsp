<!-- STRUTTURA PAGINA HTML -->
<%@ page session="true"%>

<%@ page errorPage="../errors/failure.jsp"%>

<!-- import di classi Java -->

<%@ page import="java.util.*"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<!-- per non avere problema di cache -> aggiungo nell'header delle pagine HTML -->
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="-1" />

<title>Pagina Login</title>
<script>
	function attiva() {
		var res = document.getElementById("attendiamo");
		res.style.display = 'block';
	}
</script>
</head>
<body>
	<h3>Benvenuto, inserisci le tue credenziali da admin o fai il
		Login</h3>
	<form name="Login" action="servletLogin" method="POST">
		<table>
			<tr>
				<td><input type="text" name="username" size="30"
					placeholder="Username"></td>
			</tr>
			<tr>
				<td><input type="text" name="password" size="30"
					placeholder="Password"></td>
			</tr>
			<tr>
				<td><input type="submit" name="Login" value="LogIn" size="30"
					onClick="attiva"></td>
			</tr>
		</table>
	</form>
	<div>
		<%
		/* dopo aver inviato una richiesta post alla servlet, verifico se ho un errore
		      		 * nella registrazione o nell'acceso.
		      		 * nel caos in cui ce l'avessi, stampo nel div il mex di errore */

		String err = (String) session.getAttribute("err");

		//se ho errori
		if (err != null) {

			int codiceErr = Integer.parseInt(err);

			//stampo il tipo di errore settato nella servlet che ha gestito il form
			switch (codiceErr) {
			case 1: {
		%>
		<p>
			<b> Errore: i campi inseriti non sono validi</b>
		</p>
		<%
		}
		break;
		}
		} else {
		%>
		<p id="attendiamo" style="display: none">
			<b> Attendiamo che si colleghino altri utenti</b>
		</p>
		<%
		}
		%>
	</div>
</body>
</html>



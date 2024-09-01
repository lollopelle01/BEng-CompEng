<!-- STRUTTURA PAGINA HTML -->
<%@ page session="true"%>

<%@ page errorPage="../errors/failure.jsp"%>

<!-- import di classi Java -->

<%@ page import="java.util.*"%>
<%@ page import="Beans.*"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
	<!-- per non avere problema di cache -> aggiungo nell'header delle pagine HTML -->
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="-1" />
	
	<title>Asta</title>
	
	<script>
		function offri() {
			var prezzo = document.getElementById("price").value;
			var continente = document.getElementById("continente").value;
			var send = new Object();
			send.offerta = prezzo;
			send.continente = continente;
			var msg = JSON.stringify(send);
			document.getElementById("offerta").value = msg;
			document.getElementById("send").submit();
		}
	</script>
	
	
	</head>
	<body>
		<%
		Utente utente = (Utente) session.getAttribute("utente");
	
		Asta asta = (Asta) this.getServletContext().getAttribute("asta");
		%>
		<div id="InfoUtenti">
			Utenti collegati:
			<table>
				<%
				for (Utente u : asta.getUsers()) {
				%>
				<tr>
					<td><%=u.getUsername()%>
					<td>
					<td>denari disponibili: <%=u.getDenari()%></td>
				</tr>
				<%
				}
				%>
			</table>
		</div>
		<div id="regalo">
			<%
			Random r = new Random();
			int numR = r.nextInt(5) + 1;
	
			//pesco il regalo
			Regalo currentRegalo = asta.getCurrentRegalo();
			if (currentRegalo == null) {
				currentRegalo = asta.getRegali().get(numR);
				while (currentRegalo.isAcquistato()) {
					currentRegalo = asta.getRegali().get(numR);
				}
				asta.setCurrentRegalo(currentRegalo);
			}
	
			if (!currentRegalo.isAcquistato()) {
			%>
			<p>
				In questo momento mettiamo all'asta il regalo "<%=currentRegalo.getNome()%>",
				iniziando da una base d'asta di
				<%=currentRegalo.getPrezzo()%>
			</p>
	
			<div id="infoRegalo">
				<table>
					<tr>
						<td>Descrizione</td>
						<td>Base d'asta</td>
						<td>Continente per la spedizione</td>
					</tr>
					<tr>
						<td><%=currentRegalo.getDescr()%></td>
						<td><%=currentRegalo.getPrezzo()%></td>
						<td><%=currentRegalo.getContinente()%></td>
					</tr>
				</table>
				<br>
				<form action="servletOfferta" method="POST">
					<table>
						<tr>
							<td><input type="text" name="offerta"
								placeholder="Inserisci offerta" /></td>
							<td><input type="text" name="continente"
								placeholder="Inserisci continente per la spedizione" /></td>
							<td><input type="submit" value="Fai Offerta" /></td>
						</tr>
					</table>
				</form>
				<%
				}
				%>
			</div>
			<hr>
			<div id="regaliVenduti">
				<%
				if (!asta.getRegaliVenduti().isEmpty()) {
				%>
				<table>
					<tr>
						<td>Regalo venduto</td>
						<td>Utente</td>
						<td>Descrizione</td>
						<td>Prezzo venduto</td>
					</tr>
					<%
					for (Regalo reg : asta.getRegaliVenduti()) {
					%>
					<tr>
						<td><%=reg.getNome()%></td>
						<td><%=reg.getOffertaVinc().getUtente()%></td>
						<td><%=reg.getDescr()%></td>
						<td><%=reg.getOffertaVinc().getOfferta()%></td>
					</tr>
				</table>
				<%
				}
				}
				%>
			</div>
		</div>
	</body>
</html>



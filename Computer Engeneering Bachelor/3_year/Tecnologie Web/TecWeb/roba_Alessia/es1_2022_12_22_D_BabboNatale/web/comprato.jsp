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
        <meta http-equiv="Pragma" content="no-cache"/>
        <meta http-equiv="Expires" content="-1"/>
	
		<title> Asta </title>
		</head>
    <body>
    	<%
			Asta asta = (Asta) this.getServletContext().getAttribute("asta");	
		%>
		<div>
			<h3>Regalo per cui sono iniziate le offerte: <%=asta.getCurrentRegalo().getNome()%></h3>
			<table>
				<tr>				
					<td>Utenti che partecipano all'offerta</td>
					<td>Offerta utente</td>
				</tr>
				<%
					for(Offerta off : asta.getOfferte()) {
				%>		
					<tr>
						<td><%=off.getUtente()%></td>
						<td><%=off.getOfferta()%></td>
					</tr>
				<%		
					}
				%>	
			</table>
		</div>
        <div id="esito">
			<%
				int e = Integer.parseInt(session.getAttribute("esitoOfferta").toString());
				System.out.println(e);
				if(e > 0) {
					switch(e) {
					
						case 1: {
			%>
							<p><b>Err: Hai inserito un continente diverso da quello del regalo!</b></p>
			<%
							break;
						}
						case 2: {
			%>
							<p><b>Err: Possiedi meno denari di quelli che hai inserito nella tua offerta!</b></p>
			<%
							break;
						}
						case 3: {
			%>
							<p><b>Err: La base d'asta è più alta della tua offerta!</b></p>
			<%
							break;
						}
						case 4: {
			%>
							<p><b>Il regalo è stato venduto a <%=asta.getCurrentRegalo().getOffertaVinc().getUtente()%> al prezzo di <%=asta.getCurrentRegalo().getOffertaVinc().getOfferta()%></b></p>
			<%
							break;
						}
					}
				}
     		%>
     		<h4><a href="./asta.jsp">TORNA ALL'ASTA</a></h4>   
     	</div>
	</body>
</html>



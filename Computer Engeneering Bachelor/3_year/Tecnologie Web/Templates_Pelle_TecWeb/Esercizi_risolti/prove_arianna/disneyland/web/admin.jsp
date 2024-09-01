<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<!-- accesso alla sessione -->
<%@ page session="true"%>

<!-- import di classi Java -->
<%@ page import="it.unibo.tw.web.beans.Utente"%>
<%@ page import="it.unibo.tw.web.beans.GruppoComitiva"%>
<%@ page import="java.util.*"%>

<!-- metodi richiamati nel seguito -->
<%!
// METODI PURAMENTE IN JAVA IN CUI PASSO EVENTUALMENTE GLI OGGETTI BEAN CREATI
%>

<html>
   <head>
      <title>Disneyland Jsp</title>
		<link type="text/css" href="styles/default.css" rel="stylesheet"></link>
   </head>
   <body>
   
   		<table>
			<tr>
				<th style="width: 25%">Group id</th>
				<th style="width: 25%">Number of Tickets</th>
				<th style="width: 25%">Finalize</th>
				<th style="width: 25%">Reset</th>
			</tr>
	<%
		Map<String, GruppoComitiva> gruppi = (Map<String, GruppoComitiva>)this.getServletContext().getAttribute("gruppi");
		for(String gid : gruppi.keySet())
		{
			if(gruppi.get(gid).getBiglietti() > 0) {
			%>
			<tr>
				<form action="Finalize" method="get">
						
						<td><%= gid %></td>
						<td><%= gruppi.get(gid).getBiglietti() %> </td>
						
						<td>
						<input type="hidden" name="gid" value="<%=gid%>"/>
						<input type="submit" name="richiesta" value="completamento"/></td>
						<td>
							<input type="hidden" name="gid" value="<%=gid%>"/>
							<input type="submit" name="richiesta" value="annullamento"/>
						</td>
						
				</form>
			
			</tr>
			
			<% }
		}
		%>
		</table>
	
   </body>
</html>

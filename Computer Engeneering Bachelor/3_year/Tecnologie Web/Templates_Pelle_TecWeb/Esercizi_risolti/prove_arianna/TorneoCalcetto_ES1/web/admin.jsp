<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<!-- accesso alla sessione -->
<%@ page session="true"%>

<!-- import di classi Java -->
<%@ page import="beans.Socio"%>
<%@ page import="beans.Circolo"%>
<%@ page import="beans.Torneo"%>
<%@ page import="beans.Socio"%>
<%@ page import="beans.Risultato"%>
<%@ page import="java.util.*"%>

<!-- codice html restituito al client -->
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Admin page</title>
	</head>
<body>

	<h1>Admin page</h1>
	<div>
		<%
			String iscritti = "";
			Circolo circolo=(Circolo)this.getServletContext().getAttribute("circolo"); 
			
			for(Socio u: circolo.getSoci()) {
				if(u.getSquadra() != -1)
					iscritti+=u.getUsername()+"\t squadra:"+u.getSquadra()+"\n";
			}
			
			String ris = "";
			Risultato[] risultati = (Risultato[])this.getServletContext().getAttribute("risultati");
			for(int i=0; i<risultati.length; i++) {
				if(risultati[i] != null)
					ris += i+"° risultato: semifinale="+risultati[i].getSemifinale()+"\t vincitore="
							+risultati[i].getVincitore()+"\t punteggio="+risultati[i].getRisultato()+"\n";
			}
			
			
		%>
		<label for="listaIscritti">Iscritti:</label>
		<textarea id="listaIscritti" rows="30" cols="55" style="border: 1px solid black;" readonly ><%=iscritti%></textarea>
		
		<label for="Risultati">Risultati:</label>
		<textarea id="Risultati" rows="30" cols="55" style="border: 1px solid black;" readonly ><%=ris%></textarea>
	</div>


</body>

</html>
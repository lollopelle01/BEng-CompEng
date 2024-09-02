<!-- STRUTTURA PAGINA HTML -->
<%@ page session="true"%>

<%@ page errorPage="../errors/failure.jsp"%>

<!-- import di classi Java -->

<%@ page import="java.util.*"%>
<%@ page import="Beans.Tavolo" %>
<%@ page import="Beans.Utente" %>
<%@ page import="Beans.Drink" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
        <!-- per non avere problema di cache -> aggiungo nell'header delle pagine HTML -->
        <meta http-equiv="Pragma" content="no-cache"/>
        <meta http-equiv="Expires" content="-1"/>
	
		<title> Pagina Login </title>
	
		</head>
    <body>
    
    	<%
    		Tavolo currentTable = (Tavolo) session.getAttribute("currentTable");
    		Utente utente = (Utente) session.getAttribute("success");
    		
    	%>
    
    	<h2>Ci dispiace <%=utente.getUsername()%> ma il tuo tavolo è stato chiuso dall'amministratore.</h2>
    	<h4>Totale drink (consegnati) al tavolo <%=currentTable.getId()%>: <%=currentTable.getTot()%></h4>
    </body>
</html>
<!-- STRUTTURA PAGINA HTML -->
<%@ page session="true"%>

<%@ page errorPage="../errors/failure.jsp"%>

<!-- import di classi Java -->

<%@ page import="java.util.*"%>
<%@ page import="Beans.Utente"%>
<%@ page import="Beans.Tavolo"%>
<%@ page import="Beans.Drink"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
        <!-- per non avere problema di cache -> aggiungo nell'header delle pagine HTML -->
        <meta http-equiv="Pragma" content="no-cache"/>
        <meta http-equiv="Expires" content="-1"/>
	
		<title> Pagina Admin </title>
	
		</head>
    <body>
    
    	<%
    	
    		Map<String, Tavolo> totTavoli = (Map<String, Tavolo>) this.getServletContext().getAttribute("tavoli");
    	
    		if (request.getParameter("chiusura") != null && request.getParameter("chiusura").equals("Chiudi")) {
    			String tav = (String) request.getParameter("tavolo");
    			
    			Tavolo tavolo = totTavoli.get(tav);
    			
    			if(tavolo.getTotTableCons() < 100.00) {
    				tavolo.setTotTable(100.00);
    			}
    			
    			tavolo.setStatus(true);

			} 		
    	%>
    
    
    	<h3>Benvenuto admin! Gestisci la tua discoteca.</h3>
    	
    		<table>
    			<tr>
    				<td>Id Tavolo</td>
    				<td>Numero Utenti</td>
    				<td>Numero Drink ordinati</td>
    				<td>Numero Drink consegnati</td>
    				<td>Totale drink consegnati</td>
    				<td>Stato del Tavolo</td>
    			</tr>
    			
    	<%
    		for(Tavolo tavolo : totTavoli.values()) {	
    			
    			int drinkOrd = 0;
    			int drinkCons = 0;
    			
    			for(Utente ut : tavolo.getUsers()) {
    				
    				drinkOrd += ut.getNumTotOrd();
    				drinkCons += ut.getnumTotCons();
    				
    			}
    	%>
    			<tr>
    				<td><%=tavolo.getId()%></td>
    				<td><%=tavolo.getUsers().size()%></td>
    				<td><%=drinkOrd%></td>
    				<td><%=drinkCons%></td>
    				<td><%=tavolo.getTot()%></td>
    				<td><%=tavolo.isStatus()%></td>
    			</tr>		
    	<%		
    		}
    	%>
    		</table>
    	<br><hr><br>
    	<p>Chiusura: </p>

    		<%
	    		for(Tavolo tavolo : totTavoli.values()) {	
	    	%>
	    		<form>
		    		<table>
		    			<tr>
	    					<td><label for="tavolo">Tavolo:</label></td>
	    					<td><input type="text" name="tavolo" value="<%=tavolo.getId()%>" readonly/></td>
	    					<td><input type="submit" name="chiusura" value="Chiudi"></td>
	    				</tr>
		    		</table>
	    		</form>	
	    	<%		
	    		}
	    	%>
    </body>
</html>
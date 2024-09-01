<!-- STRUTTURA PAGINA HTML -->
<%@ page session="true"%>

<%@ page errorPage="./errors/failure.jsp"%>

<!-- import di classi Java -->

<%@ page import="java.util.*"%>
<%@ page import="java.util.Arrays"%>
<%@ page import="Beans.Tavolo" %>
<%@ page import="Beans.Utente" %>
<%@ page import="Beans.Drink" %>


<%!

void addOrd(Utente utente, int quantity, double price) {
	utente.addNumTotOrd(quantity);
	Drink drink = new Drink(quantity, price);
	utente.addDrinkOrd(drink);
}

void addCons(Tavolo tavolo) {
	
	for(Utente utente : tavolo.getUsers()) {
		int numOrd = utente.getNumTotOrd();
		
		utente.addNumTotCons(numOrd);	//aggiungiamo utente numero consegnati
		
		//aggiungiamo i drink consegnati
		
		List<Drink> consegnati = utente.getConsegnati();
		
		for(Drink drinkOrd : utente.getOrdinati()) {
			consegnati.add(drinkOrd);
		}
		
		utente.getOrdinati().clear();	//svuoto richiesti perchè ormai consegnati
	}
}

%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
        <!-- per non avere problema di cache -> aggiungo nell'header delle pagine HTML -->
        <meta http-equiv="Pragma" content="no-cache"/>
        <meta http-equiv="Expires" content="-1"/>
	
		<title> Discoteca </title>
	
		</head>
    <body>
    	
    	<%
    		Tavolo currentTable = (Tavolo) session.getAttribute("currentTable");
    		Utente utente = (Utente) session.getAttribute("success");
    		
    		if(currentTable.isStatus() == false) {
    		
	    		if (request.getParameter("add") != null && request.getParameter("add").equals("Ordina")) {
					
	    			int quantity = Integer.parseInt(request.getParameter("quantity"));
	    			double price = Double.parseDouble(request.getParameter("price"));
	    			addOrd(utente, quantity, price);
				}
	    		
	    		else if (request.getParameter("cons") != null && request.getParameter("cons").equals("Consegna")) {
	    			addCons(currentTable);
	    		}
    		} else {
    			getServletContext().getRequestDispatcher("/close.jsp").forward(request, response);
    			return;
    		}
			
    	%>
    
    	<h5>Benvenuto <%=utente.getUsername()%> al tavolo <%=currentTable.getId()%></h5>
    
    	<div id="ordini">
    		<p>Vuoi aggiungere un drink?</p>
			<form>
				<table>
					<tr><td>
						<label for="quantity">Quantity:</label>
					</td><td>
						<input type="text" name="quantity"/>
					</td></tr>
					<tr><td>
						<label for="price">Price (&#8364;):</label>
					</td><td>
						<input type="text" name="price" value="5"/>
					</td></tr>
					<tr><td colspan="2">
						<input type="submit" name="add" value="Ordina" style="width:100%"/>
					</td></tr>
				</table>
			</form>
    	</div>
    	<br><hr>
    	<div id="tavolo">
    		<p>Situazione al tavolo <%=currentTable.getId()%>:</p>
			<table>
				<tr style="border: 1px solid gray">
					<td style="width: 10%">Utente</td>
					<td style="width: 20%">Numero drink ordinati</td>
					<td style="width: 20%">Numero drink consegnati</td>
					<td style="width: 20%">Totale drink ordinati</td>
					<td style="width: 20%">Totale drink consegnati</td>
				</tr>
			<%
				for(Utente ut : currentTable.getUsers()) {
			%>
				<tr>
					<td style="width: 10%"><%=ut.getUsername()%></td>
					<td style="width: 20%"><%=ut.getNumTotOrd()%></td>
					<td style="width: 20%"><%=ut.getnumTotCons()%></td>
					<td style="width: 20%"><%=ut.getTotOrd()%></td>
					<td style="width: 20%"><%=ut.getTotCons()%></td>
				</tr>
			<%
				}
			%>
			</table>
			<br>
			<p>Saldo al tavolo: <%=currentTable.getTotTableCons()%></p>
    	</div>
    	<br><hr><br>
    	<div id="cameriere">
    		<form>
	    		<table>
	    			<tr>
	    				<td>Cameriere, vuoi consegnare i drink?</td>
	    				<td><input type="submit" name="cons" value="Consegna" style="width:100%"/></td>
	    			</tr>
	    		</table>
    		</form>
    	</div>
    </body>
</html>
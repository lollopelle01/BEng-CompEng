<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<!-- accesso alla sessione -->
<%@ page session="true"%>

<!-- import di classi Java -->
<%@ page import="beans.*"%>

<%
Utente utente = (Utente) request.getSession().getAttribute("utente");
		if (utente == null) {
			request.setAttribute("loginerror", "Non sei autenticato!");
			%>
			<jsp:forward page="/login.jsp"></jsp:forward>
			<%
		}
%>

<!-- codice html restituito al client -->
<html>
	<head>
		<title>Compra JSP</title>
		<link rel="stylesheet" href="<%= request.getContextPath() %>/styles/default.css" type="text/css"/>
	</head>

	<body>	

		<%@ include file="/fragments/header.html" %>
	
		<div id="main" class="clear">

			<jsp:useBean id="catalogo" class="beans.Catalogo" scope="application" />
			
<%

			if ( request.getParameter("add") != null && request.getParameter("add").equals("add") ) {
				String id = request.getParameter("id");
				if (id != null) {
					try {
						int quantita = Integer.parseInt(request.getParameter("quantita"));
						if (quantita > catalogo.getById(id).getNumDisponibili()) {
							%>
							<h4>Non ci sono abbastanza oggetti disponibili...</h4>
							<%
						} else
							utente.aggiungiAlCarrello(id, quantita);
					} catch (Exception e) {
						%>
						<h4>Qualcosa e' andato storto...</h4>
						<%
					}
				}
			}
			if ( request.getParameter("empty") != null && request.getParameter("empty").equals("ok") ) {
				utente.svuotaCarello();
			}

%>			
			
			<div id="left" style="float: left; width: 48%; border-right: 1px solid grey">
			
				<p>Select an item from the catalogue:</p>
				<table class="formdata">
					<tr>
						<th style="width: 14%">ID</th>
						<th style="width: 14%">Name</th>
						<th style="width: 14%">Description</th>
						<th style="width: 14%">Available Qty</th>
						<th style="width: 14%">Price</th>
						<th style="width: 14%">Your order</th>
						<th style="width: 14%"></th>
					</tr>
					<% 
					
					for( Prodotto aCatalogueItem : catalogo.getProdotti() ){  
					%> 
						<form>
							<tr>
								<td><%= aCatalogueItem.getId() %></td>
								<td><%= aCatalogueItem.getNome() %></td>
								<td><%= aCatalogueItem.getDescrizione() %></td>
								<td><%= aCatalogueItem.getNumDisponibili() %></td>
								<td><%= utente.getSessioneCorrente().get(aCatalogueItem.getId()) %> &#8364;</td>
								<td><input type="text" name="quantita" style="background-color: #c3c3d7;"/></td>
								<td>
									<input type="text" name="id" hidden="hidden" value="<%=aCatalogueItem.getId() %>"/>
									<input type="submit" name="add" value="add"/>
								</td>
							</tr>
						</form>
					<% } %>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
				</table>			
			</div>
			
			<div id="right" style="float: right; width: 48%">
				<p>Cart content:</p>
				<table class="formdata">
					<tr>
						<th style="width: 20%">ID</th>
						<th style="width: 20%">Name</th>
						<th style="width: 20%">Description</th>
						<th style="width: 20%">Price</th>
						<th style="width: 20%">Your order</th>
					</tr>
					<% 
					for( String id : utente.getCarrello().keySet() ){  
					%> 
						<tr>
							<td><%= id %></td>
							<td><%= catalogo.getById(id).getNome() %></td>
							<td><%= catalogo.getById(id).getDescrizione() %></td>
							<td><%= utente.getSessioneCorrente().get(id) %></td>
							<td><%= utente.getCarrello().get(id) %></td>
						</tr>
					<% 
					} 
					%>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
				</table>			
				<br/>
				<p>
				Total: <span style="font-size: x-large; color: red;"><%= utente.getTotale() %> &#8364;</span>
				</p>
				<a href="./checkout.jsp" class="button">Vai al checkout</a>
				<%
				if ( utente.getCarrello().size() > 0 ) {
				%>
					<br/>
					<a href="?empty=ok">Remove all items from the cart</a>
				<%
				}
				%>
			</div>
		
			<div class="clear">
				<p>&nbsp;</p>
			</div>
			
		</div>
	
		<%@ include file="/fragments/footer.html" %>

	</body>
</html>

<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<!-- accesso alla sessione -->
<%@ page session="true"%>

<!-- import di classi Java -->
<%@ page import="beans.*"%>
<%@ page import="java.util.*"%>

<%
Utente utente = (Utente) request.getSession().getAttribute("utente");
		if (utente == null) {
			request.setAttribute("loginerror", "Non sei autenticato!");
			%>
			<jsp:forward page="/login.jsp"></jsp:forward>
			<%
		}
%>

<html>
<head>
<title>Checkout JSP</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/styles/default.css" type="text/css" />
</head>

<body>

<%@ include file="/fragments/header.html"%>
<jsp:useBean id="catalogo" class="beans.Catalogo" scope="application" />

<div id="main" class="clear">

Items in cart:<br />


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
<br />

<a href="./finalizzaServlet" class="button">Finalizza</a>
<br/>
<br/>
<%
	String message = (String) request.getAttribute("message");
	if (message != null && message.length() > 0) {
		%>
		<h4><%=message %></h4>
		<%
	}
%>

</div>
<%@ include file="/fragments/footer.html"%>

</body>
</html>

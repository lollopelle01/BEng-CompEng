<%@ page contentType="text/html; charset=US-ASCII" %>

<%-- uso della sessione --%>
<%@ page session="true"%>

<%-- import java --%>
<%@ page import="java.util.List, java.util.ArrayList"%>

<%-- pagina per la gestione di errori --%>
<%@ page errorPage="errors/failure.jsp"%>

<html>

<head>
	<meta name="Author" content="Federico Stella">
	<title>Start JSP</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/styles/default.css" type="text/css"/>
</head>

<jsp:useBean id="result" class="beans.Result" scope="request" />
<jsp:useBean id="precRes" class="beans.PrecResults" scope="session" />

<body>

	<%@ include file="fragments/header.html" %>
	
	<fieldset>
				<legend>Form</legend>
		<form action="<%= request.getContextPath() %>/dispatcherServlet">
			<label>Nome File: <input type="text" name="file" id="file" placeholder="prova.txt"></input></label>
			<label>Carattere: <input type="text" name="car" id="car" placeholder="c"></input></label>
			<br/>
			<button type="submit">Invia</button>
			<button type="reset">Reset</button>
		</form>
	</fieldset>

<%

	if (result.getFilename() != null) {
%>
		<h3>Risultato:</h3>
		<p><%= result.getCount() %></p>
<%
	}

	if (precRes.getResults().size() > 0) {
%>
		<h3>Risultati precedenti:</h3>
		<ul>
<%
		for (beans.Result r : precRes.getResults()) {
%>
			<li>File: <%= r.getFilename() %>, carattere: <%= r.getCar() %>, count: <%= r.getCount() %></li>
<%
		}

%>
		</ul>
<%
	}
	
	if (result.getFilename() != null) {
		precRes.addResult(result);
	}
%>

	<br/>
	<br/>
	<br/>
	<br/>
	
	<h2>Admin section</h2>
		<fieldset>
		<legend>Login</legend>
			<form action="<%= request.getContextPath() %>/loginServlet">
				<label for="username">Username: </label><input type="text" name="username" id="username" required/>
				<label for="password">Password: </label><input type="password" name="password" id="password" required/>
				<button type="submit">Login</button>
			</form>
		</fieldset>


	<%@ include file="fragments/footer.html" %>

</body>
</html>
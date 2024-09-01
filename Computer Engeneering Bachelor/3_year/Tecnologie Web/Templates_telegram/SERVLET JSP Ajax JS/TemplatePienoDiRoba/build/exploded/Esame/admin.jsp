<%@ page contentType="text/html; charset=US-ASCII" %>

<%-- uso della sessione --%>
<%@ page session="true"%>

<%-- import java --%>
<%@ page import="java.util.List, java.util.ArrayList"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>

<%-- pagina per la gestione di errori --%>
<%@ page errorPage="errors/failure.jsp"%>

<html>

<head>
	<meta name="Author" content="Federico Stella">
	<title>Start JSP</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/styles/default.css" type="text/css"/>
</head>

<jsp:useBean id="log" class="beans.OperationLog" scope="application" />
<jsp:useBean id="authorized" class="java.lang.String" scope="session" />

<body>

	<%@ include file="fragments/header.html" %>

	<a class="button" href="start.jsp">Home</a>

<%
if (authorized.equals("yes")) {
	
	if (log.getResults().size() > 0) {
		%>
		<h3>Risultati degli ultimi 30 minuti:</h3>
		<ul>
		<%
			DateFormat df = new SimpleDateFormat("dd-MM-YYYY HH:mm");
			for (beans.Result r : log.getResults()) {
			%>
			<li>File: <%= r.getFilename() %>, carattere: <%= r.getCar() %>, count: <%= r.getCount() %>, time: <%= df.format(r.getTime()) %></li>
			<%
			}

		%>
		</ul>
		<%
		}
	
}
else
{
	%>
	<h3>Non sei autenticato</h3>
	<%
}



%>

	<%@ include file="fragments/footer.html" %>


</body>
</html>
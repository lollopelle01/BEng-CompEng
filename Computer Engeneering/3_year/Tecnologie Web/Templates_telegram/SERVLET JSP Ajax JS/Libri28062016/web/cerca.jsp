<%@page import="sun.reflect.ReflectionFactory.GetReflectionFactoryAction"%>
<%@ page contentType="text/html; charset=US-ASCII" %>

<%-- uso della sessione --%>
<%@ page session="true"%>

<%-- import java --%>
<%@ page import="java.util.List, java.util.ArrayList"%>
<%@ page import="beans.*"%>

<%-- pagina per la gestione di errori --%>
<%@ page errorPage="errors/failure.jsp"%>

<%-- 
	/*oggetti built in*/
	//page --> info di una servlet
	//config --> getInitParamNames
	//request
	//response
	//out
	//SESSION --> getAttribute setAttribute
	//application (servletContext) --> var globali di application

	<jsp:forward page="url-dest">
	<jsp:param name="par1" value="val1"/>
	<jsp:param name="par2" value="val2"/>
	</jsp:forward>
	
	<jsp:include page="url-dest" flush="true">
	<jsp:param name="par1" value="val1"/>
	<jsp:param name="par2" value="val2"/>
	</jsp:include>
	
	<jsp:useBean id="nomeLogico" class="JavaBeans.aBean" scope="page|request|session|application"/>
	<jsp:getProperty property="nomeProp" name="nomeLogicoBean"/>
	<jsp:setProperty property="nomeProp" name="nomeLogicoBean" value="val"/>
	
 --%>

<html>

<head>
	<meta name="Author" content="Federico Stella">
	<title>Start JSP</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/styles/default.css" type="text/css"/>
	<script src="scripts/cerca.js" type="text/javascript"></script>
	<script src="scripts/utils.js" type="text/javascript"></script>
</head>

<jsp:useBean id="catalogo" class="beans.Catalogo" scope="application" />
<jsp:useBean id="authorization" class="java.lang.String" scope="session" />
<jsp:useBean id="risultatiPassati" class="beans.RisultatiPassati" scope="session" />

<body>

	<%@ include file="fragments/header.html" %>
	
	<fieldset>
		<legend>Form</legend>
		<form action="" onsubmit="return false">
			<label>nomeAutore_1: <input type="text" name="nomeAutore_1" id="nomeAutore_1" placeholder="nomeAutore_1" required></input></label>
			<label>nomeAutore_2: <input type="text" name="nomeAutore_2" id="nomeAutore_2" placeholder="nomeAutore_2" required></input></label>
			<label>nomeAutore_3: <input type="text" name="nomeAutore_3" id="nomeAutore_3" placeholder="nomeAutore_3" required></input></label>
			<button type="button" value="cerca" onclick="cerca(this.form, myGetElementById('resDiv'))">Cerca</button>
			<button type="reset" value="reset">Reset</button>
		</form>
	</fieldset>
		
	<div name="resDiv" id="resDiv"></div>
	
	<br/>
	<br/>
	<h4>Risultati Precedenti:</h4>
	<div name="passsati" id="passati">
	<%
	//Per gestire le 3 richieste precedenti utilizzo lo stato di sessione, tuttavia in questo modo
	//se il cliente non aggiorna la pagina dopo la richiesta AJAX non vedra' i risultati precedenti aggiornati.
	//Per cambiare questo comportamento si potrebbe agire a livello di DOM in javascript, ma rimane il problema
	//dell'indeterminazione dell'arrivo delle richieste al server, per cui e' comunque meglio gestire il tutto col solo
	//stato di sessione
	for (Catalogo risultato : risultatiPassati.getRisultati()) {
		%>
		<ol>Risultato precedente:
		<%
		for (Libro libro : risultato.getLibri()) {
			%>
			<li>Titolo: <%= libro.getTitolo()%>, Autore: <%=libro.getAutore() %>, Editore: <%= libro.getEditore()%>, ISBN: <%= libro.getIsbn()%></li>
			<%
		}
		%>
		</ol><br/>
		<%
	}
	%>
	</div>

	<%@ include file="fragments/footer.html" %>

</body>
</html>
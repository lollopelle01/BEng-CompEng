<%@ page contentType="text/html; charset=US-ASCII" %>

<%-- uso della sessione --%>
<%-- <%@ page session="true"%> --%>

<%-- import java --%>
<%@ page import="java.util.Set, java.util.ArrayList"%>
<%@ page import="beans.Libro"%>

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
</head>

<jsp:useBean id="catalogo" class="beans.Catalogo" scope="application" />

<body>

	<%@ include file="fragments/header.html" %>
	<h1>Elenco Libri JSP</h1>
	
	<fieldset>
				<legend>Ricerca Libri per Autore</legend>
		<form action="">
			<label>Nome Autore: <input type="text" name="autore" id="autore" placeholder="J.K.R."></input></label>
			<br/>
			<button type="submit" name="cerca" value="cerca">Cerca</button>
		</form>
	</fieldset>
	
	<div class="results">
	<%
		String cerca = request.getParameter("cerca");
		String autore = request.getParameter("autore");
		if (cerca != null && cerca.equals("cerca")) {
			Set<Libro> libri;
			if (autore == null || autore.length() == 0)
				libri = catalogo.getLibri();
			else
				libri = catalogo.getByAutore(autore);
			if (libri.size() > 0) {
				%>
				<ul>Risultati ricerca per l'autore <bold><%=autore %></bold>
				<%
				for (Libro libro : libri) {
					%>
						<li>Titolo=<%=libro.getNome() %>; ISBN=<%=libro.getISBN() %></li>
					<%
				}
				%>
				</ul>
				<%
			} else {
				%>
				<h5>Nessun risultato trovato</h5>
				<%
			}
		}
	%>
	</div>
	
	<a class="button" href="nuovoLibro.jsp">Vai a NuovoLibro</a>



	<%@ include file="fragments/footer.html" %>

</body>
</html>
<%@ page contentType="text/html; charset=US-ASCII" %>

<%-- uso della sessione --%>
<%@ page session="true"%>

<%-- import java --%>
<%@ page import="java.util.List, java.util.ArrayList"%>
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
<jsp:useBean id="libro" class="beans.Libro" scope="session" />

<body>

	<%@ include file="fragments/header.html" %>
	<h1>Nuovo Libro JSP</h1>
	
	<%
	String invia = request.getParameter("invia");
	if (invia != null && (invia.equals("salva") || invia.equals("finalizza")) ) {
		String nome = request.getParameter("nome");
		String autore = request.getParameter("autore");
		String ISBN = request.getParameter("isbn");
		libro.setNome(nome);
		libro.setAutore(autore);
		libro.setISBN(ISBN);
		if (invia.equals("salva")) {
			%>
			<h4>Libro salvato con successo!</h4>
			<%
		}
		if (invia != null && invia.equals("finalizza")) {
			if (libro.getNome().equals("") || libro.getAutore().equals("") || libro.getISBN().equals("")) {
				%>
				<h4>Libro non valido...</h4>
				<%
			}
			else if (catalogo.aggiungiLibro(libro)) {
				%>
				<h4>Libro inserito con successo nel catalogo!</h4>
				<%
				session.invalidate();
				libro = new Libro(); //Necessario per svuotare la form gia' alla prima visualizzazione dopo la finalizza
			}
			else {
				%>
				<h4>Un libro con lo stesso ISBN e' gia' presente...</h4>
				<%
			}
		}
	}
	
	%>
	
	<fieldset>
				<legend>Inserisci un libro</legend>
		<form action="">
			<label>Nome Libro: <input type="text" name="nome" id="nome" placeholder="Harry Potter" value="<%= libro.getNome()%>"></input></label>
			<label>Nome Autore: <input type="text" name="autore" id="autore" placeholder="J.K.R." value="<%= libro.getAutore()%>"></input></label>
			<label>ISBN: <input type="text" name="isbn" id="isbn" placeholder="ISBN" value="<%= libro.getISBN()%>"></input></label>
			<br/>
			<button type="submit" name="invia" value="salva">Salva</button>
			<button type="submit" name="invia" value="finalizza">Finalizza</button>
		</form>
	</fieldset>
	
	<a class="button" href="./elencoLibri.jsp">Vai a ElencoLibri</a>



	<%@ include file="fragments/footer.html" %>

</body>
</html>
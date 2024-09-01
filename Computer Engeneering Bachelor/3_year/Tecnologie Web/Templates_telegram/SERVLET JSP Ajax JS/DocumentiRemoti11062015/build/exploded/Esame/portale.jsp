<%@ page contentType="text/html; charset=US-ASCII" %>

<%-- uso della sessione --%>
<%@ page session="true"%>

<%-- import java --%>
<%@ page import="java.util.List, java.util.ArrayList"%>
<%@ page import="beans.Utente"%>

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
	<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/login.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/portale.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/utils.js"></script>
</head>

<jsp:useBean id="utentiManager" class="beans.UtentiManager" scope="request" />

<body>

	<%@ include file="fragments/header.html" %>
	
	<%
		if (utentiManager.getNumUtenti() > 50) {
			request.getSession().setAttribute("utente", null);
		}
	
		Utente utente = null;
		utente = (Utente) request.getSession().getAttribute("utente");
		if (utente != null) {
		%>
			<h3>Benvenuto <%= utente.getNome() %> <%= utente.getCognome() %></h3>
		<%
			String[] selezioni = request.getParameterValues("selezione");
			if (selezioni != null) {
				for (String s : selezioni) {
					String documento = request.getParameter("area"+s.charAt(3));
					if (documento != null)
						utente.addDocumento(documento);
				}
			}
		} else {
			%>
			<h3>Anonimo or stateless mode</h3>
			<%
		}
		
		%>
		<fieldset>
			<legend>Scarica documenti</legend>
			<form name="urlform" id="urlform">
				<input type="url" required placeholder="url1" name="url1">
				<input type="url" required placeholder="url2" name="url2">
				<input type="url" required placeholder="url3" name="url3">
				<input type="button" value="Submit!" onclick="return getDocumenti(this.parentElement, myGetElementById('documenti'))">
			</form>
		</fieldset>
		<%
		
		%>
		
		<form name="documenti" id="documenti" action="" method="post">
			
		</form>
		
		<%
		
		if (utente != null) {
			%>
			<div id="footer">
			<%
			for (String document : utente.getDocumentiSelezionati()) {
				%>
				<textarea disabled><%=document.replace("<", "&lt;").replace(">", "&gt;") %></textarea> <br/>
				<%
			}
			%>
			</div>
			<%
		}
	%>

	<%@ include file="fragments/footer.html" %>

</body>
</html>
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
 
<jsp:useBean id="catalogo" class="beans.Catalogo" scope="application" />
<jsp:useBean id="authorization" class="java.lang.String" scope="session" />
 <%
 if (!authorization.equals("yes")) {
	 request.setAttribute("loginerror", "Non sei autorizzato!");
	 application.getRequestDispatcher("/login.jsp").forward(request, response);
 }
%>

<html>

<head>
	<meta name="Author" content="Federico Stella">
	<title>Start JSP</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/styles/default.css" type="text/css"/>
</head>


<body>

	<%@ include file="fragments/header.html" %>
	

<%
	
		String message = (String) request.getAttribute("message");
		if (message != null) {
			%>
			<h4><%=message %></h4><br/><br/>
			<%
		}
		%>
		<script src="scripts/nuovoLibro.js" type="text/javascript"></script>
		<script src="scripts/utils.js" type="text/javascript"></script>
		<fieldset>
			<legend>Form</legend>
			<form action="" onsubmit="return false">
				<label>Autore: <input type="text" name="autore" id="autore" placeholder="autore" required></input></label><br/>
				<label>Titolo: <input type="text" name="titolo" id="titolo" placeholder="titolo" required></input></label><br/>
				<label>Editore: <input type="text" name="editore" id="editore" placeholder="editore" required></input></label><br/>
				<label>ISBN: <input type="text" name="isbn" id="isbn" placeholder="isbn" required></input></label><br/>
				<button type="button" value="invia" onclick="sendJSON(this.form, myGetElementById('resDiv'))">Invia</button>
				<button type="reset" value="reset">Reset</button>
			</form>
		</fieldset>
		
	
	<div name="resDiv" id="resDiv"></div>

	<%@ include file="fragments/footer.html" %>

</body>
</html>
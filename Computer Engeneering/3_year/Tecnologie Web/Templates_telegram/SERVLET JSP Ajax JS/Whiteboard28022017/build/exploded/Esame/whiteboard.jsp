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
 
<jsp:useBean id="whiteboard" class="beans.Whiteboard" scope="application" />
<jsp:useBean id="authorization" class="java.lang.String" scope="session" />
 <%
Utente utente = (Utente) request.getSession().getAttribute("utente");
		if (utente == null) {
			request.setAttribute("loginerror", "Non sei autenticato!");
			%>
			<jsp:forward page="/login.jsp"></jsp:forward>
			<%
		} else if (whiteboard.getVersion() < utente.getAccessoVersion() || whiteboard.getVersion() < utente.getActualVersion()) {
			utente.setAccessoVersion(whiteboard.getVersion());
			utente.setActualVersion(whiteboard.getVersion());
		}
%>

<html>

<head>
	<meta name="Author" content="Federico Stella">
	<title>Start JSP</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/styles/default.css" type="text/css"/>
	<script src="scripts/utils.js" type="text/javascript"></script>
	<script src="scripts/whiteboard.js" type="text/javascript"></script>
</head>


<body>

	<%@ include file="fragments/header.html" %>
	
	<h1>Whiteboard!</h1>
	<br/>
	
	<div id="errorDiv"></div>
	
	<div id="resDiv">
		<ul>
		<%
		utente.setActualVersion(whiteboard.getVersion());
		for (String line : whiteboard.getLinesFromVersion(utente.getAccessoVersion())) {
			%>
			<li><%= line %></li>
			<%
		}
		%>
		</ul>
	</div>
	
	<br/>
	<form>
		<input type="text" name="linea" id="linea"/>
		<input type="button" name="submit" value="Submit" onclick="submitLine(this.form.linea, myGetElementById('resDiv'))"/>
	</form>
	
<%
if (authorization.equals("yes")) {
	%>
	<h2>Admin section</h2>
	<form action="./dispatcherServlet" method="post"><button type="submit" name="cancellatutto" value="cancellatutto">Cancella tutto</button></form>
	<%
}
%>

	<%@ include file="fragments/footer.html" %>

</body>
</html>
<%@ page contentType="text/html; charset=US-ASCII" %>

<%-- uso della sessione --%>
<%@ page session="true"%>

<%-- import java --%>
<%@ page import="java.util.List, java.util.ArrayList"%>

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


<body>

	<%@ include file="fragments/header.html" %>
	
	<%
	String loginerror = (String) request.getAttribute("loginerror");
	if (loginerror != null && loginerror.length() > 0) {
		%>
		<h4><%=loginerror %></h4>
		<%
	}
	%>
	
	<script src="scripts/login.js" type="text/javascript"></script>
	<fieldset><legend>Login</legend>
		<form name="login" action="./loginServlet"
			onsubmit="return isAllCompiled(this)" method="post">
			<label for="username">Username: </label>
			<input type="text" name="username" placeholder="username" required>
			<br />
			<label for="password">Password: </label>
			<input type="password" name="password" placeholder="password" required>
			<br />
			<input type="submit" value="Login">
		</form>
	</fieldset>


	<%@ include file="fragments/footer.html" %>

</body>
</html>
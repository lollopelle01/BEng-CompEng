<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<!-- accesso alla sessione -->
<%@ page session="true"%>

<!-- import di classi Java -->
<%@ page import="java.util.*"%>
<%@ page import="servlets.LoginServlet"%>
<%@ page import="beans.Utente"%>


<html>
	<head>
		<title>LoginJSP</title>
		<link rel="stylesheet" href="styles/default.css" type="text/css"></link>
		<script type="text/javascript" src="scripts/json.js"></script>
		
	</head>
	<body>
	
       	<fieldset><legend>Admin login, oppure entra da utente</legend>
		
		<form name="login" action="<%=request.getContextPath()%>/login" method="post"> 
		
			<label for="username">Username</label> 
			<input type="text" name="username" placeholder="username" required> <br /> 
			
			<label for="password">Password</label> 
			<input type="password" name="password" placeholder="password" required> <br /> 
			
			<input type="submit" value="Login">
		</form>
		
		</fieldset>
		
	</body>
</html>
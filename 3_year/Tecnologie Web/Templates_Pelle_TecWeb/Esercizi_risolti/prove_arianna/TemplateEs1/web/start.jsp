<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<!-- accesso alla sessione -->
<%@ page session="true"%>

<!-- import di classi Java -->
<%@ page import="beans.Utente"%>
<%@ page import="beans.UtentiAutenticati"%>
<%@ page import="java.util.*"%>

<!-- codice html restituito al client -->
<!DOCTYPE HTML PUBLIC>
<html>
	<head>
	
		<meta http-equiv="Pragma" content="no-cache"/>
		<meta http-equiv="Expires" content="-1"/>

		<title>Login page</title>
	</head>

	<body>
		
		<h3>Login</h3>

		<form name="loginform" action="Login" method="post">
		
			<div>
	           <p>Log in using your username and password</p>
	           
	            <label for="nome">Your username:</label><br>
	            <input type="text" id="username" name="username" >
	        </div>
	
	        <div>
	            <label for="password">Your password:</label><br>
	            <input type="password" id="password" name="password">
	        </div>
	        <br>
	        <input type="submit" name="login" value="LOGIN">
				
		</form>	
		

		<div>
			<%
				String errLog = (String)session.getAttribute("errLog");
			  	if(errLog!=null) {
				  int codiceErr = Integer.parseInt(errLog);
				  
				  switch (codiceErr) {
						case -1: %>
							<p><b> Error: user not authenticated, please do the Signin!</b></p>
							<%break;%>
					<%	case -2: 
							UtentiAutenticati utenti = (UtentiAutenticati) application.getAttribute("utentiAutenticati");
							Utente u = utenti.getUtenteByName((String)session.getAttribute("username")); %>
							<p><b> Error: incorrect password, you have used <%=u.getTentativi()%> attemps</b></p>
							<%break;
						default: 
							break;%>
				<%	} 
				  session.setAttribute("errLog", 0);
				}
			%>
		</div>
		
		<form name="registrationform" action="Signin" method="post">
	         <div>
	         	<p>Sign in if you don't have an account yet!</p>
	         	
	            <label for="username">New Username:</label><br>
	            <input type="text" id="username" name="username" >
	        </div>
	
	        <div>
	            <label for="password">New password:</label><br>
	            <input type="password" id="password" name="password">
	        </div>
		  	<br>
		  	<input type="submit" name="signin" value="SIGNIN">
		  	
        </form>
        
        	<%
				String errSign = (String)session.getAttribute("errSign");
			  	if(errSign!=null) {
				  int codiceErr = Integer.parseInt(errSign);
				  
				  switch (codiceErr) {
						case -1: %>
							<p><b> Please enter both username and password</b></p>
							<%break;%>
					<%	case -2: %>
							<p><b> Already used username!</b></p>
							<%break;
						default: 
							break;%>
				<%	} 
				  session.setAttribute("errSign", 0);
				}
			%>
		
	</body>
</html>

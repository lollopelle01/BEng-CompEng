<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<!-- accesso alla sessione -->
<%@ page session="true"%>

<!-- import di classi Java -->
<%@ page import="beans.Socio"%>
<%@ page import="beans.Circolo"%>
<%@ page import="java.util.*"%>

<!-- codice html restituito al client -->
<!DOCTYPE HTML PUBLIC>
<html>
	<head>
	
		<meta http-equiv="Pragma" content="no-cache"/>
		<meta http-equiv="Expires" content="-1"/>

		<title>Autenticazione</title>
	</head>

	<body>
		
		<h3>Benvenuto nella pagina di autenticazione</h3>
		

		<form name="loginform" action="Login" method="post">
		
			<div>
	           <p>Inserisci i tuoi dati di autenticazione (socio o amministratore)</p>
	           
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
						case -1:
			%>
							<p><b> Non risulti essere socio...</b></p>
							<%
							break;
							%>
					<%
						case -2: 
								Circolo utenti = (Circolo) application.getAttribute("circolo");
								Socio u = utenti.getSocioByName((String)session.getAttribute("username"));
					%>
							<p><b> Password errata, hai usato <%=u.getTentativi()%> tentativi</b></p>
							<%	break;
							
						default: 
							break;%>
				<%	} 
					
				  	session.setAttribute("errLog", 0);
				}
			%>
		</div>
		
	</body>
</html>

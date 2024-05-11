<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<!-- accesso alla sessione -->
<%@ page session="true"%>

<%@ page import="java.util.*"%>


<html>
    <head>
    
        <title>Disneyland</title>
        <link type="text/css" href="styles/default.css" rel="stylesheet"></link>
        
    </head>
    <body>
    	<h1>Acquisto di biglietti per Disneyland per visite di comitiva</h1>
    
        <div> <b> Autenticazione </b></div>
     	<form name="AutenticazioneForm" action="autenticazione" method="post">
     
     	Inserire i dati di Autenticazione:<br /><br />
		username: <input type="text" name="nomeutente" value=""/> <br />
		password: <input type="text" name="pw" value=""/> <br /><br />
		<input type="submit" name="richiesta" value="autenticazione"/><br />
  
     	</form>
     	
     	<%
      	
      	Integer result = (Integer)session.getAttribute("success");
      	if(result !=null)
      	{
      		switch(result)
      		{
      			case 1:
      			{
      				%>
      					<p>Ordine finalizzato con <strong>successo</strong> da parte sua, si attendono gli altri elementi del gruppo</p>
      				<% 
      			}break;
      			
      			case 2:
      			{
      				%>
  					<p>Tutti gli elementi del gruppo hanno finalizzato l'ordine: Ordine finalizzato con <strong>successo</strong> </p>
  					<% 
      			}break;
      			case 3:
      			{
      				%>
  					<p>Admin: finalizzato ordine gruppo con <strong>successo</strong> </p>
  					<% 
      			}break;
      			
      			case 4:
      			{
      				%>
  					<p>Admin: Resettato ordine gruppo con <strong>successo</strong> </p>
  					<% 
      			}break;
      			
      			case 5:
      			{
      				%>
  					<p>Utente: il suo ordine è stato gestito dell'amministratore </p>
  					<% 
      			}break;
      		}
      		session.invalidate();
      	}
      	
      	%>
        
    </body>
</html>
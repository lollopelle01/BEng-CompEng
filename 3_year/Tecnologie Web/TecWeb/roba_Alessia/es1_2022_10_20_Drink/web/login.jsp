<!-- STRUTTURA PAGINA HTML -->
<%@ page session="true"%>

<%@ page errorPage="../errors/failure.jsp"%>

<!-- import di classi Java -->

<%@ page import="java.util.*"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
        <!-- per non avere problema di cache -> aggiungo nell'header delle pagine HTML -->
        <meta http-equiv="Pragma" content="no-cache"/>
        <meta http-equiv="Expires" content="-1"/>
	
		<title> Pagina Login </title>
	
		</head>
    <body>
        <div>
        	<h5>Benvenuto, inserisci il tuo nome e il tavolo a cui ti vuoi aggiungere</h5>
        	<form action="servletStart" method="POST">
        		<table>
        			<tr>
        				<td>Username:</td>
        				<td><input id="username" name="username" type="text" placeholder="username"/></td>
        			</tr>
        			<tr>
        				<td>Tavolo</td>
        				<td><input id="tavolo" name="tavolo" type="text" placeholder="tavolo"/></td>
        			</tr>
        			<tr>
        				<td><input type="submit" value="Login"/></td>
        			</tr>
        		</table>
        	</form>
        	
	  	  	<%  
        		String err = (String)session.getAttribute("err");
        	
        	   //se ho errori
	  	  	   if(err != null) {
	  	  		   
	  	  		  int codiceErr = Integer.parseInt(err);
	  	  		  
	  	  		  //stampo il tipo di errore settato nella servlet che ha gestito il form
	  	  		  switch(codiceErr) {
	  	  		  	case 1: {
	  	  	%>
	  	  			  	<p><b> Errore: l'utente ha già effettuato il login</b></p>
	  	  	<% 
	  	  		 	} break;
	  	  		 	
	  	  		  	case 2: {
	  	  	%>
	  	  			  	<p><b> Errore: il tavolo inserito non esiste</b></p>
	  	  	<% 
	  	  		 	} break;
	  	  		 }
	  	  		  session.invalidate();
	  	  	  }
	  	  	%>
        </div>
	</body>
</html>



<!-- STRUTTURA PAGINA HTML -->
<%@ page session="true"%>

<%@ page errorPage="../errors/failure.jsp"%>

<!-- import di classi Java -->

<%@ page import="java.util.*"%>
<%@ page import="Beans.Articolo"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
        <!-- per non avere problema di cache -> aggiungo nell'header delle pagine HTML -->
        <meta http-equiv="Pragma" content="no-cache"/>
        <meta http-equiv="Expires" content="-1"/>
	
		<script type="text/javascript" src="scripts/utils.js"></script>
		<script type="text/javascript" src="scripts/articolo.js"></script>
	
		<title> Modifica Articolo Scientifico</title>
	
		</head>
    <body>
    
    	<%
    		String utente = (String)session.getAttribute("success");
    		
    		Articolo currentArticle = (Articolo) session.getAttribute("currentArticle");
    		
    		String statusString = (String) session.getAttribute("status");
    		
    		int status = Integer.parseInt(statusString);
    		
    	   	switch(status) {
  	  		  	case 1: {
  	  	%>
  	  			  	<p>
  	  			  		<b><%=utente%>, abbiamo creato un nuovo articolo con il nome '<i><%=currentArticle.getNome()%></i>'.</b>
  	  			  	</p>
  	  	<% 
  	  		 	} break;
  	  		 	
  	  		  	case 2: {
  	  	%>
  	  			  		<b><%=utente%>, abbiamo caricato l'articolo '<i><%=currentArticle.getNome()%></i>' che cercava ed ora è pronto per la modifica.</b>
    	<%	
  	  			} break;
    	   	}
    	%>
    	
    		<form>
	    		<table>
    				<tr>
						<td><textarea id="contenuto" name="text" cols="40" rows="5" disabled="disabled"><%=currentArticle.getText()%></textarea></td>
			  			<td><input type="submit" id="ask" name="ask" value="Chiedi permesso di scrittura"/></td>
			  			<td><input type="submit" id="let" name="let" value="Cedi permesso di scrittura" disabled="disabled"/></td>
			  		</tr>
		  		</table>
		  	</form> 		
	    	<br>
    	
    	<%

	    	if(request.getParameter("ask") != null && request.getParameter("ask").equals("Chiedi permesso di scrittura")) {
		   		
		   		if(currentArticle.isAccesso() == false) {
		   			currentArticle.setAccesso(true);	//lo mettiamo occupato
		%>
					<script> abilita(); </script>
					<p> Ora puoi modificare l'articolo </p>
		<%  			
		   		} else {
		%>
					<p> La prego di attendere, il permesso di scrittura lo ha un altro utente. </p>
		<%  	
		   		}
		   		
		   	} else if(request.getParameter("let") != null && request.getParameter("let").equals("Cedi permesso di scrittura")) {
		   		
		   		//se vuole lasciare il permesso, avrà modificato il testo
		   		String testo = request.getParameter("text");
		   		if(testo != null) {
		   			currentArticle.setText(testo);
		   		}

		   		if(currentArticle.isAccesso() == true) {
 			
		%>
					<script> disabilita(); </script>
					<p> Abbiamo ceduto il diritto di scrittura. </p>
		<% 
		   			currentArticle.setAccesso(false);
		   		} else {
		%>
					<p> Nessuno ha il diritto di scrittura, può già richiederlo.</p>
		<%   			  			
		   		}
		   	}
	
		%>
				
    </body>
</html>
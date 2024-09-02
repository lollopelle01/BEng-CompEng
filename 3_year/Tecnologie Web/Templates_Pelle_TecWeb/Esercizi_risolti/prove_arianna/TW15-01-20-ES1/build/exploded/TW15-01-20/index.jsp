<%@ page session="true"%>

<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<!-- metodi richiamati nel seguito -->
<%!



%>

<!-- codice html restituito al client -->
<html>
   <head>
      <title>Password</title>
		<link type="text/css" href="styles/default.css" rel="stylesheet"></link>
   </head>
   
   <body>
		
     <h1>Gestione password degli utenti appartententi ai gruppi</h1>
     
     beans.Gruppo Gruppo1 = new beans.Gruppo();
		
     
     
     <div> <b> Registrazione </b></div>
     <form name="RegistrazioneForm" action="registrazione" method=post >
     
     	Inserire i dati di Registrazione:<br /><br />
		nome_utente: <input type="text" name="nomeutente" value=""/> <br />
		gruppo_utente:
			<select name="gruppoutente"> 
				<option>Gruppo1</option> 
				<option>Gruppo2</option> 
				<option>Gruppo3</option> 
			</select> <br />
		password: <input type="text" name="pw" value=""/> <br /><br />
		<input type="submit" name="richiesta" value="registrazione"/><br />
  
     </form>
     
     <hr>
     
     <div> <b> Login </b></div>
      <form name="LoginForm" action="login" method=post >
      
     	Inserire i dati di Login:<br /><br />
		nome_utente: <input type="text" name="nomeutente" value=""/> <br />
		gruppo_utente:
			<select name="gruppoutente"> 
				<option>Gruppo1</option> 
				<option>Gruppo2</option> 
				<option>Gruppo3</option> 
			</select> <br />
		password: <input type="text" name="pw" value=""/> <br /><br />
		<input type="submit" name="richiesta" value="login"/><br />
		
     </form>
     
     <br /><br /><br /><br />
     
	  <div>
	  	<%String err = (String)session.getAttribute("err");
	  	  if(err!=null)
	  	  {
	  		  int codiceErr = Integer.parseInt(err);
	  		  switch(codiceErr)
	  		  {
	  		  	case 1:
	  		  	{
	  			  %>
	  			  	<p><b> Errore: il gruppo selezionato non esiste</b></p>
	  			  <% 
	  		 	}break;
	  		 	
	  		  case 2:
	  		  	{
	  			  %>
	  			  	<p><b> Errore: L'utente non esiste per il gruppo selezionato</b></p>
	  			  <% 
	  		 	}break;
	  		 	
	  		case 3:
  		  	{
  			  %>
  			  	<p><b> Errore: Password utente scaduta, cambiarla presso la pagina registration</b></p>
  			  <% 
  		 	}break;
  		 	
	  		case 4:
  		  	{
  			  %>
  			  	<p><b> Errore: Nel Gruppo selezionato ci sono almeno 3 password scadute, cambiare la propria presso la pagina registration</b></p>
  			  <% 
  		 	}break;
  		 	
	  		case 5:
  		  	{
  			  %>
  			  	<p><b> Errore: Password sbagliata</b></p>
  			  <% 
  		 	}break;
  		 	
	  		case 6:
  		  	{
  			  %>
  			  	<p><b> Errore: Password sbagliata per piu di tre volte, utente bloccato</b></p>
  			  <% 
  		 	}break;
	  		  }
	  	  }
	  	%>
	  	</div>
	  	
	  	<br /><br /><br /><br />
   </body>
</html>

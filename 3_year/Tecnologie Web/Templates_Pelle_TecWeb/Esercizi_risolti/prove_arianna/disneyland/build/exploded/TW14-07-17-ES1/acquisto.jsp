<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<!-- accesso alla sessione -->
<%@ page session="true"%>

<!-- import di classi Java -->
<%@ page import="it.unibo.tw.web.beans.Utente"%>
<%@ page import="it.unibo.tw.web.beans.GruppoComitiva"%>
<%@ page import="java.util.*"%>

<!-- metodi richiamati nel seguito -->
<%!


%>

<html>
   <head>
      <title>Disneyland Jsp</title>
		<link type="text/css" href="styles/default.css" rel="stylesheet"></link>
   </head>
   <body>
		
		<!-- parte di calcolo modi-servlet inclusa nel file jsp -->
		
		<%  //prendo i dati della sessione che mi servono: userGroup e currentUser
			Utente currentUser = (Utente)session.getAttribute("currentUser");
			GruppoComitiva userGroup = (GruppoComitiva)session.getAttribute("gruppo");
		
			String richiesta = request.getParameter("richiesta"); 
			if( richiesta!=null){
				if(richiesta.equals("aggiungi al carrello")){
					String biglietti = request.getParameter("biglietti");
					int b = Integer.parseInt(biglietti);
					userGroup.addBiglietti(b);
					session.setAttribute("gruppo", userGroup);
				}
			}
		%>
		
		
      <h1>Sezione di acquisto</h1>
      
     Sei stato autenticato, benvenuto nella tua sezione acquisti<br /> <br />
      
      <div> <b> Carrello </b></div>
		<form name="Carrelloform" >
			<p>Prezzo per "Disneyland ticket" standard: 45$ </p>
			<br />
			Selezionare il numero di biglietti: <input type="text" name="biglietti" value=""/> <br />
			<input type="submit" name="richiesta" value="aggiungi al carrello"/><br />
		</form>	
		<br /><br />
		
		<%  if( richiesta!=null){
				if(richiesta.equals("aggiungi al carrello")){ %>
					<div>L'utente <%=currentUser.getUsername()%> ha selezionato <%=request.getParameter("biglietti")%></div>
			<% 	}
			}
		%>
		
		<br /><br />
		
		<form name="Finalizzazioneform" action="Finalizzazione" method="post">
			<input type="submit" name="richiesta" value="finalizza"/><br />
		</form>	

   </body>
</html>

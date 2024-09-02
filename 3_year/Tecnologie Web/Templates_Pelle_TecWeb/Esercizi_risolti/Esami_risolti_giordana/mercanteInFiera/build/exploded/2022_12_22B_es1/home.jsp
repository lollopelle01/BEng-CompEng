<%@page import="com.google.gson.Gson"%>
<%@page import="beans.*"%>
<%@page import="java.util.*"%>
<%@ page import="java.time.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta http-equiv="Refresh" content= "45"/> <!-- refresh ogni 45 s della pagina per controllo durata -->
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="./styles/home.css">
        <script src="./scripts/jquery-1.12.3.min.js"></script>
        <script src="./scripts/home.js" defer></script>
        <title>Homepage</title>
    </head>
    <body>
    <%
		if(session.getAttribute("logged") == null || !((String) session.getAttribute("logged")).equals("Logged.") ) {
			response.sendRedirect("./login.jsp");
		}
    
    	Map<String, Giocatore> giocatori = (Map<String, Giocatore>) getServletContext().getAttribute("giocatori");
    	
    	String username = (String) session.getAttribute("username");
    	
    	if(getServletContext().getAttribute("start") != null){
    		LocalDateTime start = (LocalDateTime) getServletContext().getAttribute("start");
    		Duration duration = Duration.between(start, LocalDateTime.now());
        	
        	if(duration.toMinutes() > 20){
        		response.sendRedirect("./Vincita.jsp");
        	}
    	}
    	
    	//la jsp deve controllare che l'utente possa vendere la sua carta e setta il timer
    	if(request.getParameter("carta")!=null){
    		int cartaDaVendere = Integer.parseInt((String) request.getParameter("carta"));
    		System.out.println("HOME: l'utente " + username + " vuole vendere la sua carta " + cartaDaVendere);
    		
    		//controlliamo che non ci sia gia una vendita in corso
    		//controlliamo che possa vendere 
			if(getServletContext().getAttribute("venditaInCorsoDi").equals("")) {
				getServletContext().setAttribute("venditaInCorsoDi", username);
					
				getServletContext().setAttribute("cartaInVendita", cartaDaVendere);
					
				//iniziamo il timer per l'asta
				LocalDateTime startAsta = LocalDateTime.now();
				
				getServletContext().setAttribute("timerAsta", startAsta);
					
			}else {
				//la vendita non puo essere effettuata perchè ce ne è gia una in corso
				System.out.println("HOME: l'utente " + username + "non può vendere perchè è gia in corso una vendita");
			}
    	}
  	%>
        <header><h1>MERCANTE IN FIERA B</h1></header>
        <main>
        <%
        if(giocatori.keySet().size() == 3){
        %>
            <form action="home.jsp" method="post" >
                <label for="carta">Ecco le tue carte</label> <br>
                <select name="carta" id="carta">
                	<%for(int c : giocatori.get(username).getCarte()){ %>
                		<option value="<%=c %>"><%=c %></option>
                	<%} %>
                </select>
                <button id="vendi">Vendi questa carta</button>
            </form>
        <%}else { %>
        	<div>Attendi che altri giocatori si uniscano alla tua partita</div>
        
        <%} %>
        
        <div id="ajax"></div>
        
        
        <% String venditaDi = (String) getServletContext().getAttribute("venditaInCorsoDi");
        	int cartaInVendita = (int) getServletContext().getAttribute("cartaInVendita");
        	int offertaAttuale = (int) getServletContext().getAttribute("offertaAttuale");
        	String offertaDi = (String) getServletContext().getAttribute("offertaAttualeDi");
        	
        	if(cartaInVendita != 0 && !venditaDi.equals("")){
        		
        		if(!venditaDi.equals(username)) { 
                	System.out.println("Service: l'utente "+ venditaDi + ", ha iniziato la vendita della sua carta n." 
        				+ cartaInVendita);%>
                	
                	<div>
                		<div>Il giocatore <%=venditaDi %> ha messo in vendita la sua carta <%=cartaInVendita %></div> 
                		
                		 <label>Hai a disposizione <%=giocatori.get(username).getDenari() %> denari</label> <br>
                		 
                		 <label for="offerta">Fai un'offerta</label> <br>
                		 <input type="text" id="offerta" name="offerta"><br>
                		 
                		 <label for="prezzo">L'offerta attuale è di <%=offertaDi %> con valore:</label> <br>
                		 <input type="text" id="prezzo" readonly value="<%= offertaAttuale%>"><br>
                		 
                		 <button id="compra">Invia la tua offerta</button>
                	</div>
                	
                <% } else{ 
                	if(!venditaDi.equals("") && venditaDi.equals(username)){ %>
                		<div>
                		<label for="prezzo">L'offerta attuale per la tua carta <%=cartaInVendita %>è di <%=offertaDi %> con valore:</label> <br>
                		<input type="text" id="prezzo" readonly value="<%= offertaAttuale%>"><br>
                		</div>
                	<%}//if stesso utente vendita 
                	
                }//else 
        	}//if non esiste una vendita
        	else{%>
        		<div>Nessuna asta in corso</div>
        	<%} %>
        </main>
    </body>
</html>
<%@ page import="java.util.*" %>
<%@ page import="beans.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="./styles/home.css">
        <script type="text/javascript" src="./scripts/jquery-1.12.3.min.js" defer></script>
        <script type="text/javascript" src="./scripts/home.js" defer></script>
        <title>Homepage</title>
    </head>
    <body>
    <%
    if(session.getAttribute("logged") == null || !((String) session.getAttribute("logged")).equals("Logged.")){
    	response.sendRedirect("./login");
    }
    
    
    Map<Integer, Campo> campi = (Map<Integer, Campo>) getServletContext().getAttribute("campi");
	if(campi == null) {
		response.sendRedirect("./init");
	}
	
	String username = (String) session.getAttribute("username");
    %>
    
    <header><h1>TENNIS</h1></header>
    <main>
       <div>
       		<label>Seleziona il campo in cui prenotare:</label>
        	<select name="campo" id="campo">
        		<% for(int i : campi.keySet()){ %>
            		<option value="<%=i %>" ><%= i %></option>
       			<% } %>
        	</select> <br>
        	
            <label>Seleziona il giorno in cui prenotare (1-365):</label>
            <select name="giorno" id="giorno">
        		<% for(int i=1; i<366; i++){ %>
            		<option value="<%=i %>" ><%= i %></option>
       			<% } %>
        	</select> <br>
        	
        	<label>Seleziona l'orario in cui prenotare:</label>
        	<select name="orario" id="orario">
        		<% for(int i=0; i<24; i++){ %>
            		<option value="<%=i %>" ><%= i %></option>
       			<% } %>
        	</select> <br>
       </div> 
       
       <div id="ajax"></div>
       
       <div>
       	<table id="tabella_alternative">
       	</table>
       </div>
       
       <%System.out.println((String) getServletContext().getAttribute(username));
       if(getServletContext().getAttribute(username) != null){ 
       		String prenotazione = (String) getServletContext().getAttribute(username);
       		
       		if(prenotazione.equals("temporanea") ) { %>
       			<div>La tua prenotazione è momentaneamente in attesa di completamento</div>
       		<%}else{
       			if(prenotazione.equals("annullata") ){ %>
       			<div>La tua prenotazione ha superato il tempo limite di 2h e pertanto è stata annullata, si prega di rifarne un'altra</div>
       			<%}
       			}
       	}%>
       	<%if(	((String) getServletContext().getAttribute(username)).equals("confermata")){ %>
       	<div id="partita">Verifica le tue <a href="Partita.jsp">prenotazioni</a></div>
       	<%} %>
    </main>
    </body>
</html>
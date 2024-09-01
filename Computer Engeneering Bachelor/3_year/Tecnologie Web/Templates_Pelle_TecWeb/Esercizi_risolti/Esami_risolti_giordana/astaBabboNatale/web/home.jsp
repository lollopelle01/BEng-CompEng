<%@page import="com.google.gson.Gson"%>
<%@page import="beans.*"%>
<%@page import="java.util.*"%>
<%@ page import="java.time.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta http-equiv="Refresh" content= "30"/> <!-- refresha ogni 30 secodni -->
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
    
    	if(session.getAttribute("logged").equals("Admin.")){
    		response.sendRedirect("./Admin.jsp");
    	}
    
    	Map<String, Acquirente> acquirenti = (Map<String, Acquirente>) getServletContext().getAttribute("acquirenti");
    	
    	List<Regalo> regali = (List<Regalo>) getServletContext().getAttribute("regali");
    	
    	String username = (String) session.getAttribute("username");
    	
    	LocalDateTime start= null;
    	
    	if( getServletContext().getAttribute("start")!=null){
    		start= (LocalDateTime) getServletContext().getAttribute("start");
    	}
    	
  	%>
        <header><h1>ASTA DI BABBO NATALE</h1></header>
        <main>
        <%
        if(acquirenti.keySet().size() < 2){
        %>
            <div>Attendi che altri giocatori si uniscano alla tua partita</div>
        <%}else { %>
        	
        <%if(start != null && Duration.between(start, LocalDateTime.now()).toHours()>2){ %>
        
        	<div>L'ASTA HA SUPERATO IL TEMPO LIMITE</div>
        <%}else{ %>
        
        	<%if(getServletContext().getAttribute("asta") == null){
        		response.sendRedirect("./service");
        	}else{
        	
        	Regalo regalo_attuale = (Regalo) getServletContext().getAttribute("asta");
        	Offerta offerta_attuale = (Offerta) getServletContext().getAttribute("offerta");
        	%>
        
        <div>
        	<h2>Oggetto attualmente in vendita</h2>
        	
        	<label for="nome">nome regalo:</label>
        	<input type="text" id="nome" readonly value="<%=regalo_attuale.getNome() %>"><br>
        	
        	<label for="descrizione">descrizione regalo:</label>
        	<input type="text" id="descrizione" readonly value="<%=regalo_attuale.getDescrizione() %>"><br>
        	
        	<label for="base">base partenza asta regalo:</label>
        	<input type="text" id="base" readonly value="<%=regalo_attuale.getBase() %>"><br>
        	
        	<label for="prezzo_attuale">offerta attuale:</label>
        	<input type="text" id="prezzo_attuale" readonly value="<%=offerta_attuale.getOfferta() %>"><br>
        	
        	<label for="user_attuale">Nome acquirente dell'offerta attuale:</label>
        	<input type="text" id="user_attuale" readonly value="<%=offerta_attuale.getUsername() %>"><br>
        	
        	<div>
        		<label for="denari">I tuoi denari:</label>
        		<input type="text" id="denari" readonly value="<%=acquirenti.get(username).getDenari() %>"><br>
        		
        		<form action="./offerta" method="post">
        			<label for="offerta">Fai la tua offerta:</label>
        			<input type="text" id="offerta" name="offerta"><br>
        		
        			<button id="offri">Offri</button>
        		</form>
        	</div>
        	
        	<div>
        	<label>I TUOI REGALI VINTI</label><br>
        	<table>
        	<tr>
        		<th>NOME REGALO</th>
        		<th>DESCRIZIONE</th>
        	</tr>
        	<%for(Regalo r: acquirenti.get(username).getRegali()){ %>
        	<tr>
        		<td><%=r.getNome() %></td>
        		<td><%=r.getDescrizione() %></td>
        	</tr>
        	
        	<%} %>
        	</table>
        	</div>
        	
        </div>
        
        <div id="ajax"></div>
        <%}//else nuova asta
        } //else fine durata
        }//else inizio asta%>
        	
        </main>
    </body>
</html>
<%@ page import="java.util.*" %>
<%@ page import="beans.*" %>
<%@ page import="java.time.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Refresh" content= "10"/>
<link rel="stylesheet" href="./styles/home.css">
<title>Administrator</title>
</head>
<body>
	<%
		if(session.getAttribute("logged") == null || !((String) session.getAttribute("logged")).equals("Admin.")) {
			response.sendRedirect("./login.jsp");
		}
	Map<String, Acquirente> acquirenti = (Map<String, Acquirente>) getServletContext().getAttribute("acquirenti");
	
	List<Regalo> regali = (List<Regalo>) getServletContext().getAttribute("regali");
	
	LocalDateTime start= null;
	
	if( getServletContext().getAttribute("start")!=null){
		start= (LocalDateTime) getServletContext().getAttribute("start");
	}
	
	%>
	 <header><h1>ADMIN</h1></header>
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
        	
        	<form action="./service" method="post"><button>Termina questa asta</button></div>
        	
        </div>
        <%}//else nuova asta
        } //else fine durata asta totale
        }//else inizio asta acquirenti%>
    </main>
	        
</body>
</html>
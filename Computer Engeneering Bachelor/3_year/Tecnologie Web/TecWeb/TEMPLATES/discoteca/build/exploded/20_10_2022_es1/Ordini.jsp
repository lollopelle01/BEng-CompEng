<%@ page import="java.util.*" %>
<%@ page import="beans.Tavolo" %>
<%@ page import="beans.Drink" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="./styles/home.css">
        <script src="./scripts/jquery-1.12.3.min.js"></script>
        <script src="./scripts/home.js" defer></script>
        <title>Homepage</title>
    </head>
    <body>
		<%
		Map<Integer, Tavolo> tavoli = (Map<Integer, Tavolo>) getServletContext().getAttribute("tavoli");
		int tavolo = (int) session.getAttribute("entrato");
		
		Map<String, Integer> menu = (Map<String, Integer>) getServletContext().getAttribute("menu");
		if(menu == null) {
			response.sendRedirect("./init");
		}
		
		 //se è un utente gia entrato va redirecto alla pagina del suo tavolo
	    if(session.getAttribute("entrato") == null){
	    	response.sendRedirect("./home.jsp");
	    }
	    //altrimenti deve scegliere un tavolo in cui entrare
	    else{
	    	if(session.getAttribute("logged") != null){
	    		if(session.getAttribute("logged").equals("Admin.")){
	    			response.sendRedirect("./Admin.jsp");
	    		}
	    		else
	    			response.sendRedirect("./Cameriere.jsp");
	    	}
	    	else{
	    		if(request.getParameter("drink")!=null){
	        		String drink = (String) request.getParameter("drink");
	        		
	        		Drink d = new Drink(drink, menu.get(drink));
	        		tavoli.get(tavolo).addDrink(session, d);
	        		System.out.println("Orini: Utente (" + session + "), nel tavolo " + tavolo + " ha ordinato un " + drink);
	        			
	        	}
	    	}
	    }
		
		%>
		
		<form action="Ordini.jsp" method="post" class="form">
                <label>Benvenuto nel tuo tavolo: <%=tavolo %></label> <br>
                <label>Cosa vorresti ordinare?</label>
                <select name="drink">
                	<% for(String s : menu.keySet()){ %>
                		<option value="<%=s %>" ><%= s %>, costo: <%=menu.get(s) %></option>
                	<% } %>
                </select>
                <button id="ordina">Ordina</button>
        </form> 
        
        <div><button id="btn-conto_tavolo">Chiedi il conto del tavolo intero</button>
        	<input type="text" readonly id="conto_tavolo">
        	<div id="ajax1"></div>
        </div>
        
        <div><button id="btn-conto_personale">Chiedi il conto dei tuoi drink ordinati e consegnati</button>
        	 <input type="text" readonly id="conto_personale">
        	 <div id="ajax2"></div>
        </div>
	</body>
</html>
<%@ page import="java.util.*" %>
<%@ page import="beans.Tavolo" %>
<%@ page import="beans.Drink" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="Refresh" content= "2"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="./styles/home.css">
        <script src="./scripts/jquery-1.12.3.min.js"></script>
        <script src="./scripts/home.js" defer></script>
        <title>Homepage</title>
    </head>
    <body>
		<%
		if(session.getAttribute("logged") == null || !((String) session.getAttribute("logged")).equals("Cameriere.")) {
			response.sendRedirect("./login.jsp");
		}
		
		Map<Integer, Tavolo> tavoli = (Map<Integer, Tavolo>) getServletContext().getAttribute("tavoli");
		if(tavoli == null) {
			response.sendRedirect("./init");
		}
		
		if(request.getParameter("tavolo") != null || request.getParameter("utente") != null || request.getParameter("drink") != null ){
			int tavolo = Integer.parseInt(request.getParameter("tavolo"));
			String drink = (String) request.getParameter("drink");
			String utente = (String) request.getParameter("utente");
			
			for(HttpSession ses : tavoli.get(tavolo).getConsumazioni().keySet()){
				if(ses.getId().equals(utente)){
					for(Drink d : tavoli.get(tavolo).getConsumazioni().get(ses)){
						d.setConsegnato(true);
					}
				}
			}
			System.out.println("Cameriere: si effettua la consegna nel tavolo " + tavolo + ", del drink " + drink);
		}
		%>
		
		<header><h1>Cameriere</h1></header>
	        <main>
	        
	        	<form action="Cameriere.jsp" method="post" class="form">
	        	
	        	<label>Consegna uno dei drink che non lo sono ancora</label><br>
                <label>Seleziona il tavolo:</label>
                <select name="tavolo">
                	<% for(int i : tavoli.keySet()){ %>
                		<option value="<%=i %>" ><%= i %></option>
                	<% } %>
                </select>
                
                <label>Seleziona l'utente</label>
                <select name="utente">
                	<%for(int i : tavoli.keySet()) {
	        			for(HttpSession s : tavoli.get(i).getConsumazioni().keySet()){ %>
                			<option value="<%=s.getId() %>" ><%= s.getId() %></option>
                	<% } } %>
                </select>
                
                <label>Seleziona il drink</label>
                <select name="drink">
                	<%for(int i : tavoli.keySet()) {
	        			for(HttpSession s : tavoli.get(i).getConsumazioni().keySet()){ 
	        				for(Drink d : tavoli.get(i).getConsumazioni().get(s)){
	        					if(!d.isConsegnato()){	%>
                					<option value="<%=d.getNome() %>" ><%= d.getNome() %></option>
                	<% } } } } %>
                </select>
                
                <button id="consegna">Consegna</button>
        		</form> 
	        	
	        	<br><br>
	        	
	        	<table>
	        		<tr>
	        			<th>Tavolo n.</th>
	        			<th>Drink ordinati e consegnati</th>
	        		</tr>
	        		<%for(int i : tavoli.keySet()) {
	        			for(HttpSession s : tavoli.get(i).getConsumazioni().keySet()){ 
	        				for(Drink d : tavoli.get(i).getConsumazioni().get(s)){
	        					if(d.isConsegnato()){	%>
	        				
	        						<tr>
		        						<td><%=i %></td>
		        						<td><%=d.getNome() %></td>
	        						</tr>
	        		<%} } } }%>
	        	</table>
	        	
	        </main>
	</body>
</html>
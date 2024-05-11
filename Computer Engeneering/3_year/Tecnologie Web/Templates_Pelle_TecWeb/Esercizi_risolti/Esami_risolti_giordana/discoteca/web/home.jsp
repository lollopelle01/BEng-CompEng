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
        <title>Homepage</title>
    </head>
    <body>
    <%
    Map<Integer, Tavolo> tavoli = (Map<Integer, Tavolo>) getServletContext().getAttribute("tavoli");
	if(tavoli == null) {
		response.sendRedirect("./init");
	}
	
    //se è un utente gia entrato va redirecto alla pagina del suo tavolo
    if(session.getAttribute("entrato") != null){
    	response.sendRedirect("./Ordini.jsp");
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
    		if(request.getParameter("tavolo")!=null){
        		int tavolo = Integer.parseInt(request.getParameter("tavolo"));
        		if(tavoli.get(tavolo).addUser(session)){
        			System.out.println("HOME:Utente (" + session + ") unito correttamente al tavolo " + tavolo);
        				
        			//settiamo le variabili di sessione
        			session.setAttribute("entrato", tavolo);
        			response.sendRedirect("./Ordini.jsp");
        		}
        		else{
        			System.out.println("HOME:Utente (" + session + ") impossibile da unire al tavolo " + tavolo);
        		}
        			
        	}
    	}
    }
    %>
        <header><h1>DISCOTECA LOCA</h1></header>
         <main>
        	<div id="navbar">
				<table style="width: 100%;">
					<tr>
						<td class="navbar">
							<h1>DISCOTECA LOCA</h1>
						</td>
						
						<td class="navbar">
							<form action="login" method="post" class="form">
								<label for="username" class="label">Username</label>
								<input type="text" name="username" id="username"> <br>
								<label for="password" class="label">Password</label>
								<input type="password" name="password" id="password"> <br>
								<button id="login">Login</button>
							</form>
						</td>
					</tr>
				</table>
			</div>
			
            <form action="home.jsp" method="post" class="form">
                <label>Seleziona il tavolo:</label>
                <select name="tavolo">
                	<% for(int i : tavoli.keySet()){ %>
                		<option value="<%=i %>" ><%= i %></option>
                	<% } %>
                </select>
                <button id="ingresso">Unisciti</button>
            </form> 
        </main>
    </body>
</html>
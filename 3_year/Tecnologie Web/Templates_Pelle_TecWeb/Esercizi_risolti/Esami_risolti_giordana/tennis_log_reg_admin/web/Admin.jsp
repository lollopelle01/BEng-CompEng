<%@ page import="java.util.*" %>
<%@ page import="beans.*" %>
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
	Map<Integer, Campo> campi = (Map<Integer, Campo>) getServletContext().getAttribute("campi");
	if(campi == null) {
		response.sendRedirect("./init");
	}
	
	if(request.getParameter("campo")!=null && request.getParameter("giorno")!=null && request.getParameter("orario")!=null){
		int campo = Integer.parseInt((String) request.getParameter("campo"));
		int giorno = Integer.parseInt((String) request.getParameter("giorno"));
		int orario = Integer.parseInt((String) request.getParameter("orario"));
		String username = (String) request.getParameter("username");
		
		Prenotazione a = campi.get(campo).findPrenotazione(giorno, orario, username);
				
		campi.get(campo).removeP(a);
		
		getServletContext().setAttribute(username, "annullata");
		
		System.out.println("ADMIN: eliminata la prenotazione: " + a.toString());
	}
	%>
	 <header><h1>ADMIN</h1></header>
    <main>
    	<table>
    		<tr>
    			<th>CAMPO</th>
    			<th>GIORNO</th>
    			<th>ORARIO</th>
    			<th>USERNAME</th>
    		</tr>
<% 
for(int i=1; i<7; i++){
	for(Prenotazione p: campi.get(i).getPrenotazioni()){
		if(!p.isOccupato()){ %>
				<tr><td><%=i %></td><td><%=p.getGiorno() %></td><td><%=p.getOrario() %><td><%=p.getUsername1() %></td></tr>
		<% }
			
		}
	}

%>
	</table>
       
       <form action="Admin.jsp" method="post">
       <label for="campo">Inserisci il campo</label>
       <input type="text" name="campo"><br>
       
       <label for="giorno">Inserisci il giorno</label>
       <input type="text" name="giorno"><br>
       
       <label for="orario">Inserisci l'orario</label>
       <input type="text" name="orario"><br>
       
       <label for="username">Inserisci l'username</label>
       <input type="text" name="username"><br>
       
       <button id="submit">Elimina questa prenotazione temporanea</button>
       </form>
    </main>
</body>
	        
</body>
</html>
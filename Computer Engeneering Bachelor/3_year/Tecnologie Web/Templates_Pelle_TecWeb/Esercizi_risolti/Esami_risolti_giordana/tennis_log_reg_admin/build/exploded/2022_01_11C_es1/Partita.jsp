<%@ page import="java.util.*" %>
<%@ page import="beans.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%Map<Integer, Campo> campi = (Map<Integer, Campo>) getServletContext().getAttribute("campi");
if(campi == null) {
	response.sendRedirect("./init");
}

String username = (String) session.getAttribute("username"); 
if(username == null) {
	response.sendRedirect("./home");
}
%>
 <header><h1> LE TUE PARTITE</h1></header>
    <main>
    	<table>
    		<tr>
    			<th>CAMPO</th>
    			<th>GIORNO</th>
    			<th>ORARIO</th>
    			<th>AVVERSARIO</th>
    		</tr>
<% 
for(int i=1; i<7; i++){
	for(Prenotazione p: campi.get(i).getPrenotazioni()){
		if(p.getUsername1().equals(username)){ %>
				<tr><td><%=i %></td><td><%=p.getGiorno() %></td><td><%=p.getOrario() %></td><td><%=p.getUsername2() %></td></tr>
		<% }else{ 
			if(p.getUsername2().equals(username)){ %>
			<tr><td><%=i %></td><td><%=p.getGiorno() %></td><td><%=p.getOrario() %></td><td><%=p.getUsername1() %></td></tr>
		<% }
			
		}
	}
}
%>

       </table>
       
       <div id="partita">Prenota un altro <a href="home.jsp">campo</a></div>
    </main>
</body>
</html>
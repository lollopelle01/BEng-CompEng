<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="beans.Clienti"%>
<%@ page import="javax.websocket.Session"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
         <script type="text/javascript" src="scripts/admin.js"></script>
         <link type="text/css" href="styles/default.css" rel="stylesheet">
<title>Admin page</title>
</head>
<body>

		<!--  QUESTA PAGINA PER ADMIN -->
		
		<!-- ottengo lista sessioni attive con la classe SINGLETON -->
	<%
	String list="";
		if ( request.getParameter("visualizza") != null && request.getParameter("visualizza").equals("visualizza") ){
			Clienti clienti=Clienti.getInstance();
			list+="SessionID\n ";
			for(Session s: clienti.getSessioni()) {
				list+=s.getId()+"\n";
			}
		}
		
		if ( request.getParameter("elimina") != null && request.getParameter("giocatore") != null && request.getParameter("elimina").equals("elimina") ){
			Clienti clienti=Clienti.getInstance();
			for(Session s: clienti.getSessioni()) {
				if(s.getId().equals(request.getParameter("giocatore"))){
					clienti.removeCliente(s);
				}
			}
		}
	%>

<!-- ottengo lista sessioni attive con la getOpenSessions() acceduta in modo statico -->
<%-- 	<%
	String list="";
	
		if ( request.getParameter("visualizza") != null && request.getParameter("visualizza").equals("visualizza") ){
			list+="SessionID\n ";
			for(Session s: ProvaWS.getSessions()) {
				list+=s.getId()+"\n";
			}
		}
		
		if ( request.getParameter("elimina") != null && request.getParameter("giocatore") != null && request.getParameter("elimina").equals("elimina") ){
			for(Session s: ProvaWS.getSessions()) {
				if(s.getId().equals(request.getParameter("giocatore"))){
					synchronized (this) {s.close();}
				}
			}
		}
	%>
 --%>

	<div>
	<textarea id="list"  rows="30" cols="55" style="border: 1px solid black;" readonly ><%=list%></textarea>
	
	<form action="" method="get">
	<input type="submit" name="visualizza" value="visualizza" ><br>
       <input type="text" value="" name="giocatore">
        <input type="submit" name="elimina" value="elimina"><br>
</form>


<!-- metodo 2 con chiamata a WebSocket specifica per l'admin -->
 <!--       <button id="visualizza" value="visualizza" onclick="visualizza()">VISUALIZZA</button><br>
       <input type="text" value="" name="giocatore">
        <button id="elimina" value="elimina" onclick="elimina()">ELIMINA</button><br>

	<textarea id="lista"  rows="30" cols="55" style="border: 1px solid black;" readonly ></textarea> -->
    	
<a href='<%=request.getContextPath()%>/home.html?'>Vai alla pagina Principale</a>
<br>
</div>

</body>
</html>
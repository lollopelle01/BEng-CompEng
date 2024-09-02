<%@page import="beans.ExecThread"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	<meta http-equiv="Refresh" content= "30"/>
	<meta charset="UTF-8">
	<title>Admin</title>
	</head>
	<body>
	<%  if(getServletContext().getAttribute("threads") == null) {
			response.sendRedirect("./home.html");
		} else {
			Map<String, ExecThread> threads = (Map<String, ExecThread>)getServletContext().getAttribute("threads");
			
			if(request.getParameter("thread") != null) {
				ExecThread thread = threads.get(request.getParameter("thread"));
				if(thread != null) {
					thread.interrupt();
					
					threads.remove("thread");
				}
			}
		%>
			
			<h1>LISTA DELLE RICHIESTE ATTIVE</h1>
			<ul>
				<%for(String s : threads.keySet()) { %>
				<li><%= s %></li>
				<%} %>
			</ul>
			
			<form action="admin.jsp" method="post">
				<label>Scegli un thread da terminare</label>
				<select name="thread">
					<%for(String s : threads.keySet()) { %>
					<option value="<%=s%>"><%= s %></option>
					<%} %>
				</select>
				<button>TERMINA</button>
			</form>
			
		</body>
		<% }%>
		
</html>
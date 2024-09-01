<%@page import="beans.*"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	<meta http-equiv="Refresh" content= "10"/>
	<meta charset="UTF-8">
	<title>Admin</title>
	</head>
	<body>
	<%  if(getServletContext().getAttribute("threads")==null) {
			response.sendRedirect("./init");
		}
			
		Map<String, List<ExecThread>> threads = (Map<String, List<ExecThread>>) getServletContext().getAttribute("threads");
		
		if(request.getParameter("canzone") != null) {
			System.out.println("Admin: voglio eliminare i thread collegati alla canzone: " + (String) request.getParameter("canzone"));
			List<ExecThread> thread = threads.get((String) request.getParameter("canzone"));
			
			for(ExecThread t : thread){
				if(t.isAlive()) {
					t.interrupt();
				}
			}
		}
		%>
			
			<h1>LISTA DELLE CANZONI</h1>
			<ul>
				<%for(String s : threads.keySet()) { %>
				<li><%= s %></li>
				<%} %>
			</ul>
			
			<form action="admin.jsp" method="post">
				<label>Scegli una canzone per la quale eliminare tutti i thread attivi in questo momento</label>
				<select name="canzone">
					<%for(String s : threads.keySet()) { %>
					<option value="<%=s%>"><%= s %></option>
					<%} %>
				</select>
				<button>TERMINA</button>
			</form>
			
		</body>
</html>
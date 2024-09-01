//Servlet:
Utente utente = (Utente) request.getSession().getAttribute("utente");
		if (utente == null) {
			request.setAttribute("loginerror", "Non sei autenticato!");
			getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
		}


//JSP:
<%
Utente utente = (Utente) request.getSession().getAttribute("utente");
		if (utente == null) {
			request.setAttribute("loginerror", "Non sei autenticato!");
			%>
			<jsp:forward page="/login.jsp"></jsp:forward>
			<%
		}
%>
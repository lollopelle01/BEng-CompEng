<%@ page session="true"%>
<%@ page import="beans.Utente"%>
<%@ page import="beans.Gioco"%>
<%@ page import="java.time.*"%>

<%
	Gioco gioco = (Gioco) application.getAttribute("gioco");
	if(Duration.between(gioco.getStartTime(), Instant.now()).toMinutes()>20) {
		gioco.setGiocoCominciato(false);
		gioco.addMessaggio("Il gioco e' finito, accedere a j2.jsp");
		getServletContext().getRequestDispatcher("/j2.jsp").forward(request, response);
		return;
	}
	
	int num = Integer.parseInt(request.getParameter("numero"));
	Utente u = (Utente) session.getAttribute("utente");
	u.setCartaVendita(num);
	
	gioco.addMessaggio("Carta "+num+" messa in vendita da "+ u.getNome());
	gioco.setVendita(true);
	gioco.addMessaggio("Le offerte possono avere inizio");
	getServletContext().getRequestDispatcher("/mainUser.jsp").forward(request, response);
%>
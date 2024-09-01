<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<!-- accesso alla sessione -->
<%@ page session="true"%>

<!-- import di classi Java -->
<%@ page import="java.util.*"%>
<%@ page import="java.time.*"%>
<%@ page import="beans.*"%>
<%@ page import="com.google.gson.Gson" %>

<%
	//J1 gestisce le offerte
	Asta asta = (Asta)getServletContext().getAttribute("asta");

	Utente u = (Utente)request.getSession().getAttribute("user");
	Regalo r = asta.getInvendita();
	int cifra = Integer.parseInt(request.getParameter("offerta"));
	
	String msg, fine;
	Gson g = new Gson();
	
	if(asta.isTimeout()) {
		fine = "L'asta è terminata perché è scaduto il tempo!";
		getServletContext().setAttribute("fine", g.toJson(fine));
		getServletContext().getRequestDispatcher("/welcome.jsp").forward(request, response);
		return;
	}
	
	if(cifra > u.getDenari()) {
		msg = "Non hai abbastanza denaro per fare questa offerta";
		session.setAttribute("msg", g.toJson(msg));
		getServletContext().getRequestDispatcher("/welcome.jsp").forward(request, response);
		return;
	}

	Offerta nuovaOfferta = new Offerta(u, cifra, r);
	Offerta vecchiaOfferta = (Offerta)getServletContext().getAttribute("vecchiaOfferta");
	
	if(vecchiaOfferta == null) //è la prima offerta effettuata
	{
		getServletContext().setAttribute("vecchiaOfferta", nuovaOfferta);
		return;
	} 
	else 		//controllo che non siano passati 60 secondi
	{
		if(Duration.between(nuovaOfferta.getTime(), vecchiaOfferta.getTime()).toSeconds() >= 60) {
			asta.getVenduti().add(vecchiaOfferta);
			vecchiaOfferta.getAcquirente().setDenari(vecchiaOfferta.getAcquirente().getDenari()-vecchiaOfferta.getCifra());
			
			msg = "Troppo tardi, il regalo è già stato venduto";
			session.setAttribute("msg", g.toJson(msg));
			getServletContext().getRequestDispatcher("/S1").forward(request, response);
			return;
			
		} else {
			getServletContext().setAttribute("vecchiaOfferta", nuovaOfferta);
			msg = "La tua offerta è valida, si aspettano altre offerte per i prossimi 60 secondi";
			session.setAttribute("msg", g.toJson(msg));
			getServletContext().getRequestDispatcher("/welcome.jsp").forward(request, response);
			return;
		}
	}
	
%>
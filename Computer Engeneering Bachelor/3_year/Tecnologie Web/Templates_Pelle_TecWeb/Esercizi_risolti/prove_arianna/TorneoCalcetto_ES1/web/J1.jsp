<%@ page session="true"%>
<%@ page import="beans.Socio"%>
<%@ page import="beans.Torneo"%>
<%@ page import="java.time.*"%>

<%
	Torneo torneo = (Torneo) application.getAttribute("gioco");
	Socio s = (Socio)session.getAttribute("username");

	Socio[] squadra = null;
	squadra = torneo.getSquadra1();  //squadra 1
	if(!torneo.isFull(squadra)) {
		if(torneo.addSocio(squadra, s) == 0)
			s.setCapitano(true);
		s.setSquadra(1);
	} else {
		squadra = torneo.getSquadra2();  //squadra 2
		if(!torneo.isFull(squadra)) {
			if(torneo.addSocio(squadra, s) == 0)
				s.setCapitano(true);
			s.setSquadra(2);
		}
		else {
			squadra = torneo.getSquadra3();  //squadra 3
			if(!torneo.isFull(squadra)) {
				if(torneo.addSocio(squadra, s) == 0)
					s.setCapitano(true);
				s.setSquadra(3);
			}
			else {
				squadra = torneo.getSquadra4();  //squadra 4
				if(!torneo.isFull(squadra)) {
					if(torneo.addSocio(squadra, s) == 0)
						s.setCapitano(true);
					s.setSquadra(4);
				}
			}
		}
	}
	session.setAttribute("username", s);
	
	//se ho 4*5 iscrizioni il gioco può cominciare
	if(torneo.isFull(torneo.getSquadra1()) && torneo.isFull(torneo.getSquadra2()) && 
			torneo.isFull(torneo.getSquadra3()) &&torneo.isFull(torneo.getSquadra4())) {
		torneo.setStarted(true);
		torneo.setFaseiscrizione(false);
		torneo.init();
	}
	
	getServletContext().getRequestDispatcher("/welcome.jsp").forward(request, response);
%>
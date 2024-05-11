package it.unibo.tw.web.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.unibo.tw.web.beans.GruppoComitiva;
import it.unibo.tw.web.beans.Utente;

public class Finalizzazione extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	public void init(ServletConfig config) throws ServletException { 
		super.init(config);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String richiesta = request.getParameter("richiesta");
		if (!richiesta.equals("finalizza") && !richiesta.equals("completamento") && !richiesta.equals("annullamento"))
			return;
		
		//prendo gli oggetti che mi servono
		HttpSession session = request.getSession();
		GruppoComitiva gruppo = (GruppoComitiva) session.getAttribute("gruppo");
		Utente currentUser = (Utente) session.getAttribute("currentUser");
		
		//se l'utente è l'admin
		if(currentUser.getUsername().compareTo("admin")==0)
		{
			String gid = request.getParameter("gid");
			Map<String, GruppoComitiva> gruppi = (Map<String, GruppoComitiva>) this.getServletContext().getAttribute("gruppi");
			GruppoComitiva group = gruppi.get(gid);
			group.setBiglietti(0);
			gruppi.replace(gruppo.getNomeGruppo(), gruppo);
			this.getServletContext().setAttribute("gruppi", gruppi);
			
			if(richiesta.equals("completamento")) {
				session.setAttribute("success", 3);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/start.jsp");			
				rd.forward(request, response);
				return;
			}
			
			if(richiesta.equals("annullamento")) {
				session.setAttribute("success", 4);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/start.jsp");			
				rd.forward(request, response);
				return;
			}
		}
		
		//se l'utente è arrivato qui MA l'admin ha già gestito il gruppo comitiva non può far nulla
		if(gruppo.getGestitoDaAdmin()) {
			session.setAttribute("success", 5);
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/start.jsp");			
			rd.forward(request, response);
			return;
		}
		
		//altrimenti 
		int sessionCounter = 0;
		for(Utente u : gruppo.getLista())
		{
			if(u.equals(currentUser))
				u.setSession(null);  //sessione finita
			
			if(u.getSession() == null)
				sessionCounter++;   // aumento il numero di utenti del gruppo che hanno già eseguito la loro sessione 
		}
		
		if(gruppo.getLista().size() == sessionCounter)  //se tutti hanno effettuato la loro sessione
		{
			//finalizza gruppo comitiva
			gruppo.setBiglietti(0);
			Map<String, GruppoComitiva> gruppi = (Map<String, GruppoComitiva>) this.getServletContext().getAttribute("gruppi");
			gruppi.replace(gruppo.getNomeGruppo(), gruppo);
			this.getServletContext().setAttribute("gruppi", gruppi);
			
			session.setAttribute("success", 2);
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/start.jsp");			
			rd.forward(request, response);
			return;
		}
		else  //altrimenti solo lui ha finalizzato
		{
			session.setAttribute("success", 1);
			Map<String, GruppoComitiva> gruppi = (Map<String, GruppoComitiva>) this.getServletContext().getAttribute("gruppi");
			gruppi.replace(gruppo.getNomeGruppo(), gruppo);
			this.getServletContext().setAttribute("gruppi", gruppi);
			
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/start.jsp");		
			rd.forward(request, response);
			return;
		}
    }
}

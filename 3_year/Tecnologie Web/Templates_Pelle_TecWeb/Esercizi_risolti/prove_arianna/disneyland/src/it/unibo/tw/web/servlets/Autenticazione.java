package it.unibo.tw.web.servlets;

import java.io.IOException;
import java.util.HashMap;
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


public class Autenticazione extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	public void init(ServletConfig config) throws ServletException 
	{ 
		super.init(config);
		
		//mi serve una lista di utenti registrati 
		Map<String,Utente> utentiRegistrati = new HashMap<String, Utente>();
		
		Utente u = new Utente("ari", "ciao");
		u.setGruppo("Gruppo1");
		utentiRegistrati.put(u.getUsername(), u);
		
		u = new Utente("noe", "cia");
		u.setGruppo("Gruppo1");
		utentiRegistrati.put(u.getUsername(), u);
		
		u = new Utente("reb", "ci");
		u.setGruppo("Gruppo1");
		utentiRegistrati.put(u.getUsername(), u);
		
		u = new Utente("pepo", "bu");
		u.setGruppo("Gruppo2");
		utentiRegistrati.put(u.getUsername(), u);
		
		u = new Utente("pepa", "bau");
		u.setGruppo("Gruppo2");
		utentiRegistrati.put(u.getUsername(), u);
		
		u = new Utente("admin","admin");
		utentiRegistrati.put(u.getUsername(), u);
		
		this.getServletContext().setAttribute("utentiRegistrati", utentiRegistrati);
		
		//mi servono i due gruppi INIZIALMENTE VUOTI  a cui aggiungere gli utenti autenticati
		GruppoComitiva g1 = new GruppoComitiva();
		g1.setNomeGruppo("Gruppo1");
		GruppoComitiva g2 = new GruppoComitiva();
		g2.setNomeGruppo("Gruppo2");
		
		Map<String, GruppoComitiva> gruppi = new HashMap<String, GruppoComitiva>();
		gruppi.put("Gruppo1", g1);
		gruppi.put("Gruppo2", g2);
		this.getServletContext().setAttribute("gruppi", gruppi);
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws IOException, ServletException {
		
		String param = request.getParameter("richiesta");
		if(param.equals("autenticazione")) {
			
			String username = request.getParameter("nomeutente");
			Map<String,Utente> utentiRegistrati = (Map<String, Utente>) this.getServletContext().getAttribute("utentiRegistrati");
			Utente user = utentiRegistrati.get(username);
			System.out.println("Richiesta di autenticazione da: " +user);
			
			HttpSession session = request.getSession();
			
			//utente non registrato
			if(user == null) { 
				System.out.println("L'utente non è stato riconosciuto...");
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/start.jsp");	
				rd.forward(request, response);
				return;
			}
			
			// se arrivo qui è stato riconosciuto
			session.setAttribute("currentUser", user); 
			
			//è l'amministratore?
			if(user.getUsername().compareTo("admin")==0 && user.getPassword().compareTo("admin")==0) {
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin.jsp");
				dispatcher.forward(request, response);
	            return;
			}
			
			//se ha sbagliato password
			if(user.getPassword().compareTo(request.getParameter("pw"))!=0) {
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/start.jsp");	
				rd.forward(request, response);
				return;
			}
			
			user.setSession(session);
			
			Map<String, GruppoComitiva> gruppi = (Map<String, GruppoComitiva>)this.getServletContext().getAttribute("gruppi");
			GruppoComitiva gruppo = gruppi.get(user.getGruppo());
			gruppo.addUtente(user);  //aggiunge l'utente al gruppo
			
			session.setAttribute("gruppo", gruppo);
			
			response.sendRedirect("acquisto.jsp");
		}
		
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws IOException, ServletException {
	        throw new ServletException("This servlet can only be reached via an HTTP POST REQUEST");
	    }

}

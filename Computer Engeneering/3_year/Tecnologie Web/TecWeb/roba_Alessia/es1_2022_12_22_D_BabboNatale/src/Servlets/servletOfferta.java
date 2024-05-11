package Servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Beans.Utente;
import Beans.Regalo;
import Beans.Asta;
import Beans.Offerta;

import java.io.*;
import java.util.Date;

public class servletOfferta extends HttpServlet {

    private static final long serialVersionUID = 1L;
	
	@Override
	public void init(ServletConfig config) throws ServletException  { 
        super.init(config);
	}

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		 
	}
	 
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		Asta asta = (Asta) this.getServletContext().getAttribute("asta");
		
		if(asta.getStartTime() == -1) {
			asta.setStartTime(new Date().getTime());
		}
		
		String continente = req.getParameter("continente");
		Regalo currentRegalo = asta.getCurrentRegalo();
		
		Utente u = (Utente) req.getSession().getAttribute("utente");
		Double denari = Double.parseDouble(req.getParameter("offerta"));
		
		System.out.println("denari: " + denari);
		System.out.println("continente: " + continente);
		
		//no stesso continente
		System.out.println("eseguo continente");
		if(!currentRegalo.getContinente().equals(continente)) {
			req.getSession().setAttribute("esitoOfferta", 1);
			this.getServletContext().getRequestDispatcher("/comprato.jsp").forward(req, res);
			return;
		}
		
		//no abbastanza denari
		System.out.println("eseguo denari");
		if(denari > u.getDenari()) {
			req.getSession().setAttribute("esitoOfferta", 2);
			this.getServletContext().getRequestDispatcher("/comprato.jsp").forward(req, res);
			return;
		}
		
		//base asta > offerta
		System.out.println("eseguo offerta");
		if(currentRegalo.getPrezzo() > denari) {
			req.getSession().setAttribute("esitoOfferta", 3);
			this.getServletContext().getRequestDispatcher("/comprato.jsp").forward(req, res);
			return;
		}
		
		synchronized(this) {
			Offerta offerta = new Offerta(u.getUsername(), denari);
			asta.getOfferte().add(offerta);
			
			System.out.println("aggiunta offerta");
			
			System.out.println("current time");
			
			System.out.println("ciclo time");
			while((new Date().getTime()) - asta.getStartTime() < 5000) { //120000) {
				System.out.println("entro nel ciclo del tempo");
			}
			
			//regalo comprato
			System.out.println("setto acquisto");
			currentRegalo.setAcquistato(true);
			double temp = 0.0;
			
			System.out.println("setto offerta");
			for(Offerta off : asta.getOfferte()) {
				if(off.getOfferta() > temp) {
					currentRegalo.setOffertaVinc(off);
					temp = off.getOfferta();
				}
			}
			
			System.out.println("tutto ok");
			req.getSession().setAttribute("esitoOfferta", 4);
			this.getServletContext().getRequestDispatcher("/comprato.jsp").forward(req, res);
			return;
		}
		
    }
} 
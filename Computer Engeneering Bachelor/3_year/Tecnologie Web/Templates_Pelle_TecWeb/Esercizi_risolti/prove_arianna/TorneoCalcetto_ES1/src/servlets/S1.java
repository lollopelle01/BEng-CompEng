package servlets;


import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import beans.Risultato;
import beans.Torneo;

public class S1 extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Gson gson;
	private int j;
	private int vincitore1;
	private int vincitore2;
	
	@Override
	public void init(ServletConfig conf) throws ServletException {
		
		super.init(conf);
		gson = new Gson();
		
		// memorizzo un array di 4 risultati, 1 per ogni capitano
		Risultato[] risultati = (Risultato[])this.getServletContext().getAttribute("risultati");
		if(risultati == null) {
			risultati = new Risultato[4];
			this.getServletContext().setAttribute("risultati", risultati);
			j = 0;
			vincitore1 = -1;
			vincitore2 = -1;
		}
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	 throws ServletException, IOException {
		
		int semifinale = Integer.parseInt(request.getParameter("semifinale"));
		int vincitore = Integer.parseInt(request.getParameter("vincitore"));
		int risultato = Integer.parseInt(request.getParameter("risultato"));
		
		Risultato[] risultati = (Risultato[])this.getServletContext().getAttribute("risultati");
		Risultato rcorr = new Risultato(semifinale, vincitore, risultato);
		String risposta = null;
		
		Torneo torneo = (Torneo)this.getServletContext().getAttribute("torneo");
		if(torneo.tempoScaduto()) {
			response.getWriter().println("Il tempo per comunicare la semifinale e' scaduto");
			return;
		}
		
		// verifico che sia concorde
		boolean corretto = true;
		for(int i=0; i<risultati.length; i++) 
		{
			corretto = true;
			if(risultati[i].getSemifinale() == rcorr.getSemifinale()) {
				
				if(risultati[i].getVincitore() == rcorr.getVincitore()) {
					risultati[j] = rcorr;
					j++;
					
					if(rcorr.getSemifinale() == 1)
						vincitore1 = rcorr.getVincitore();
					else
						vincitore2 = rcorr.getVincitore();
					
				} else
					corretto = false;
				
			} else {					//primo risultato inserito della semifinale in questione
				risultati[j] = rcorr;
				j++;
			}
		}
		
		boolean done = true;
		for(int i=0; i<risultati.length; i++) {
			if(risultati[i] == null)
				done = false;
		}
		
		if(!corretto) {
			risposta = "Non sono stati inseriti dati concordi con l'altro capitano";
			request.setAttribute("incorretto", gson.toJson(risposta,String.class));
			getServletContext().getRequestDispatcher("/welcome.jsp").forward(request, response);
		}
		if(done) {
			risposta = "Vincitori: squadra"+vincitore1+" e squadra"+vincitore2+
					"\nChe abbia inizio la finale!";
			getServletContext().setAttribute("done", gson.toJson(risposta, String.class));
			getServletContext().getRequestDispatcher("/welcome.jsp").forward(request, response);
		}
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
	 throws ServletException, IOException {
		throw new ServletException("This servlet can only be reached via an HTTP POST REQUEST");
	}
}

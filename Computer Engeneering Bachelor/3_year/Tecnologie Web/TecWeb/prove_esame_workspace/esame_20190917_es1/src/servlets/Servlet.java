package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import beans.*;

public class Servlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	Gson g;
	
	@SuppressWarnings("unchecked")
	@Override
	public void init() throws ServletException {
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Ricevo prenotazione e devo salvarla
		
		/*** Estraggo richiesta */
		int id = Integer.parseInt(request.getParameter("id_hotel"));
		int check_in = Integer.parseInt(request.getParameter("check_in"));
		int check_out = Integer.parseInt(request.getParameter("check_out"));
		float prezzo = Float.parseFloat(request.getParameter("prezzo_finale"));
		
		// Creo prenotazione
		Prenotazione p = new Prenotazione();
		p.setId_hotel(id);
		p.setCheck_in(check_in);
		p.setCheck_out(check_out);
		p.setPrezzo_finale(prezzo);
		
		// Inserisco prenotazione per Admin
		this.getServletContext().setAttribute("lista_prenotazioni", ((ArrayList<Prenotazione>) this.getServletContext().getAttribute("lista_prenotazioni_ufficiali")).add(p));
		
		// Aggiorno i posti disponibili
		for( Hotel h : ( (ArrayList<Hotel>) this.getServletContext().getAttribute("lista_hotel"))) {
			if (h.getId() == id) {
				h.setCamere_disponibili(h.getCamere_disponibili() - 1);
			}
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Devo calcolare il prezzo dinamico e restituire una Prenotazione in JSON
		
		/** Estraggo richiesta **/
		int id = Integer.parseInt(request.getParameter("id_hotel"));
		int check_in = Integer.parseInt(request.getParameter("check_in"));
		int check_out = Integer.parseInt(request.getParameter("check_out"));
		// Il prezzo lo ottengo dinamicamente
		float prezzo_statico=-1;
		for( Hotel h : ( (ArrayList<Hotel>) this.getServletContext().getAttribute("lista_hotel"))) {
			if (h.getId() == id && h.getCamere_disponibili() > 0) {
				prezzo_statico = h.getPrezzo_statico_camere();
				break;
			}
			if (h.getId() == id && h.getCamere_disponibili()==0) {
				prezzo_statico = -1;
				break;
			}
		}
		
		// Creiamo la Prenotazione
		Prenotazione p = new Prenotazione();
		p.setId_hotel(id);
		p.setCheck_in(check_in);
		p.setCheck_out(check_out);
		p.setPrezzo_finale(prezzo_statico);
		
		// Confronto richiesta con altre prenotazione per calcolare prezzo
		for (Prenotazione pren : (ArrayList<Prenotazione>) this.getServletContext().getAttribute("lista_prenotazioni")) {
			if (p.isOverlapped(pren)) {p.setPrezzo_finale((float) 1.1 * p.getPrezzo_finale());}
		}
		
		// Inserisco per confronti successivi
		this.getServletContext().setAttribute("lista_prenotazioni", ((ArrayList<Prenotazione>) this.getServletContext().getAttribute("lista_prenotazioni")).add(p));
		
		// Invio tramite JSON la prenotazione proposta al cliente
		String ris = g.toJson(p);
		response.getWriter().println(ris);
	}
}

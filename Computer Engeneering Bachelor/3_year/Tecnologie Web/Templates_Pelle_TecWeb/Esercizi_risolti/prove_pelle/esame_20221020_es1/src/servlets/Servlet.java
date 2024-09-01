package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.*;

public class Servlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	private static Map<Integer, ArrayList<Cliente>> tavoli;
	private static Map<Integer, Float> contoTotaleFinale;

	private Cliente getCliente(HttpSession utente, int tavolo){
		for(Cliente c : tavoli.get(tavolo)){
			if(c.getSessione().getId() == utente.getId()){return c;}
		}
		return null;
	} 
	
	@SuppressWarnings("unchecked")
	@Override
	public void init() throws ServletException {
		// Inizializzo eventuali mappe/liste x database
		tavoli = new HashMap<Integer, ArrayList<Cliente>>();
		getServletContext().setAttribute("tavoli", tavoli); // per admin e cameriere

		contoTotaleFinale = new HashMap<Integer, Float>();
	}

	/**
	 * GET: admin che fa cose e client eche chiede risultati
	 * POST: cliente chiede drink e cameriere consegna drink
	 */

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Prendo parametri
		String nomeDrink = request.getParameter("nome_drink");
		float costoDrink = Float.parseFloat(request.getParameter("nome_drink"));
		int tavolo = Integer.parseInt(request.getParameter("numTavolo"));
		String richiesta = request.getParameter("richiesta");
		HttpSession utente = request.getSession();

		// Creo drink richiesto
		Drink d = new Drink(nomeDrink, costoDrink, false);

		// Differenziamo richiesta
		if(richiesta=="utente"){ //Utente
			// Guardo se cliente è nuovo
			if(tavoli.get(tavolo).contains(utente)){ // già presente --> aggiorno
				getCliente(utente, tavolo).addDrink(d);
			}
			else{ // non presente --> aggiungo
				tavoli.get(tavolo).add(tavolo, new Cliente(utente, new ArrayList<Drink>()));
			}
		}
		else{ // Cameriere
			String sessionId = request.getParameter("utente");

			// Cameriere ora provvede a cambiare lo stato del drink consegnato
			for(Cliente c : tavoli.get(tavolo)){
				if(c.getSessione().getId() == sessionId){
					for(Drink dest : c.getDrinks()){
						if(dest.getNome() == d.getNome()){dest.setConsegnato(true);}
					}
				}
			}
		}
	}

    @SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Prendo parametri
		String tipoRichiesta = request.getParameter("richiesta");
		HttpSession utente = request.getSession();

		// Gestiamo il vario tipo di richiesta
		switch(tipoRichiesta){
			case "prezzo_tavolo" :  
			{ // utente mi chiede il prezzo totale del suo tavolo --> quindi serata terminata --> posso calcolare anche glii altri
				int tavolo = Integer.parseInt(request.getParameter("numTavolo"));
				
				if(contoTotaleFinale.isEmpty()){ // calcoliamo tutti
					for(int t : tavoli.keySet()){
						float costoTavolo = 0;
						for(Cliente c : tavoli.get(t)){costoTavolo += c.calcolaCosto();}
						if(costoTavolo < 100 && (boolean)getServletContext().getAttribute("chiusura_tavolo")==true){
							costoTavolo = 100;
						}
						contoTotaleFinale.put(tavolo, costoTavolo);
					}
				}
				
				utente.setAttribute("risp_calcolo_acquisti", "Costo del tavolo " + tavolo + " : " + contoTotaleFinale.get(tavolo));
			}
			break;

			case "prezzo_personale" :
			{ // utente mi chiede il suo prezzo 
				int tavolo = Integer.parseInt(request.getParameter("numTavolo"));
				utente.setAttribute("risp_calcolo_acquisti", "Costo personale: " + getCliente(utente, tavolo).calcolaCosto());
			}
			break;

			case "visualizza_tavoli" :
			{ // admin chiede di chiudere il bar
				getServletContext().setAttribute("visualizza_tavoli", true);
			}
			break;

			case "chiusura_bar" :
			{ // admin chiede di chiudere il bar
				getServletContext().setAttribute("chiusura_tavolo", true);
				// Chiudendo il tavolo, al primo che mi chiede qualcosa forzo il calcolo
			}
			break;

		}

	}
}

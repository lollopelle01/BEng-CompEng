package servlets;


import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import beans.Giocatore;
import beans.Result;

public class ServiceServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7554977297793320847L;
	Gson gson;
	Random random;
	
	@Override
	public void init() throws ServletException {
		this.gson = new Gson();
		this.random = new Random(System.currentTimeMillis());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();

		Map<String, Giocatore> giocatori = (Map<String, Giocatore>) getServletContext().getAttribute("giocatori");
		
		String venditore = (String) getServletContext().getAttribute("venditaInCorsoDi");
		int cartaInVendita = (int) getServletContext().getAttribute("cartaInVendita");
		int offertaAttuale = (int) getServletContext().getAttribute("offertaAttuale");
		String offertaDi = (String) getServletContext().getAttribute("offertaAttualeDi");
		
		LocalDateTime start = (LocalDateTime) getServletContext().getAttribute("timerAsta");
		/*--------------------------------------------------------*/
		/* GESTIONE DELLA RICHIESTA */
		String username = (String) session.getAttribute("username");
		
		int offerta = Integer.parseInt((String) req.getParameter("offerta"));
		
		System.out.println("ServiceServlet: l'utente: " + username + ", vorrebbe comprare la carta n. " + cartaInVendita
				+ " per " + offerta + " denari");
		
		Result result = new Result();
		
		//controllo sul timer dell'asta
		if(Duration.between(start, LocalDateTime.now()).toSeconds()>60 && !getServletContext().getAttribute("venditaInCorsoDi").equals("")) {
			//offerta nulla, l'asta per questa carta va conclusa e bisogna resettare tutte le variabili di applicazione
			getServletContext().setAttribute("venditaInCorsoDi", "");
			getServletContext().setAttribute("cartaInVendita", 0);
			getServletContext().setAttribute("offertaAttuale", 0);
			getServletContext().setAttribute("offertaAttualeDi", "");
			
			//modifichiamo il database 
			
			//aggiungo all'acquirente la sua nuova carta e detraggo i denari offerti
			giocatori.get(offertaDi).getCarte().add(cartaInVendita);
			giocatori.get(offertaDi).setDenari(giocatori.get(offertaDi).getDenari()-offertaAttuale);
			
			//tolgo al venditore la carta venduta e aggiungo i denari
			List<Integer> carte = giocatori.get(venditore).getCarte();
			
			int count=0;
			for(int c : carte) {
				if(c==cartaInVendita) {
					break;
				}
				else {
					count++;
				}
			}
			
			giocatori.get(venditore).getCarte().remove(count);
			
			
			giocatori.get(venditore).setDenari(giocatori.get(venditore).getDenari() + offertaAttuale);
			
			result.setMessage("L'asta è terminata");
		}else {
			//l'asta è ancora attiva e l'offerta va presa in considerazione
			
			if(offertaAttuale>=offerta) {
				result.setMessage("offerta insufficiente");
			}else {
				if(offerta > giocatori.get(username).getDenari()) {

					result.setMessage("offerta non eseguibile per fondi insufficienti");
				}else {
					result.setMessage("offerta andata a buon fine");
					
					getServletContext().setAttribute("offertaAttuale", offerta);
					getServletContext().setAttribute("offertaAttualeDi", username);
				}
				
			}
			
		}
		
		resp.getWriter().write(gson.toJson(result));
	}
}

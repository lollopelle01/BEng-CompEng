package servlets;


import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import beans.Campo;
import beans.Prenotazione;
import beans.Result;
import beans.Soluzioni;

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
		Map<Integer, Campo> campi = (Map<Integer, Campo>) getServletContext().getAttribute("campi");
		
			HttpSession session = req.getSession();
			
			String username = (String) session.getAttribute("username");
			
			/*--------------------------------------------------------*/
			/* GESTIONE DELLA RICHIESTA */
			int campo = Integer.parseInt((String) req.getParameter("campo"));
			int giorno = Integer.parseInt((String) req.getParameter("giorno"));
			int orario = Integer.parseInt((String) req.getParameter("orario"));
			System.out.println("Service: Utente (" + username + "), vorrebbe prenotare il campo " + campo + ", il " + giorno + ", alle " + orario);
			
			Result result = new Result();
			
			boolean esiste = false;
			
			for(Prenotazione p : campi.get(campo).getPrenotazioni()) {
				if(p.getGiorno() == giorno && p.getOrario() == orario) {
					//dentro il campo selezionato esiste gia una prenotazione per quel giorno e per quell'orario
					esiste = true;
					
					if(p.isOccupato()) {
						//nel caso in cui la prenotazione sia conclusa
						result.setMessage("ERROR");
						
						//BISOGNA indicargli 5 slot liberi
						List<Soluzioni> rr = new ArrayList<>();
						
						for(int i=0; i<5; i++) {
							int campo_libero = this.random.nextInt(6)+1;
							int orario_libero = this.random.nextInt(24)+1;
							
							boolean trovata = false;
							
							//itero su tutte le prenotazioni del nuovo campo random
							for(Prenotazione libera : campi.get(campo_libero).getPrenotazioni()) {
								//se trovo una prenotazione con ora e giorno uguali allora trovata = true
								if(libera.getGiorno() == giorno && libera.getOrario() == orario_libero ) {

									trovata = true;
									
									// se la prenotazione trovata non è completa o scaduta allora la aggiungo
									if(Duration.between(libera.getStart(), LocalDateTime.now()).toHours()<2 && !libera.isOccupato()) {
										//quindi va aggiunta alle 5 alternative
										Soluzioni s = new Soluzioni(campo_libero, giorno, orario_libero);
										rr.add(s);
									}
									
								}
							}
							
							if(!trovata) {
								//se non esiste 
								Soluzioni s = new Soluzioni(campo_libero, giorno, orario_libero);
								rr.add(s);
							}
						}
						
						System.out.println("ServiceServlet: Generate 5 nuove possibilità: " + rr.toString() );
						
						
						result.setSoluzioni(rr);
						
					}
					else {
						//se la prenotazione esiste gia controllo la scandenza del timer
						Duration duration = Duration.between(p.getStart(), LocalDateTime.now());
						if(duration.toHours()>2) {
							//la prenotazione va annullata per quel tennista e creata nuova per questo
							campi.get(campo).removeP(p);
							getServletContext().setAttribute(p.getUsername1(), "annullata");
							
							//Nuova prenotazione per questo
							Prenotazione pp = new Prenotazione(username, giorno, orario);
							
							//si avvia ora un timer di 2h
							LocalDateTime start = LocalDateTime.now();
							
							pp.setStart(start);
							
							campi.get(campo).addP(pp);
							
							getServletContext().setAttribute(pp.getUsername1(), "temporanea");
							
							result.setMessage("temporanea");
							
							System.out.println("Service: l'utente " + username + ", elimina la vecchia prenotazione: "+ p.toString());
						}
						else {
							//la prenotazione va confermata
							p.setOccupato(true);
							p.setUsername2(username);
							
							//BISOGNA notificare ad entrambi l'avvenuta prenotazione
							result.setMessage("ok");
							
							getServletContext().setAttribute(p.getUsername1(), "confermata");
							getServletContext().setAttribute(p.getUsername2(), "confermata");
							
							System.out.println("Service: l'utente " + username + ", conferma la prenotazione: " + p.toString());
						}
					}
				}
			}
			
			if(!esiste) {
				//se non esiste una prenotazione allora va aggiunta temporanea
				Prenotazione pp = new Prenotazione(username, giorno, orario);
				
				//si avvia ora un timer di 2h
				LocalDateTime start = LocalDateTime.now();
				
				pp.setStart(start);
				
				campi.get(campo).addP(pp);
				
				result.setMessage("temporanea");
				
				getServletContext().setAttribute(pp.getUsername1(), "temporanea");
				
				
				System.out.println("Service: l'utente " + username + ", crea la prenotazione: " + pp.toString());
			}
			
			resp.getWriter().write(gson.toJson(result));
	}
}

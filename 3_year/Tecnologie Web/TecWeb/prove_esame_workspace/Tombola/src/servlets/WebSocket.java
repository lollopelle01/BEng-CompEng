package servlets;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

import javax.servlet.http.HttpServlet;
import javax.websocket.*;
import javax.websocket.server.*;

import org.hibernate.ejb.criteria.predicate.IsEmptyPredicate;

import com.google.gson.Gson;

import beans.Request;
import beans.Response;
import beans.UpdateReq;

import java.time.Instant;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/actions")
public class WebSocket{
	
	private static final Set<WebSocket> calcEndpoints = new CopyOnWriteArraySet<>();
	private static final Map<Long, ArrayList<Session>> giocatori = new HashMap<Long, ArrayList<Session>>();
	private static final Map<Long, PartitaTombolaThread> partite = new HashMap<Long, PartitaTombolaThread>();
	private static long idGruppo = 0;

	private Map<String, String> databaseUtenti = new HashMap<String, String>();
	private Session sessione;

	private Gson g = new Gson();
	// private Map<String, Integer> reqCounter = new HashMap<String,Integer>();
	// private Set<String> users = new HashSet<String>();
	long inizio=0;
	
	@OnOpen
	public void open(Session session){
		System.out.println("Connessione Aperta ");
		
		// Aggiungo la sessione corrente a priori --> se tizio non autenticato lo caccio dopo
		calcEndpoints.add(this);

		System.out.print("ho finito la open");
	}
	
	@OnClose
	public void close(Session session)
	{
		System.out.println("Connessione Chiusa ");
	}
	
	@OnMessage
	public void handleMessage(String message, Session session)throws IOException, EncodeException {
		Request req = g.fromJson(message, Request.class);
		this.DoOperation(req, session);
	}
	
	// ELABORAZIONE RICHIESTA e INVIO
	private void DoOperation(Request req, Session session) throws IOException, EncodeException {
		Response response = new Response();

		switch(req.getFase_partita()){
			case "autenticazione" :
			{// gestione autenticazione
				String username = req.getNome_password().get(0);
				String password = req.getNome_password().get(1);
				if (databaseUtenti.containsKey(username) && databaseUtenti.get(username).compareTo(password)==0){ // È autenticato
					response.setAutenticato(true);

					// Aggiungo giocatore a ultima sessione di gioco
					giocatori.get(idGruppo).add(session);

					if(giocatori.get(idGruppo).size()==10){ // Inizia la partita
						PartitaTombolaThread partita = new PartitaTombolaThread(giocatori.get(idGruppo));
						partite.put(idGruppo, partita);
						partita.start();
						
						// Ora il gruppo è al completo quindi ne prepariamo un altro
						idGruppo++;
						giocatori.put(idGruppo, new ArrayList<Session>());
					}
					
					// Se non sono 10 aspetteranno il 10° 
				
				}
				else { // Non è autenticato
					response.setAutenticato(false);
					close(session);
				}

				// Invio risposta
				sendback(g.toJson(response), session);
			}
			break;

			case "vittoria" :
			{ // gestione cinquina, decina e/o tombola --> semplifichiamo
				
				// Devo terminare il Thread corrispondendte e comunicare ai giocatori //

				// Ricavo il gruppo
				for(long id : giocatori.keySet()){
					if( giocatori.get(id).contains(session)){ // Abbiamo trovato il suo gruppo

						// Interrompiamo la partita
						partite.get(id).interrupt();

						// Comunichiamo e terminiamo connessioni
						for(Session giocatore : giocatori.get(id)){ // Per ogni giocatore
							giocatore.getBasicRemote().sendText("Partita terminata, ha vinto" + session.getId());
							session.close();
						}
					}
				}
			}
			break;


			case "abbandono" :
			{ // gestione abbandono di un giocatore
				// Ricavo il gruppo
				for(long id : giocatori.keySet()){
					if( giocatori.get(id).contains(session) && isAliveFrom3(partite.get(id))){ // Abbiamo trovato il suo gruppo

						// Togliamo un giocatore al thread
						giocatori.get(id).remove(session);

						// Andato tutto bene
						response.setOk(true);
						
					}

				}
				response.setOk(false);
				session.getBasicRemote().sendText(g.toJson(response));
			}
			break;
		}
	}

	private boolean isAliveFrom3(PartitaTombolaThread p){
		ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
		long threadCpuTime = threadBean.getThreadCpuTime(p.getId());
		if (System.nanoTime() - threadCpuTime >= 3*60*1000){return true;}
		else{return false;}
	}

	// Invio in broadcast
	private void broadcast(String message) throws IOException, EncodeException {
		calcEndpoints.forEach(endpoint -> {
			try {
				sendback(message, endpoint.sessione);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
    }

	// Invio normale
	private void sendback(String message, Session sessione) throws IOException, EncodeException {
		try {
			System.out.println("Sto inviando: "+message);
			sessione.getBasicRemote().sendText(message);
			
		} catch (Exception  e) {
           e.printStackTrace();
       }
	}

	@OnError
	public void onError(Session session, Throwable throwable)
	{
		System.out.println("Errore ");
		throwable.printStackTrace();
	}
}



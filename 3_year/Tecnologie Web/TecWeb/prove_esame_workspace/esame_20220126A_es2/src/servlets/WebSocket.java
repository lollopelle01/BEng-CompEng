package servlets;

import java.io.IOException;

import javax.websocket.*;
import javax.websocket.server.*;

import com.google.gson.Gson;

import beans.Request;
import beans.Response;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/actions")
public class WebSocket{
	
	private Session sessione; // serve per broadcast
	private Gson g = new Gson();
	// private Map<String, Integer> reqCounter = new HashMap<String,Integer>();
	// private Set<String> users = new HashSet<String>();
	private static final Set<WebSocket> calcEndpoints = new CopyOnWriteArraySet<>();
	long inizio=0;

	// Viene mantenuta server-side
	private int[][] matrice_condivisa = new int[5][5];

	public WebSocket(){ //inizializza matrice
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				this.matrice_condivisa[i][j] = -1;
			}
		}
	}
	
	@OnOpen
	public void open(Session session)
	{
		System.out.println("Connessione Aperta ");

		// Per broadcast
		this.sessione = session;
		calcEndpoints.add(this);

		// users.add(session.getId());
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
		Response res = this.DoOperation(req);
		
		// In ogni caso la risposta sarà condivisa a tutti
		broadcast(g.toJson(res));
	}
	
	// ELABORAZIONE RICHIESTA
	private Response DoOperation(Request req) { // La richiesta sarà solo un inserimento di un valore
		Response response = new Response();
		
		// Controlliamo se matrice è piena
		int matricePiena = 0;
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				if(this.matrice_condivisa[i][j]!=-1){matricePiena++;}
			}
		}
		if(matricePiena == 9){
			response.setType("matrice"); return response;
			// Valuteremo le proprietà delle matrici con richieste AJAX a servlet ...
		}

		// Se non è piena ed il valore è ovvimente valido
		this.matrice_condivisa[req.getRiga()][req.getColonna()] = req.getValore();	
		response.setType("valore_singolo");
		response.setRichiesta(req);


		return response;
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

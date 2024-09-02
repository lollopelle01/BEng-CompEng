package servlets;

import java.io.IOException;

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
	
	private Session sessione;
	private Gson g;
	// private Map<String, Integer> reqCounter = new HashMap<String,Integer>();
	// private Set<String> users = new HashSet<String>();
	private static final Set<WebSocket> calcEndpoints = new CopyOnWriteArraySet<>();
	long inizio=0;
	
	@OnOpen
	public void open(Session session)
	{
		this.sessione = session;
		g = new Gson();
		System.out.println("Connessione Aperta ");
		// reqCounter.put(session.getId(), 0);
		calcEndpoints.add(this);

		// Termino la chat per tutti dopo 30 min
		if(calcEndpoints.size() == 2){ inizio = Instant.now().getEpochSecond();}
		if(Instant.now().getEpochSecond() >= inizio + 30*3600 && inizio!=0){
			calcEndpoints.clear();
		}

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
		if (res.isValid()){broadcast(g.toJson(res));}
	}
	
	// ELABORAZIONE RICHIESTA
	private Response DoOperation(Request req) {
		Response response = new Response();

		if(req.getTesto().startsWith("S") || req.getTesto().startsWith("A")){ // Messaggio proibito
			response.setValid(false);
		}
		else{ // Messaggio normale
			response.setNome(req.getNome());
			response.setTesto(req.getTesto());
			response.setValid(true);
		}

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

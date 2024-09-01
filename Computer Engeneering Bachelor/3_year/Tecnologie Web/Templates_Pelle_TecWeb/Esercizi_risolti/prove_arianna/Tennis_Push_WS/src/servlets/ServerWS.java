package servlets;

import java.io.IOException;

import javax.websocket.*;
import javax.websocket.server.*;


import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/actions2")
public class ServerWS {
	
	private Session sessione;
	private static int iscritti = 0; 
	private static Push thread;
	private static final Set<ServerWS> calcEndpoints = new CopyOnWriteArraySet<>();
	
	@OnOpen
	public void open(Session session) {
		this.sessione = session;
		System.out.println("Connessione Aperta ");
		calcEndpoints.add(this);
	}
	
	@OnClose
	public void close(Session session) {
		System.out.println("Connessione Chiusa ");
	}
	
	@OnMessage
	public void handleMessage(String message, Session session)throws IOException, EncodeException {
		System.out.println("Messaggio: "+message.toString());
		
		if(message.contains("iscrivimi")) {		// messaggio: iscrizione
			iscritti++;
			sendback("Iscrizione effettuata con successo, d'ora in poi riceverà gli aggiornamenti!");
		}
		
		if(iscritti == 1) {						//caso messaggio: inizio servizio, solo una volta al primo iscritto
			thread = new Push();
			thread.start(); 
		}
	}
	
	//unicast
	private void sendback(String message) throws IOException, EncodeException {
		try {
			System.out.println("Sto inviando: "+message);
			this.sessione.getBasicRemote().sendText(message);
			
		} catch (IOException  e) {
            e.printStackTrace();
        }
	}
	
	//broadcast
	static void broadcast(String message) throws IOException, EncodeException {
        calcEndpoints.forEach(endpoint -> {
            try {
                endpoint.sessione.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

	@OnError
	public void onError(Session session, Throwable throwable) {
		System.out.println("Errore ");
		throwable.printStackTrace();
	}
	
}

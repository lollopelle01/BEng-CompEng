package servlet;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import javax.websocket.*;
import javax.websocket.server.*;


import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;


@ServerEndpoint("/actions")
public class ProvaWS{
	
	
	private Session sessione;
	private static final Set<ProvaWS> calcEndpoints = new CopyOnWriteArraySet<>();
	private static String chat = "";
	private static Instant now;
	
	@OnOpen
	public void open(Session session)
	{
		this.sessione = session;
		System.out.println("Connessione Aperta ");
		calcEndpoints.add(this);
		
		if (calcEndpoints.size() == 2) {
			now = Instant.now();
		}
	}
	
	@OnClose
	public void close(Session session)
	{
		System.out.println("Connessione Chiusa ");
	}
	
	@OnMessage
	public void handleMessage(String message, Session session)throws IOException, EncodeException {
		if ((now != null) && (Duration.between(now, Instant.now()).toMinutes()>=30)){
			this.close(session);
			return;
		}
		if (message.equals("Iscrivimi")) {
			sendback(chat);
			return;
		} 
		if (message.charAt(0)=='A' || message.charAt(0) == 'S') {
			sendback(" ");
		}
		else {
			broadcast(message);
			chat += message;
		}
		
	}

	private void sendback(String message) throws IOException, EncodeException {

		try {
			System.out.println("Sto inviando: "+message);
			this.sessione.getBasicRemote().sendText(message);
			
		} catch (IOException  e) {
            e.printStackTrace();
        }
		
	}
	
	
	private static void broadcast(String message) throws IOException, EncodeException {
		
        calcEndpoints.forEach(endpoint -> {
            try {
                endpoint.sessione.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    

	@OnError
	public void onError(Session session, Throwable throwable)
	{
		System.out.println("Errore ");
	}

}

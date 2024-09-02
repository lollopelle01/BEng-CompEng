package servlets;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import javax.websocket.*;
import javax.websocket.server.*;

import com.google.gson.Gson;


import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/actions")
public class ServerWS {
	
	private Session sessione;
	private Gson g;
	private static int chatgroup = 0; 		//chat users
	private static Instant start = null;
	private static final Set<ServerWS> calcEndpoints = new CopyOnWriteArraySet<>();
	
	
	@OnOpen
	public void open(Session session) {
		this.sessione = session;
		g = new Gson();
		System.out.println("Connessione Aperta ");
		calcEndpoints.add(this);
	}
	
	@OnClose
	public void close(Session session) {
		System.out.println("Connessione Chiusa ");
	}
	
	@OnMessage
	public void handleMessage(String message, Session session)
	 throws IOException, EncodeException {
		
		if ((start != null) && (Duration.between(start, Instant.now()).toMinutes()>=30)){
			this.close(session);
			return;
		}
		
		message = g.fromJson(message, String.class);
		
		if(message.contains("connect me")) {			// messaggio: connessione
			
			chatgroup++;
			
			if(chatgroup >= 2)
				start = Instant.now();
			
			broadcast(g.toJson("new user join the chat"));  
		}
		else if (start != null){						// messaggio: scrittura su chat (solo se sono almeno 2)
			
			if(message.startsWith("S") || message.startsWith("A"))  //messaggio proibito
				sendback(g.toJson("Deleted message...the other users will not see your message"));
			else
				broadcast(message);  
		} else 
			sendback(g.toJson("Wait for other users"));
		
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

package servlets;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import javax.websocket.*;
import javax.websocket.server.*;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;


@ServerEndpoint("/actions")
public class ProvaWS {
	
	Session sessione;
	private Gson g;
	private static int count = 0; //giocatori
	private static Instant start = null;
	private static Gioca thread;
	//for brodcast
	static final Set<ProvaWS> calcEndpoints = new CopyOnWriteArraySet<>();
	Map<Gioca, Set<ProvaWS>> partite = new HashMap<Gioca, CopyOnWriteArraySet<>>();
	@OnOpen
	public void open(Session session)
	{
		this.sessione = session;
		g = new Gson();
		System.out.println("Connessione Aperta ");
		calcEndpoints.add(this);
	}
	
	@OnClose
	public void close(Session session)
	{
		System.out.println("Connessione Chiusa ");
	}
	
	@OnMessage
	public void handleMessage(String message, Session session)throws IOException, EncodeException, InterruptedException {
		
		System.out.println("Messaggio Arrivato: "+message+" da: "+session.getId());
		if(message.equals("gioca")) {
			
			ProvaWS.count++;
			if(count==10) {
				broadcast(count +" numero giocatori, si puo' iniziare la partita");
				
				start = Instant.now();
				initBroadcast();  //invio cartelle
				
				thread = new Gioca();
				thread.start(); // start the thread
				count=0;
				
			} else {
				broadcast(count +" numero giocatori");   //altrimenti viene visualizzato solo il numero di partecipanti
			}
			
		} else if(message.equals("cinquina") && start != null) {
			
			broadcast(session.getId() +" ha fatto cinquina");
			
		} else if(message.equals("decina") && start != null) {
			
			broadcast(session.getId() +" ha fatto decina");
			
		} else if(message.equals("tombola") && start != null) {
			
			broadcast(session.getId() +" ha fatto tombola");
			thread.stopThread(); // stops the thread
			thread.join(); // wait for the thread to stop
			broadcast("il gioco e' terminato");
			start = null;
			
		} else if(message.equals("abbandona")) {
			Instant end = Instant.now();
			Duration timeElapsed = Duration.between(start, end);
			if(timeElapsed.toMinutes()>=3) {
				count--;
				broadcast(session.getId() +" ha abbandonato la partita");
				broadcast(count +" giocatori rimasti");
			} else {
				sendback("Non puoi ancora abbandonare la partita");
			}
		}
	}

	//per comunicazione unicast
	private void sendback(String message) throws IOException, EncodeException {

		try {
			System.out.println("Sto inviando: "+message);
			this.sessione.getBasicRemote().sendText(message);
			
		} catch (IOException  e) {
            e.printStackTrace();
        }
		
	}
	
	//per comunicazione broadcast
	static void broadcast(String message) throws IOException, EncodeException {
		
        calcEndpoints.forEach(endpoint -> {
            try {
                endpoint.sessione.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
	
	//invio delle cartelle
	private static List<Integer> creaCartelle() {
		int countNum = 0;
		Random r = new Random();
		List<Integer> result = new ArrayList<>();
		while(countNum!=15) {
			int num = r.nextInt(100);
			result.add(num);
			countNum++;
		}
		return result;
	}
	
	//identica alla comunicazione broadcast generica ma invia cartelle
	private static void initBroadcast() throws IOException, EncodeException {
		
        calcEndpoints.forEach(endpoint -> {
            try {
                endpoint.sessione.getBasicRemote().sendText(creaCartelle().toString());
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

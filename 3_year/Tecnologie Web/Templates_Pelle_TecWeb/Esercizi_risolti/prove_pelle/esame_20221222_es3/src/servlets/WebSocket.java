package servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;

import beans.Request;

@ServerEndpoint("/actions")
public class WebSocket{

	// private Map<String, Integer> reqCounter = new HashMap<String,Integer>();
	private static Map<Session, java.sql.Timestamp> users = new HashMap<Session, java.sql.Timestamp>();
	// private static ArrayList<Session> users = new ArrayList<Session>();
	private static ServerSecondario ss = new ServerSecondario(users.keySet());
	
	public WebSocket (){ ss.start(); }
	
	@OnOpen
	public void open(Session session)
	{
		System.out.println("Connessione Aperta ");
	}
	
	@OnClose
	public void close(Session session)
	{
		System.out.println("Connessione Chiusa ");
	}
	
	@OnMessage
	public void handleMessage(String message, Session session)throws IOException, EncodeException {
		String response="";
		if(message.compareTo("admin")==0){ // serviamo admin
			// Ordiniamo mappa
			List<Entry<Session, java.sql.Timestamp>> values = new ArrayList<Entry<Session, java.sql.Timestamp>>(users.entrySet()); 
			values.sort(Entry.comparingByValue(Comparator.reverseOrder()));

			for( Entry e : values){
				response += "Utente: " + ((Session) e.getKey()).getId() + "\tloggato in:" + ((java.sql.Timestamp) e.getValue());
			}
			sendback(response, session);
		}
		else{
			// Iscrivo utente formalmente
			users.put(session, new java.sql.Timestamp(System.currentTimeMillis()));

			// Iscrivo utente a demone 
			ss.addSessione(session);
		}
	}
	
	// ELABORAZIONE RICHIESTA
	// private void DoOperation(Request req) {
	// 	ss.addSessione(sessione);
	// }

	@OnError
	public void onError(Session session, Throwable throwable)
	{
		System.out.println("Errore ");
		throwable.printStackTrace();
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
}

// SERVER SECONDARIO PER GESTIONE UTENTI
class ServerSecondario extends Thread{
	private Set<Session> sessioni;

	public ServerSecondario(Set<Session> sessioni){
		this.sessioni = sessioni;
	}

	public void addSessione(Session s){
		this.sessioni.add(s);
	}

	public void run(){
		File results = new File("risultati.txt");
		String line;
		try (BufferedReader br = new BufferedReader(new FileReader(results))) {
			long ultima_modifica = results.lastModified();

			// Gestione demone
			while(true){
				if(results.lastModified() > ultima_modifica){ // modifica avvenuta
					while((line=br.readLine())!= null){
						broadcast(line + "\n");
					}

					ultima_modifica = results.lastModified();
				}
			}
		} catch (IOException | EncodeException e) {
			e.printStackTrace();
		}
	}

	// Invio in broadcast
	private void broadcast(String message) throws IOException, EncodeException {
		sessioni.forEach(iscritto -> {
			try {
				sendback(message, iscritto);
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
}

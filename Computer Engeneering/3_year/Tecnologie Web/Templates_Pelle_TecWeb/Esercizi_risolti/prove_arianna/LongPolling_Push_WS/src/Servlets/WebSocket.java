package Servlets;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;

import Beans.Action;
import Beans.Admin;
import Beans.News;




// Guardare anche controparte relativa in file javaScript MyWebSocket.js


@ServerEndpoint("/WebSocketJava")
public class WebSocket {

	private Gson g = new Gson();
	
	private long lastModified = -1;
	private File f;
	static HashMap<Session, LocalDateTime> users;

	@OnOpen
	public void open(Session session) throws IOException, InterruptedException {

		System.out.println("Connection opened!");
		if(f == null) {
			f = new File("/Users/alessiadelforo/Desktop/risultati.txt");
			f.createNewFile();
		}
		if(lastModified == -1) {
			lastModified = f.lastModified();
		}
		if(users==null) {
			users = new HashMap<Session, LocalDateTime>();
		}
		this.parallel(session);
	}

	@OnClose
	public void close(Session session) {

		System.out.println("Connection closed!");
		// Eventuali procedure di chiusura della connessione

	}

	@OnError
	public void onError(Throwable error) {

		// Eventuale gestione dell'errore
		error.printStackTrace();

	}

	@OnMessage
	public void messageHandlerText(String message, Session session) throws IOException { 

		Action req = g.fromJson(message, Action.class);
		
		String action = req.getAction();
		String nickname = req.getNickname();
		String pssw = req.getPassword();
		
		System.out.println("Ho ricevuto: " + action + ", " + nickname + ", " + pssw);
		
		if(nickname.equals("admin") && pssw.equals("admin")) {
			System.out.println("E' entrato l'amministratore");
			/* visualizzare tutti gli utenti correntemente iscritti
			 * al servizio, con associato il timestamp di loro iscrizione,
			 * e ordinati in ordine di durata decrescente dell’iscrizione */
			
			List<Entry<Session, LocalDateTime>> values = new ArrayList<>(users.entrySet());
			values.sort(Entry.comparingByValue(Comparator.reverseOrder()));
			
			Admin admin = new Admin("admin", values);
			session.getBasicRemote().sendText(g.toJson(admin));

		} else {
			if(action.equals("iscrizione")) {
				try {
					LocalDateTime instrTime = LocalDateTime.now();
					Date data = java.sql.Timestamp.valueOf(instrTime);
					
					users.put(session, instrTime);
				
					FileReader r = new FileReader(f);
					BufferedReader bf = new BufferedReader(r);
					String line, toSend = "";
					
					while((line = bf.readLine())!=null) {
						toSend += "NEWS!! " + data + ":  "+ line + "\n";
					}
				
					req.setNews(toSend);
					
					System.out.println(instrTime + ": nuovo utente iscritto " + nickname);
					session.getBasicRemote().sendText(g.toJson(req));
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private synchronized void parallel(Session session) throws IOException, InterruptedException {
		Thread timerTask = new Thread() {
			@Override
			public void run() {
				try {
					while(true) {
						if(lastModified<f.lastModified()) {

							lastModified = f.lastModified();
							
							Date data = new Date(lastModified);
							
							FileReader r = new FileReader(f);
							BufferedReader bf = new BufferedReader(r);
							String line, toSend = "";
							
							while((line = bf.readLine())!=null) {
								toSend += "NEWS!! " + data + ":  "+ line + "\n";
							}
							System.out.println("Nuova news: " + line);
							
							for(Session s: session.getOpenSessions()) {
								s.getBasicRemote().sendText(g.toJson(new News(toSend)));
							}
						}
						Thread.sleep(5000);	//ogni minuto
					}
				} catch(IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		};
		// parte il thread
		timerTask.start();
	}

}

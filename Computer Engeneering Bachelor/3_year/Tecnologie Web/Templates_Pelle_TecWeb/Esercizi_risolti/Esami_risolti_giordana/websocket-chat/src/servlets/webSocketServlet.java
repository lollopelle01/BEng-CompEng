package servlets;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServlet;
import javax.websocket.*;
import javax.websocket.server.*;

import com.google.gson.Gson;

import beans.Request;
import beans.Responce;

import java.util.*;


@ServerEndpoint("/actions")
public class webSocketServlet{
	/* il parametro session esprime il numero inteso come persona connessa alla web socket, ad ogni nuova tab si avrà un nuovo numero session
	 * nel caso di piu persone connesse ma che abbiano la stessa visualizzazione bisogna lavorare sulle loro session per mandare a tutte le loro
	 * session il messaggio
	 */
	
	private Session sessione;
	private Gson gson;
	private static String chat ="";
	private static LocalDateTime start;
	
	@OnOpen
	public void open(Session session)
	{
		this.sessione = session;
		gson = new Gson();
		System.out.println("WebSocket: Connessione Aperta ");
		
		//quando la chat inizia, cioè quando si connetterà la seconda persona, si avvia un timer ti 30 minuti, dopo il quale la chat non permetterà
		//piu di inviare alcun messaggio
		if(session.getOpenSessions().size()==2) {
			start = LocalDateTime.now();
			System.out.println("WebSocket: è iniziato il timer della chat alle:" + start);
		}
	}
	
	@OnClose
	public void close(Session session)
	{
		System.out.println("WebSocket: Connessione Chiusa ");
		
	}
	
	@OnMessage
	public void handleMessage(String message, Session session)throws IOException, EncodeException {
		
		Responce resp = new Responce();
		Request req = gson.fromJson(message, Request.class);
		Duration duration = null;
		
		if(start!= null) {
			//la chat dura solo 30 minuti, dopodiche si chiude 
			 duration = Duration.between(start, LocalDateTime.now());
		}
		
		
		if(duration != null && duration.toMinutes() > 30 && start !=null) {
			resp.setMessage("chat terminata");
			System.out.println("WebSocket: la chat ha superato il tempo limite");	
		}
		else {
			String m = req.getMessage();
			
			if(m.equals("avvio")) {
				resp.setChat(chat);
				resp.setMessage("ok");
			}
			else {
				String text = req.getText();
				System.out.println("WebSocket: arrivato messaggio: " + text);
				
				if(text.startsWith("A") || text.startsWith("S")) {
					resp.setMessage("Operazione impossibile, messaggio proibito");
				}
				else {
					chat = chat + this.sessione.getId() + ":\n" + text + "\n" + "\n";
					resp.setChat(chat);
					resp.setMessage("ok");
				}
			}
		}
		
		this.sendback(resp);
	}
	
	private void sendback(Responce resp) throws IOException, EncodeException {
		// TODO Auto-generated method stub
		try {
			//invio singolo:     this.sessione.getBasicRemote().sendText(message);

			for (Session sess : sessione.getOpenSessions()){ 
				if (sess.isOpen()) {
					sess.getBasicRemote().sendText(gson.toJson(resp));
				}
			} 
		} catch (IOException  e) {
            e.printStackTrace();
        }
	}

	@OnError
	public void onError(Session session, Throwable throwable)
	{
		throwable.printStackTrace();
	}

}

package servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.websocket.*;
import javax.websocket.server.*;

import com.google.gson.Gson;

import beans.HitUserReq;
import beans.HitUserResultReq;
import beans.HitServerResp;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/server")
public class BattleShipServer {
	
	private Session sessione;
	private static Gson g;
	private static Map<String, Integer> users = new HashMap<String,Integer>();
	private static final Set<BattleShipServer> calcEndpoints = new CopyOnWriteArraySet<>();
	
	private static int current_turn = 1;
	private static int turn_order = 1;
	private static int received_responses = 0;
	private static int max_users = 4;
	
	
	@OnOpen
	public void open(Session session) throws IOException, EncodeException
	{
		System.out.println("Ello");
		this.sessione = session;
		g = new Gson();
		if (users.size() >= max_users) {
			try {
			      HitServerResp _HitServerResp = new HitServerResp("error", "Troppi utenti già connessi");
			      sendback(g.toJson(_HitServerResp));
			      session.close();
			    } catch (IOException ex) {
			    	System.out.println("Error while refusing connection" + ex.toString());
			    }
			return;
		}
		users.put(session.getId(), turn_order);
		System.out.println("Connessione Aperta con " + session.getId() + ", turn_order = " + turn_order);
		System.out.println("users" + users.toString());
		System.out.println("users size" + users.size());
		System.out.println("---------------------");
		HitServerResp _HitServerResp = new HitServerResp("client_id_assignment", session.getId());
		sendback(g.toJson(_HitServerResp));
		turn_order++;
		calcEndpoints.add(this);
	}
	
	@OnClose
	public void close(Session session)
	{
		System.out.println("Connessione Chiusa con " + session.getId());
		users.remove(session.getId());
		turn_order--;
	}
	
	@OnMessage
	public void handleMessage(String message, Session session)throws IOException, EncodeException {
		
		System.out.println("Messaggio: "+message.toString());
		if(message.contains("hit_user_request"))
		{
			HitServerResp _hitServerResp;
			// check if user can fire
			if (users.get(session.getId()) != current_turn) {
				// user cannot play
				_hitServerResp = new HitServerResp("error", "Non è il tuo turno!");
				sendback(g.toJson(_hitServerResp));
				return;
			}
			
			// user can play
			HitUserReq _hitUserReq = g.fromJson(message, HitUserReq.class);
			_hitServerResp = new HitServerResp(
					"hit_server_result_request", 
					"Utente["+session.getId()+"] spara in "+_hitUserReq.getRow()+_hitUserReq.getCol()
			);
			System.out.println("_hitUserReq: " + _hitUserReq.toString());
			System.out.println("_HitServerResp: " + _hitServerResp.toString());
			broadcastButNotSender(g.toJson(_hitServerResp), session.getId());
		}
		else if(message.contains("hit_user_result_request"))
		{
			HitUserResultReq _hitUserResultReq = g.fromJson(message, HitUserResultReq.class);
			received_responses++;
			HitServerResp _hitServerResp = new HitServerResp(
					"hit_user_result", 
					"Utente["+session.getId()+"] riporta "+_hitUserResultReq.getContent()
			);
			broadcast(g.toJson(_hitServerResp));
			if(received_responses == users.size() - 1) {
				received_responses=0;
				_hitServerResp = new HitServerResp(
						"fine_turno", 
						" ---------------- Fine turno ----------- "
				);
				broadcast(g.toJson(_hitServerResp));
				current_turn++;
				if (current_turn > users.size()) current_turn=1;
			}
			
		}
	}

	private void sendback(String message) throws IOException, EncodeException {
		// TODO Auto-generated method stub
		try {
			System.out.println("Sto inviando: "+message);
			this.sessione.getBasicRemote().sendText(message);
			
		} catch (IOException  e) {
            e.printStackTrace();
        }
		
	}

	@OnError
	public void onError(Session session, Throwable throwable)
	{
		System.out.println("Errore ");
	}
	
	private static void broadcast(String message) throws IOException, EncodeException {
		
        calcEndpoints.forEach(endpoint -> {
        	synchronized (endpoint) {
                try {
                    endpoint.sessione.getBasicRemote()
                        .sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
	
	private static void broadcastButNotSender(String message, String sender_sessionId) throws IOException, EncodeException {
		
        calcEndpoints.forEach(endpoint -> {
        	if (endpoint.sessione.getId() != sender_sessionId) {
        		synchronized (endpoint) {
                    try {
                        endpoint.sessione.getBasicRemote()
                            .sendText(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        	}
        });
    }


}

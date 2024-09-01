package servlets;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.ArrayList;
import java.util.List;
import javax.websocket.*;
import javax.websocket.server.*;
import com.google.gson.Gson;
import beans.OperationReq;
import beans.OperationResp;
import beans.Partita;
import beans.Clienti;


@ServerEndpoint("/actions")
public class ProvaWS{
	private Gson gson;
	private OperationResp op_resp;
	private static final Set<ProvaWS> endpoints=new CopyOnWriteArraySet<ProvaWS>();
	private Clienti clients;
	private Session current_session;
	private Partita partita;
	private static Set<Session> sessions =new TreeSet<Session>(); 


	@OnOpen
	public void open(Session session) {
		synchronized (this){endpoints.add(this);}
		gson = new Gson();	
		current_session=session;
		clients=Clienti.getInstance();
	}

	public static Set<Session> getSessions() {
		return sessions;
	}

	public static Set<ProvaWS> getEndpoints() {
		return endpoints;
	}

	@OnMessage
	public void operazione(Session session, String msg) throws IOException {
		OperationReq op_req = gson.fromJson(msg, OperationReq.class);
		op_resp = new OperationResp("",-1);
       
		if(op_req.getOperazione().equals("iscrizione")) {  //login
			if(  partita.getPartita_avviata()==true ) {
				op_resp.setMsg("La partita è già stata avviata");
			}
			else {
				this.clients.addCliente(current_session);
				op_resp.setMsg("Iscrizione eseguita con successo");
			}
		}
		
		
		if(op_req.getOperazione().equals("avvia")) {   //avvia logica di programma
			if( partita.getPartita_avviata()==true ) {
				op_resp.setMsg("C'è già una partita avviata");
			}
			else {  //metodo per verificare se un utente è iscritto
				boolean isIscritto=false;
				for (Session s : clients.getSessioni()) {
					if(s.equals(current_session)){
						isIscritto=true;
						break;
					}
				}
				if(isIscritto==false) {
					op_resp.setMsg("Non puoi avviare una partita se non ti sei iscritto");
				}
				else {
					op_resp.setMsg("Partita avviata");
			    	this.partita.setPartita_avviata(true);
					avviaPartita();
				}
			}
		}
		
		
		if(op_req.getOperazione().equals("abbandona")) {   //remove
			this.clients.removeCliente(current_session);
			op_resp.setMsg("Hai abbandonato la partita con successo");
		}
		
		if(op_req.getOperazione().equals("visualizza")) {   //admin
			List<String> ids=new ArrayList<String>();
			for (Session s : this.clients.getSessioni()) {
				ids.add(s.getId());
			}

			this.current_session.getBasicRemote().sendText(gson.toJson(ids));
		}
		
		if(op_req.getOperazione().equals("elimina")) {   //admin
			for (Session s : clients.getSessioni()) {
		//		if(s.getId().equals(op_req.getGiocatore_da_eliminare())) {
					this.clients.removeCliente(s);
		//		}
			}
			this.current_session.getBasicRemote().sendText(gson.toJson("ho eliminato il giocatore"));
		}
		
		broadcast(op_resp);
		
	}

	//Per inviare un messaggio a tutti i peer connessi ad un endpoint (esempio applicazioni chat):
		@ServerEndpoint("/echoall")
		public class EchoAllEndpoint {
			@OnMessage
			public void onMessage(Session session, String msg) {
				try {
					for(Session sess: session.getOpenSessions()) {
						if(sess.isOpen()) {
							sess.getBasicRemote().sendText(msg);
						}
					}
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
			
			
	//Vari tipi di invio messaggi
		@ServerEndpoint("/receive")
		public class ReceiveEndpoint{
			@OnMessage
			public void textMessage(Session session, String msg) {
				System.out.println("Text message: "+msg);
			}
			@OnMessage
			public void binaryMessage(Session session, ByteBuffer msg) {
				System.out.println("Binary message: "+msg.toString());
			}
			@OnMessage
			public void pongMessage(Session session, PongMessage msg) {
				System.out.println("pong message: "+msg.getApplicationData().toString());;
			}
		}

		//invio di un echo
		@ServerEndpoint("/delayedecho")
		public class DelayedEchoEndpoint{
			@OnOpen
			public void open(Session session) {
				session.getUserProperties().put("previousMsg", " ");
			}
			@OnMessage
			public void message(Session session, String msg) {
				String prev= (String) session.getUserProperties().get("previousMsg");
				try {
					session.getBasicRemote().sendText(prev);
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			}
		}

	
	//invio messaggio gni 60 secondi
	private void avviaPartita() throws IOException {
	 //	boolean x = true;
		try {
			while( partita.getPartita_avviata()==true ) {
				Thread.sleep(1000*60);
				broadcast(op_resp);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//bradcast a tutti quelli collegati
	private void broadcast(Object obj) {
		//utilizzando l'array di istanze
		for(ProvaWS istanza: ProvaWS.endpoints) {
			if(istanza.current_session.isOpen()) {
				try {
					istanza.current_session.getBasicRemote().sendText(gson.toJson(obj));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}


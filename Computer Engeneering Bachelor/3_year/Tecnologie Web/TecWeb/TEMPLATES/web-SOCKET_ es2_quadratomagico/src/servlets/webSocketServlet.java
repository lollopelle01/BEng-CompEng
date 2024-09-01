package servlets;

import java.io.IOException;

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
	//private static Map<Integer, Partita> partite = new HashMap<Integer, Partita>();
	private static int matrix[][] = new int[3][3];
	
	@OnOpen
	public void open(Session session)
	{
		this.sessione = session;
		gson = new Gson();
		System.out.println("WebSocket: Connessione Aperta ");
		
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				matrix[i][j]=0;
			}
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
		
		int row = req.getRow();
		int col = req.getCol();
		int val = req.getValore();
		System.out.println("WebSocket: " + val);
		matrix[row][col]=val;
		
		resp.setCol(col);
		resp.setRow(row);
		resp.setValore(val);
		resp.setMessage("non piena");
		
		//una volta che la matrice è completa la webSocket manda ad una servlet la richiesta, tramite ajax dal client
		int count=0;
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				if(matrix[i][j] != 0) {
					count ++;
				}
			}
		}
				
		if(count ==9) {
			resp.setMessage("piena");		
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

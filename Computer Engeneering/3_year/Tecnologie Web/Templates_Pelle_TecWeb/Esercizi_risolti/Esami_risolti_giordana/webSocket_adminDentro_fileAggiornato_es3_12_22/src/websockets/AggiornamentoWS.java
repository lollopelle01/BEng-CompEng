package websockets;

import java.io.*;
import java.nio.file.Paths;

import javax.websocket.*;
import javax.websocket.server.*;

import com.google.gson.Gson;

import utils.SharedWSSessionManager;

@ServerEndpoint("/ws")
public class AggiornamentoWS {
	private static long oldDim = 0;
	private static Boolean running = false;
	
	// non so come ottenere il path del progetto dentro tomcat da una websocket
	private static File F = new File("C:/Users/Utente/Desktop/apache-tomcat-9.0.22/webapps/2022_12_22D_es3/risultati.txt");
	private static Thread thread = new Thread() {
		public void run() {
			// controlla modifiche
			while (true) {
				try {
					long newDim = F.length();
					if (newDim > oldDim) {
						StringBuilder sb = new StringBuilder();

						FileReader r = new FileReader(F);

						r.skip(oldDim);

						oldDim = newDim;
						int i;
						while ((i = r.read()) != -1) {
							sb.append((char) i);
						}

						SharedWSSessionManager.getInstance().broadcast(sb.toString());
					}

					Thread.sleep(1 * 1000);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	};

	@OnOpen
	public void onOpen(Session sess) {
		SharedWSSessionManager.getInstance().addSession(sess);

		if (!running) {
			thread.start();
			running = true;
		}

	}

	@OnClose
	public void onClose(Session sess) {
		SharedWSSessionManager.getInstance().removeSession(sess);
		if (SharedWSSessionManager.getInstance().getSessions().size() == 0) {
			thread.interrupt();
			running = false;
		}

	}

	@OnMessage
	public void onMessage(String mess, Session sess) throws IOException {
	}

	@OnError
	public void onError(Throwable err, Session sess) {
		err.printStackTrace();

	}
}

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class pellegrino_lorenzo_0000971455_Server { //processo padre che crea Server2 per ogni client
	public static final int PORT = 54321; // porta default per server

	public static void main(String[] args) throws IOException {
		// Porta sulla quale ascolta il server
		int port = -1;

		/* controllo argomenti */
		try {
			if (args.length == 1) {
				port = Integer.parseInt(args[0]);
				// controllo che la porta sia nel range consentito 1024-65535
				if (port < 1024 || port > 65535) {
					System.out.println("Usage: java lorenzo_pellegrino_0000971455_Server1 or java lorenzo_pellegrino_0000971455_Server1 port");
					System.exit(1);
				}
			} else if (args.length == 0) {
				port = PORT;
			} else {
				System.out
					.println("Usage: java lorenzo_pellegrino_0000971455_Server1 or java lorenzo_pellegrino_0000971455_Server1 port");
				System.exit(1);
			}
		} //try
		catch (Exception e) {
			System.out.println("Problemi, i seguenti: ");
			e.printStackTrace();
			System.out
				.println("Usage: java lorenzo_pellegrino_0000971455_Server1 or java lorenzo_pellegrino_0000971455_Server1 port");
			System.exit(1);
		}

		/* preparazione socket e in/out stream */
		ServerSocket serverSocket = null;
		ServerSocket serverSocketThread = null;
		pellegrino_lorenzo_0000971455_Thread thread = null;
		try {
			//serverSocket = new ServerSocket(port,2);
			serverSocket = new ServerSocket(port);
			serverSocket.setReuseAddress(true);
			System.out.println("lorenzo_pellegrino_0000971455_Server1: avviato ");
			System.out.println("Creata la server socket: " + serverSocket);
		}
		catch (Exception e) {
			System.err.println("Problemi nella creazione della server socket: "
					+ e.getMessage());
			e.printStackTrace();
			System.exit(2);
		}
		try {
			//ciclo infinito server
			while (true) {
				Socket clientSocket = null;
				DataOutputStream outSock = null;
				
				// Connessione con client
				System.out.println("\nIn attesa di richieste...");
				try {
					clientSocket = serverSocket.accept();
					clientSocket.setSoTimeout(30000); //timeout altrimenti server sequenziale si sospende
					System.out.println("Connessione accettata: " + clientSocket + "\n");
					outSock = new DataOutputStream(clientSocket.getOutputStream());
					
				}
				catch (SocketTimeoutException te) {
					System.err.println("Non ho ricevuto nulla dal client per 30 sec., interrompo la comunicazione e accetto nuove richieste.");
					continue;
				}
				catch (Exception e) {
					System.err.println("Problemi nella accettazione della connessione: " + e.getMessage());
					e.printStackTrace();
					continue;
				}
				
				// Provo a creare socket per thread
				boolean trovato = false;
				for(int i = 1024; i<65535 && !trovato; i++) { //esploro porte
					try {
						serverSocketThread = new ServerSocket(i);
						trovato=true;
					}catch(Exception e ) {}
				}
				
				// Passo socket al thread
				thread = new pellegrino_lorenzo_0000971455_Thread(serverSocketThread);
				thread.start();
				
				//comunico porta del thread al client
				System.out.println("Creo socket alla porta: " + serverSocketThread.getLocalPort());
				try {
					outSock.writeInt(serverSocketThread.getLocalPort()); // comunico porta al client
				}
				catch(SocketTimeoutException ste){
					System.out.println("Timeout scattato: ");
					ste.printStackTrace();
					clientSocket.close();
					System.out.print("\n^D(Unix)/^Z(Win)+invio per uscire, solo invio per continuare: ");
					// il server continua l'esecuzione riprendendo dall'inizio del ciclo
					continue;          
				}
				catch (IOException e) {
		        	System.out.println("Problemi nella creazione degli stream di input/output su socket:");
		        	e.printStackTrace();
		        	// il server continua l'esecuzione riprendendo dall'inizio del ciclo
		        	continue;
		        }
				
				outSock.close();
				clientSocket.shutdownOutput();
				clientSocket.shutdownInput();
			} // while (true)
		}
		// qui catturo le eccezioni non catturate all'interno del while
		// in seguito alle quali il server termina l'esecuzione
		catch (Exception e) {
			e.printStackTrace();
			// chiusura di stream e socket
			System.out.println("Errore irreversibile, pellegrino_lorenzo_0000971455_Server: termino...");
			System.exit(3);
		}
	} // main
}

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class pellegrino_lorenzo_0000971455_Client {
	public static void main(String[] args) throws IOException {
		   
		InetAddress addr = null;
		int port = -1;
		
		try{ //check args
			if(args.length == 2){
				addr = InetAddress.getByName(args[0]);
				port = Integer.parseInt(args[1]);
			} else{
				System.out.println("Usage: java lorenzo_pellegrino_0000971455_Client serverAddr serverPort");
				System.exit(1);
			}
		} //try
		// Per esercizio si possono dividere le diverse eccezioni
		catch(Exception e){
			System.out.println("Problemi, i seguenti: ");
			e.printStackTrace();
			System.out.println("Usage: java lorenzo_pellegrino_0000971455_Client serverAddr serverPort");
			System.exit(2);
		}
		
		// oggetti utilizzati dal client per la comunicazione e la lettura del file
		// locale
		Socket socket = null;
		DataInputStream inSock = null;
		DataOutputStream outSock = null;
		String servizio = null;
		int porta_risp;
		
		// creazione stream di input da tastiera
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Che servizio vuoi usare? (conta_file, trasferisci_file)");
		
		//variabili per servizio 'conta_file'
		char carattere;
		int occ, risposta;
		
		//variabili per servizio 'trasferisci_file'
		String direttorio, fileName;
		int num_file, buffer, byte_letti;
		long lunghezza_file;
		DataOutputStream dos;
		//DataInputStream dis;

		try{
			while ( (servizio=stdIn.readLine()) != null){
				// se il servizio è corretto creo la socket --> risparmio risorse
				if( servizio.compareTo("conta_file")==0 || servizio.compareTo("trasferisci_file")==0) {
					
					// creazione socket
					try{
						socket = new Socket(addr, port);
						socket.setSoTimeout(30000);
						System.out.println("Creata la socket: " + socket);
					}
					catch(Exception e){
						System.out.println("Problemi nella creazione della socket: ");
						e.printStackTrace();
						System.out.print("Che servizio vuoi usare? (conta_file, trasferisci_file)");
						continue;
					}
					
					// creazione stream di input/output su socket
					try{
						inSock = new DataInputStream(socket.getInputStream());
						//outSock = new DataOutputStream(socket.getOutputStream());
					}
					catch(IOException e){
						System.out.println("Problemi nella creazione degli stream su socket: ");
						e.printStackTrace();
						System.out.println("Che servizio vuoi usare? (conta_file, trasferisci_file)");
						continue;
					}
					
					
					if(servizio.compareTo("conta_file")==0) {
	
						//raccolgo parametri
						System.out.println("Che carattere vuoi che venga considerato per il conteggio?");
						carattere = stdIn.readLine().charAt(0);
						System.out.println("Quante volte almeno deve essere presente?");
						occ = Integer.parseInt(stdIn.readLine());
						
						//ricezione porta su cui risponderà il server-thread
						try {
							porta_risp = inSock.readInt();
							System.out.println("Il server risponderà alla porta " + porta_risp);
							inSock.close();
							socket.close();
						}catch(SocketTimeoutException ste){
							System.out.println("Timeout scattato: ");
							ste.printStackTrace();
							socket.close();
							System.out.println("Che servizio vuoi usare? (conta_file, trasferisci_file)");
							continue;         
						}
						catch(Exception e){
							System.out.println("Problemi nella ricezione dell'esito, i seguenti: ");
							e.printStackTrace();
							socket.close();
							System.out.println("Che servizio vuoi usare? (conta_file, trasferisci_file)");
							continue;
						}
						
						//creo socket con server-thread
						try{
							socket = new Socket(addr, porta_risp);
							socket.setSoTimeout(30000);
							System.out.println("Creata la socket2: " + socket);
						}
						catch(Exception e){
							System.out.println("Problemi nella creazione della socket2: ");
							e.printStackTrace();
							System.out.println("Che servizio vuoi usare? (conta_file, trasferisci_file)");
							continue;
						}
						
						// creazione stream di input/output su socket2
						try{
							inSock = new DataInputStream(socket.getInputStream());
							outSock = new DataOutputStream(socket.getOutputStream());
						}
						catch(IOException e){
							System.out.println("Problemi nella creazione degli stream su socket2: ");
							e.printStackTrace();
							System.out.println("Che servizio vuoi usare? (conta_file, trasferisci_file)");
							continue;
						}
						
						//invio parametri al server
						try {
							outSock.writeChar(carattere);
							System.out.println("Ho inviato il carattere " + carattere);
							outSock.writeInt(occ);
							System.out.println("Ho inviato l'intero " + occ);
						}catch(Exception e) {
							System.out.println("Errore nell'invio dei parametri al server");
							e.printStackTrace();
							System.out.println("Che servizio vuoi usare? (conta_file, trasferisci_file)");
							continue;
						}
						
						//ricezione risposta
						try {
							System.out.println("Attendo il risultato dal thread...");
							risposta = inSock.readInt();
							System.out.println("Sono state trovate " + risposta + 
									" linee che presentano il carattere " + carattere + 
									" almeno " + occ + " volte");
							socket.close();
						}catch(SocketTimeoutException ste){
							System.out.println("Timeout scattato: ");
							ste.printStackTrace();
							socket.close();
							System.out.println("Che servizio vuoi usare? (conta_file, trasferisci_file)");
							continue;         
						}
						catch(Exception e){
							System.out.println("Problemi nella ricezione dell'esito, i seguenti: ");
							e.printStackTrace();
							socket.close();
							System.out.println("Che servizio vuoi usare? (conta_file, trasferisci_file)");
							continue;
						}
					}
					
					else { //trasferisci_file
						
						//raccolgo parametri
						System.out.println("Su quale direttorio vuoi operare?");
						direttorio = stdIn.readLine();
						
						//ricezione porta su cui risponderà il server-thread
						try {
							porta_risp = inSock.readInt();
							System.out.println("Il server risponderà alla porta " + porta_risp);
							socket.close();
						}catch(SocketTimeoutException ste){
							System.out.println("Timeout scattato: ");
							ste.printStackTrace();
							socket.close();
							System.out.println("Che servizio vuoi usare? (conta_file, trasferisci_file)");
							continue;         
						}
						catch(Exception e){
							System.out.println("Problemi nella ricezione dell'esito, i seguenti: ");
							e.printStackTrace();
							socket.close();
							System.out.println("Che servizio vuoi usare? (conta_file, trasferisci_file)");
							continue;
						}
						
						//creo socket con server-thread
						try{
							socket = new Socket(addr, porta_risp);
							socket.setSoTimeout(30000);
							System.out.println("Creata la socket2: " + socket);
						}
						catch(Exception e){
							System.out.println("Problemi nella creazione della socket2: ");
							e.printStackTrace();
							System.out.println("Che servizio vuoi usare? (conta_file, trasferisci_file)");
							continue;
						}
						
						// creazione stream di input/output su socket2
						try{
							inSock = new DataInputStream(socket.getInputStream());
							outSock = new DataOutputStream(socket.getOutputStream());
						}
						catch(IOException e){
							System.out.println("Problemi nella creazione degli stream su socket2: ");
							e.printStackTrace();
							System.out.println("Che servizio vuoi usare? (conta_file, trasferisci_file)");
							continue;
						}
						
						//invio parametri al server
						try {
							outSock.writeUTF(direttorio);
							System.out.println("Ho inviato il direttorio " + direttorio);
						}catch(Exception e) {
							System.out.println("Errore nell'invio dei parametri al server");
							e.printStackTrace();
							System.out.println("Che servizio vuoi usare? (conta_file, trasferisci_file)");
							continue;
						}
						
						//ricezione risposta --> lettura di file binari
						try {
							System.out.println("Attendo il risultato dal thread...");
							num_file = inSock.readInt();
							System.out.println("File trovati: " + num_file);
							
							for(int i = 0; i < num_file; i++) {
								fileName = inSock.readUTF();
								System.out.println(i+ ") Nome: " + fileName);
								
								File file = new File(fileName); //creazione del file
								file.createNewFile();
								
								lunghezza_file = inSock.readLong();
								System.out.println(i+ ") lunghezza: " + lunghezza_file);
								
								dos = new DataOutputStream(new FileOutputStream(file));
								//dis = new DataInputStream(inSock);
								byte_letti = 0;
								System.out.println(i+ ") contenuto: ");
								while((buffer = inSock.read())>=0 && byte_letti<lunghezza_file) {
									System.out.print((char)buffer);
									dos.write(buffer);
									byte_letti++;
								}
								dos.flush();
							}
						}catch(SocketTimeoutException ste){
							System.out.println("Timeout scattato: ");
							ste.printStackTrace();
							socket.close();
							System.out.println("Che servizio vuoi usare? (conta_file, trasferisci_file)");
							continue;         
						}
						catch(Exception e){
							System.out.println("Problemi nella ricezione dell'esito, i seguenti: ");
							e.printStackTrace();
							socket.close();
							System.out.println("Che servizio vuoi usare? (conta_file, trasferisci_file)");
							continue;
						}
					}
					inSock.close();
					outSock.close();
				}
				// se la richiesta non è corretta non proseguo
				else{System.out.println("Richiesta non valida: <" + servizio + ">");}
				System.out.println("Che servizio vuoi usare? (conta_file, trasferisci_file)");
			}
			socket.close();
			System.out.println("PutFileClient: termino...");
		}
		// qui catturo le eccezioni non catturate all'interno del while
		// quali per esempio la caduta della connessione con il server
		// in seguito alle quali il client termina l'esecuzione
		catch(Exception e){
			System.err.println("Errore irreversibile, il seguente: ");
			e.printStackTrace();
			System.err.println("Chiudo!");
			System.exit(3); 
	    }
	} // main
}

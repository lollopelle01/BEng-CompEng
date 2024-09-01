import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class pellegrino_lorenzo_0000971455_Thread extends Thread{ 
	private ServerSocket socket;
	
	public pellegrino_lorenzo_0000971455_Thread(ServerSocket s) {
		this.socket = s;
	}
	
	//codice
	@Override
	public void run() {
		// strutture dati per connessione
		Socket clientSocket = null;
		DataInputStream inSock = null;
		DataOutputStream outSock = null;
		
		// strutture dati per elaborazione dati
		File folder=null;
		BufferedReader br = null;
		DataInputStream dis = null;
		int count_linee, count_occ, min_occ, i, byte_letti, buffer, file_trovati=0;
		char carattere;
		long lunghezza_file;
		String direttorio = null, line = null, fileName=null;
		
		// Connessione con client
		System.out.println("\nThread:\nAspetto il client...");
		try {
			clientSocket = this.socket.accept();
			clientSocket.setSoTimeout(30000); //timeout altrimenti server sequenziale si sospende
			System.out.println("Connessione accettata: " + clientSocket + "\n");
			inSock = new DataInputStream(clientSocket.getInputStream());
			outSock = new DataOutputStream(clientSocket.getOutputStream());
		}
		catch (SocketTimeoutException te) {
			System.err.println("Non ho ricevuto nulla dal client per 30 sec., interrompo la comunicazione e accetto nuove richieste.");
		}
		catch (Exception e) {
			System.err.println("Problemi nella accettazione della connessione: " + e.getMessage());
			e.printStackTrace();
		}
		
		// lettura servizio
		String servizio = null;
		try {
			servizio=inSock.readUTF();
		} catch (IOException e1) {
			System.out.println("Errore nella lettura del servizio");
			e1.printStackTrace();
		}
		System.out.println("Ho ricevuto il servizio: " + servizio);
		
		// lettura parametri, elaborazione dati e trasferimento risultato
		if(servizio.compareTo("conta_file")==0) {
			try {
				//lettura parametri
				carattere = inSock.readChar();
				min_occ = inSock.readInt();
				
				//elaborazione
				folder = new File(".");
				count_linee=0;
				for(File entry : folder.listFiles()) {
					if(entry.getAbsolutePath().endsWith(".txt")) { //considero solo file.txt
						br = new BufferedReader(new FileReader(entry));
						while((line=br.readLine())!=null) {
							count_occ=0;
							for(i=0; i<line.length(); i++) {
								if(line.charAt(i)==carattere) {
									count_occ++;
								}
							}
							if(count_occ >= min_occ) {
								count_linee++;
							}
						}
					}
				}
				
				//invio risultato
				outSock.writeInt(count_linee);
				
				//chiudo connessione
				inSock.close();
				outSock.close();
				clientSocket.shutdownOutput(); 
				clientSocket.shutdownInput(); 
			}catch(Exception e) {
				System.out.println("Errore durante conta_file: ");
				e.printStackTrace();
			}
		}
		else { //trasferisci_file
			try {
				//lettura parametro
				direttorio = inSock.readUTF();
				System.out.println("Lavoro sul direttorio: " + direttorio);
				
				//elaborazione
				folder = new File(direttorio);
				for(File entry : folder.listFiles()) {
					if(!entry.getAbsolutePath().endsWith(".txt")) { //considero solo file binari
						file_trovati++;
					}
				}
				outSock.write(file_trovati);
				//System.out.println("Numero di file trovati: " + file_trovati);
				
				for(File entry : folder.listFiles()) {
					byte_letti=0;
					if(!entry.getAbsolutePath().endsWith(".txt")) { //considero solo file binari
						
						fileName = entry.getName();
						outSock.writeUTF(fileName);
						//System.out.println("Nome: " + fileName);
						
						lunghezza_file = entry.length();
						outSock.writeLong(lunghezza_file);
						//System.out.println("Lunghezza: " + lunghezza_file);
						
						dis = new DataInputStream(new FileInputStream(entry));
						//System.out.println("Contenuto del file:");
						while((buffer=dis.read())>=0 && byte_letti<=lunghezza_file) {
							//System.out.print((char)buffer);
							outSock.write(buffer);
							byte_letti++;
						}
						outSock.flush();
						//System.out.println("Byte letti: " + byte_letti);
					}
				}
				
				//chiudo la connessione
				inSock.close();
				outSock.close();
				clientSocket.shutdownOutput(); 
				clientSocket.shutdownInput();  
				
				System.out.println("Thread: termino...");
			}catch(Exception e) {
				System.out.println("Errore durante trasferisci_file: ");
				e.printStackTrace();
			}
		}
	}
}

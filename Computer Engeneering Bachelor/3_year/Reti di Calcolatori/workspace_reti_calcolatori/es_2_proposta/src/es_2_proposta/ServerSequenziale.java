package es_2_proposta;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSequenziale {
	public static final int PORTA = 40000;
	
	public static void main(String[] args) {
		//	Non faccio controlli perchè la porta sarà di default per semplicità
		
		//	ATTENZIONE: inserisci qui le tue variabili
		ServerSocket socketServer = null;
		Socket socketClient = null;
		DataInputStream inSock = null;
		DataOutputStream outSock = null;
		FileOutputStream outFile = null;
		
		boolean EOF_raggiunto = false;
		String fileName = null;
		int dimensione = -1, controllo = -1, byteCount = 0;;
		byte byteFile;
		File fileDiTurno = null;
		
		//	1) Creazione della socket
		try {
			socketServer = new ServerSocket(PORTA);
			socketServer.setReuseAddress(true);
			System.out.println("Server: avviato ");
			System.out.println("Creata la server socket: " + socketServer);
		} catch (IOException e) {
			System.out.println("Errore nella creazione della socket");
			e.printStackTrace();
			System.exit(1);
		}
		
		//	2) Rendo la socket sempre operativa
		while(true) {
			
			//	2.1) Socket in attesa della connessione col cliente
			try {
				System.out.println("\nIn attesa di richieste...");
				socketClient = socketServer.accept();
				//	potrei settare un timeout
				System.out.println("Accettata connessione con; " + socketClient);
			} catch (IOException e) {
				System.out.println("Errore durante la ricezione della socket del cliente");
				e.printStackTrace();
				System.exit(2);
				continue;
			}
			
			//	2.2) Preparazione di I/O
			try {
				inSock = new DataInputStream(socketClient.getInputStream());
				outSock = new DataOutputStream(socketClient.getOutputStream());
			} catch (IOException e) {
				System.out.println("errore nella preparazione I/O");
				e.printStackTrace();
				continue;
			}
			
			//	2.3)	Gestione trasferimento di ogni file
			try {
				while(!EOF_raggiunto) {
					
					//	2.3.1)	Ricezione del nome del file
					System.out.println("\nSta per arrivare il nome di un file");
					try {
						fileName = inSock.readUTF(); //si blocca qui dopo la prima lettura di un file
						System.out.println("Nome del file arrivato: " + fileName);
					} catch (IOException e) {
						System.out.println("Errore nella ricezione del nome del file");
						e.printStackTrace();
						EOF_raggiunto = true;
					}
					
					fileDiTurno = new File(fileName);
					if(fileDiTurno.exists()) {
						System.out.println("File già presente, comunico di saltarlo");
						try {
							outSock.writeUTF("salta file");
						} catch (IOException e) {
							System.out.println("Errore nell'invio della risposta (negativa)");
							e.printStackTrace();
							EOF_raggiunto = true;
							continue;
						}
						continue;
					}
					else {
						System.out.println("Non presente, comunico di leggerlo");
						try {
							outSock.writeUTF("attiva");
						} catch (IOException e) {
							System.out.println("Errore nell'invio della risposta (positiva)");
							e.printStackTrace();
							EOF_raggiunto = true;
							continue;
						}
						
						//	2.3.2)	Ricezione della dimensione del file
						try {
							dimensione = inSock.readInt();
							System.out.println("Dimensione: " + dimensione);
						} catch (IOException e) {
							System.out.println("Errore nella lettura della dimensione");
							e.printStackTrace();
							EOF_raggiunto = true;
							continue;
						}
						
						//	2.3.3)	Lettura e scrittura del contenuto
						outFile = new FileOutputStream(fileDiTurno);
						for(int i=0; i<dimensione; i++) {
							outFile.write(inSock.read());
						}
						outFile.close();
					}
				} //while 
			}catch(Exception e) {
				e.printStackTrace();
				EOF_raggiunto = true;
				continue;
			}
		} // while(true)
	}
}

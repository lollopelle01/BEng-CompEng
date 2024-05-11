package es_2_proposta;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	public static void main(String[] args) {
		if(args.length!=3) {
			System.out.println("Errore nel numero di argomenti");
			System.exit(1);
		}
		
		int portaServer = -1;
		InetAddress ip = null;
		String directory = null;
		File cartella = null;
		File[] files = null;
		
		
		try {
			ip = InetAddress.getByName(args[0]);
			portaServer = Integer.parseInt(args[1]);
			directory = args[2];
			cartella = new File(directory);
			files = cartella.listFiles();
			
		} catch (Exception e) {
			System.out.println("Errore nell'inserimento di PORTA e/o IP");
			e.printStackTrace();
		}
		
		// ATTENZIONE: inserire qui le variabili
		Socket socket = null;
		FileInputStream inFile = null;
		DataInputStream inSock = null;
		DataOutputStream outSock = null;
		String nomeFile = null;
		int dimensioneInt = -1;
		long dimensioneLong = -1;
		
		//	1) Preparo la socket
		try {
			socket = new Socket(ip, portaServer);
			inSock = new DataInputStream(socket.getInputStream());
			outSock = new DataOutputStream(socket.getOutputStream());
			System.out.println("Creata la socket: " + socket);
			
		} catch (Exception e) {
			System.out.println("Errore nella preparazione della socket");
			e.printStackTrace();
		}
		
		for(int i=0; i<files.length; i++) {	// 2) Generalizzo il processo di scambio file per ogni file nella cartella
			
			//	2.1) Invio parametri
			try {
				System.out.println("provo a inviare: " + files[i]);
				outSock.writeUTF(files[i].getName());
				System.out.println("Ho inviato il nome del file");
				if(inSock.readUTF().compareTo("attiva")!=0) {
					System.out.println("File: "+files[i]+" saltato");
					continue;
				}
				else {
					dimensioneInt = (int) files[i].length();
					System.out.println("dimensione (int): "+dimensioneInt);
					outSock.writeInt(dimensioneInt);
					inFile = new FileInputStream(files[i]);
					trasferisci_a_byte_file_binario(new DataInputStream(inFile), outSock);
					System.out.println("File: "+files[i]+" inviato");
				}
			} catch(Exception e) {
				System.out.println("Errore nell'invio del file: "+files[i]);
				continue;
			}	
		}
		try {
			inFile.close();
			outSock.close();
			
			socket.close();
		} catch (IOException e) {
			System.out.println("Errore nella chiusura della socket");
			e.printStackTrace();
		}
	}

	static protected void trasferisci_a_byte_file_binario(DataInputStream src,
			DataOutputStream dest) throws IOException {
	
		// ciclo di lettura da sorgente e scrittura su destinazione
	    int buffer;    
	    try {
	    	// esco dal ciclo all lettura di un valore negativo -> EOF
	    	// N.B.: la funzione consuma l'EOF
	    	while ((buffer=src.read()) >= 0) {
	    		dest.write(buffer);
	    	}
	    	dest.flush();
	    }
	    catch (EOFException e) {
	    	System.out.println("Problemi, i seguenti: ");
	    	e.printStackTrace();
	    }
	}
}

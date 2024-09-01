// LineServer.java

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.StringTokenizer;

public class LineServer {

	// porta nel range consentito 1024-65535!
	// dichiarata come statica perch� caratterizza il server
	private static final int PORT = 4445;

	public static void main(String[] args) {

		System.out.println("LineServer: avviato");

		DatagramSocket socket = null;
		DatagramPacket packet = null;
		byte[] buf = new byte[256];
		int port = -1;

		// controllo argomenti input: 0 oppure 1 argomento (porta)
		if ((args.length == 0)) {
			port = PORT;
		} else if (args.length == 1) {
			try {
				port = Integer.parseInt(args[0]);
				// controllo che la porta sia nel range consentito 1024-65535
				if (port < 1024 || port > 65535) {
					System.out.println("Usage: java LineServer [serverPort>1024]");
					System.exit(1);
				}
			} catch (NumberFormatException e) {
				System.out.println("Usage: java LineServer [serverPort>1024]");
				System.exit(1);
			}
		} else {
			System.out.println("Usage: java LineServer [serverPort>1024]");
			System.exit(1);
		}

		try {
			socket = new DatagramSocket(port);
			packet = new DatagramPacket(buf, buf.length);
			System.out.println("Creata la socket: " + socket);
		}
		catch (SocketException e) {
			System.out.println("Problemi nella creazione della socket: ");
			e.printStackTrace();
			System.exit(1);
		}

		try {
			String nomeFile = null;
			int numLinea = -1;
			String richiesta = null;
			ByteArrayInputStream biStream = null;
			DataInputStream diStream = null;
			StringTokenizer st = null;
			ByteArrayOutputStream boStream = null;
			DataOutputStream doStream = null;
			String linea = null;
			byte[] data = null;

			while (true) {
				System.out.println("\nIn attesa di richieste...");
				
				// ricezione del datagramma
				try {
					packet.setData(buf);
					socket.receive(packet);
				}
				catch (IOException e) {
					System.err.println("Problemi nella ricezione del datagramma: "
							+ e.getMessage());
					e.printStackTrace();
					continue;
					// il server continua a fornire il servizio ricominciando dall'inizio
					// del ciclo
				}

				try {
					biStream = new ByteArrayInputStream(packet.getData(), 0, packet.getLength());
					diStream = new DataInputStream(biStream);
					richiesta = diStream.readUTF();
					st = new StringTokenizer(richiesta);
					nomeFile = st.nextToken();
					numLinea = Integer.parseInt(st.nextToken());
					System.out.println("Richiesta linea " + numLinea + " del file " + nomeFile);
				}
				catch (Exception e) {
					System.err.println("Problemi nella lettura della richiesta: "
						+ nomeFile + " " + numLinea);
					e.printStackTrace();
					continue;
					// il server continua a fornire il servizio ricominciando dall'inizio
					// del ciclo
				}

				// preparazione della linea e invio della risposta
				try {
					linea = getLine(nomeFile, numLinea);
					boStream = new ByteArrayOutputStream();
					doStream = new DataOutputStream(boStream);
					doStream.writeUTF(linea);
					data = boStream.toByteArray();
					packet.setData(data, 0, data.length);
					socket.send(packet);
				}
				catch (IOException e) {
					System.err.println("Problemi nell'invio della risposta: "
				      + e.getMessage());
					e.printStackTrace();
					continue;
					// il server continua a fornire il servizio ricominciando dall'inizio
					// del ciclo
				}

			} // while

		}
		// qui catturo le eccezioni non catturate all'interno del while
		// in seguito alle quali il server termina l'esecuzione
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("LineServer: termino...");
		socket.close();
	}
	
	static String getLine(String nomeFile, int numLinea) {
		String linea = null;
		BufferedReader in = null;

	if( numLinea <= 0 ) 
		return linea = "Linea non trovata: numero linea maggiore di 0.";
	// associazione di uno stream di input al file da cui estrarre le linee
	try {
		in = new BufferedReader(new FileReader(nomeFile));
		System.out.println("File aperto: " + nomeFile);
	} catch (FileNotFoundException e) {
		System.out.println("File non trovato: ");
		e.printStackTrace();
		return linea = "File non trovato";
	}
	try {
		for (int i = 1; i <= numLinea; i++) {
			linea = in.readLine();
			if (linea == null) {
				linea = "Linea non trovata";
				in.close();
				return linea;
			}
		}
	} catch (IOException e) {
		System.out.println("Linea non trovata: ");
		e.printStackTrace();
		return linea = "Linea non trovata";
	}
	System.out.println("Linea selezionata: " + linea);
	
	try {
		in.close();
	}
	catch (IOException e) {
		System.out.println("Errore nella chiusura del reader");
		e.printStackTrace();
	}
    return linea;
  } // getLine

}
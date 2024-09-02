package es_1_proposta;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class RowSwapClient {
	public static void main(String[] args) {
		
	//	1) controllo argomemnti -> ip , porta , file
		InetAddress ip = null;
		int port = -1;
		String fileName = null;
		
		if(args.length!=3) {
			System.out.println("Errore numero argomenti");
			System.exit(1);
		}
		else {
			try {
				ip = InetAddress.getByName(args[0]);
				port = Integer.parseInt(args[1]);
				fileName = args[2];
			} catch (Exception e) {
				System.out.println("Errore inserimento argomenti");
				e.printStackTrace();
				System.exit(2);
			}
		}
		
		
	// IMPORTANTE: dichiara qui tutto quello che userai	
		DatagramSocket socket = null;
		DatagramPacket packet = null;
		
		byte[] buf = null;
		
		ByteArrayOutputStream boStream = null;
		DataOutputStream doStream = null;
		
		ByteArrayInputStream biStream = null;
		DataInputStream diStream = null;
		
		int porta = -1 , esito = -1;
		
		String line = null;
		
		BufferedReader bir;
		
		
	//	2) creazione socket-datagram e datagram-packet
		try {
			buf = new byte[256];
			socket = new DatagramSocket();
			socket.setSoTimeout(30*1000);
			packet = new DatagramPacket(buf, buf.length, ip, port);
			System.out.println("\nRowSwapClient creato");
			System.out.println("Socket creata: "+socket);
		} catch (Exception e) {
			System.out.println("Problemi nella creazione delle socket");
			e.printStackTrace();
			System.out.println("Interrompo...");
			System.exit(1);
		}
	
	
	//	3) invio fileName a DiscoveryServer
		try {
			boStream = new ByteArrayOutputStream();
			doStream = new DataOutputStream(boStream);
			doStream.writeUTF(fileName);
			buf = boStream.toByteArray();
			doStream.close();
			boStream.close();
			packet.setData(buf);
			socket.send(packet);
			System.out.println("Richiesta inviata a " + ip + ", " + port);
		} catch (Exception e) {
			System.out.println("Problemi nell'invio della richiesta: ");
			e.printStackTrace();
		}
	
	
	//	4) ricevo porta RowSwapServer da DiscoveryServer
		try {
			packet.setData(buf);
			socket.receive(packet);
		} catch (Exception e) {
			System.out.println("Problemi nella ricezione del datagramma: ");
			e.printStackTrace();
		}
	
	
	// 5) leggo porta RowSwapserver
		try {
			biStream = new ByteArrayInputStream(packet.getData(), 0, packet.getLength());
			diStream = new DataInputStream(biStream);
			porta = diStream.readInt();
			System.out.println("Risposta: la porta di RS è " + porta);
		} catch (Exception e) {
			System.out.println("Problemi nella lettura della risposta: ");
			e.printStackTrace();
		}
	
	
	//	6) chiedo a RowSwapServer inversione delle righe x e y
		try { 
			boStream = new ByteArrayOutputStream();
			doStream = new DataOutputStream(boStream);
			
			socket = new DatagramSocket();
			packet = new DatagramPacket(buf, buf.length, ip, porta);
			
			System.out.println("Quali righe vuoi invertire? (2 numeri separati da spazio e poi invio)");
			bir = new BufferedReader(new InputStreamReader(System.in));
			line = bir.readLine();
			doStream.writeUTF(line);
			buf = boStream.toByteArray();
			doStream.close();
			boStream.close();
			packet.setData(buf);
			socket.send(packet);
			System.out.println("Richiesta inviata a " + ip + ", " + porta + ": Inversione di " + line);
		} catch (Exception e) {
			System.out.println("Problemi nell'invio della righe da scambiare: ");
			e.printStackTrace();
		}
	
	
	//	7) ricevo da RowSwapServer intero con esito
		try {
			packet.setData(buf);
			socket.receive(packet);
			System.out.println("esito ricevuto");
		} catch (Exception e) {
			System.out.println("Problemi nella ricezione del datagramma: ");
			e.printStackTrace();
		}
	
	
	// 8) leggo esito da RowSwapserver
		try {
			biStream = new ByteArrayInputStream(packet.getData(), 0, packet.getLength());
			diStream = new DataInputStream(biStream);
			esito = diStream.readInt();
			
			diStream.close();
			biStream.close();
			System.out.println("Risposta: la porta di RS è " + esito);
		} catch (Exception e) {
			System.out.println("Problemi nella lettura della risposta: ");
			e.printStackTrace();
		}
		
	//	9) stampo risultato se esito positivo
	//...	
	}
}
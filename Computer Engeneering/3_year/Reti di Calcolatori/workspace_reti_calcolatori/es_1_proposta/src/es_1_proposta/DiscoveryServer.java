package es_1_proposta;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class DiscoveryServer {
	
	public static void main(String[] args) {
		
		System.out.println("DiscoveryServer: avviato");
		
		int portaDiscovery = -1;
		int[] porteRS = null;
		String[] fileNamesRS = null;
		
		
		//	1) controllo degli argomenti
		if(args.length<=2) {
			System.out.println("Errore numero argomenti");
			System.exit(1);
		}
		else if(args.length%2==0) {
			System.out.println("Il numero di argomenti deve essere dispari: porta + N coppie");
			System.exit(1);
		}
		else {
			porteRS = new int[(args.length-1)/2];
			fileNamesRS = new String[(args.length-1)/2];
			boolean doppioneTrovato = false;
			
			for(int i=0; i<args.length; i++) {
				if(i%2==0) { //sono porte
					for(int j=0; j<args.length; j+=2) {
						if(args[i]==args[j] && i!=j) {doppioneTrovato=true;}
					}
				}
				else { //sono file
					for(int j=1; j<args.length; j++) {
						if(args[i]==args[j] && i!=j) {doppioneTrovato=true;}
					}
				}
			}
			
			if(doppioneTrovato) {
				System.out.println("Doppione trovato");
				System.exit(2);
			}
			
			try {portaDiscovery = Integer.parseInt(args[0]);}
			catch(Exception e) {
				System.out.println("Porta illeggibile");
				System.exit(2);
			}
			
			if(portaDiscovery<1024 || portaDiscovery>65535) {
				System.out.println("la porta deve essere tra 1024 e 65535");
				System.exit(2);
			}
			
			int j=0, k=0;
			for(int i=1; i<args.length; i++) {
				if(i%2!=0)	{ //sono file
					fileNamesRS[k]=args[i];
					k++;
					
				}
				else { //sono porte
					try {
						porteRS[j]=Integer.parseInt(args[i]);
						if(porteRS[j]<1024 || porteRS[j]>65535) {
							System.out.println("Porte inserite non sono in un range valido (1024-65535)");
							System.exit(2);
						}
						j++;
					}
					catch(Exception e) {
						System.out.println("Porta numero "+ i +" illeggibile");
						System.exit(2);
					}
				}
			}
			
		}
		
		System.out.println("DENTRO IL SERVER HO:");
		System.out.println("Porta del server: " + portaDiscovery);
		System.out.println("\tNomi File\t\t\tPorta");
		for(int i=0; i<porteRS.length; i++) {
			System.out.println("\t" + fileNamesRS[i] + "\t\t\t" + porteRS[i]);
		}
		
		//ATTENZIONE: ricordati di dichiarare qui le variabili
		DatagramSocket socket = null;
		DatagramPacket packet = null;
		byte[] buf = null;
		ByteArrayInputStream biStream = null;
		DataInputStream diStream = null;
		String fileName = null;
		ByteArrayOutputStream boStream = null;
		DataOutputStream doStream = null;
		int i;
		
		//	2) creazione socket
		try {
			buf = new byte[256];
			socket = new DatagramSocket(portaDiscovery);
			packet = new DatagramPacket(buf, buf.length);
			System.out.println("creata la socket: " + socket);
			
		} catch (SocketException e) {
			System.out.println("Problemi nella creazione della socket");
			e.printStackTrace();System.exit(3);
		}
		
		
		
		//	3.1) Creazione RSthread
		for(i=0; i<porteRS.length; i++) {
			RowSwapServer threadRS = new RowSwapServer(fileNamesRS[i], porteRS[i]);
			threadRS.start(); //qui il thread si deve bloccare ma blocca anche il server
		}
		
		//	3) preparazione a ricevere richiesta da RSclient e a rispondere
			int portaRisposta = -1;
			System.out.println("\nIn attesa di richieste..."); //provarla anche dentro al ciclo (come fa il prof)
		
		try { 	while(true) {
				try {
					packet.setData(buf);
					socket.receive(packet);
				} catch (IOException e) {
					System.out.println("Errore ricezione richiesta cliente");
					e.printStackTrace();
				}
				
				//	4) lettura richiesta 
				try {
					biStream = new ByteArrayInputStream(packet.getData(), 0, packet.getLength());
					diStream = new DataInputStream(biStream);
					fileName = diStream.readUTF();
					System.out.println("Arrivata richiesta per: "+fileName);
					boolean esiste = false;
					
					for(i=0; i<fileNamesRS.length && !esiste; i++) {
						if(fileNamesRS[i].compareTo(fileName)==0) {esiste = true;}
					}
					if(esiste) { // 5) delega a thread la row-swap
						portaRisposta = porteRS[i-1];
						System.out.println("Invierò la porta: " + portaRisposta);
					}
					diStream.close();
					biStream.close();
				}
				catch (IOException e) {
					System.out.println("Errore nella lettura della richiesta");
					e.printStackTrace();
					continue;
				}
	
				//	6) scrittura porta del thread sull'output
				try {
					boStream = new ByteArrayOutputStream();
					doStream = new DataOutputStream(boStream);
					doStream.writeInt(portaRisposta);
					buf = boStream.toByteArray();
					
					doStream.close();
					boStream.close();
				} catch (IOException e) {
					System.out.println("Errore scrittura risposta");
					e.printStackTrace();
				}
				
				// 7) invio porta al cliente
				try {
					packet.setData(buf, 0, buf.length);
					socket.send(packet);
				} catch (IOException e) {
					System.out.println("errore invio risposta");
					e.printStackTrace();
				}
			}
		}catch(Exception e) {
			System.out.println("chiudo scocket...");
			socket.close();
		}
		
	}
}

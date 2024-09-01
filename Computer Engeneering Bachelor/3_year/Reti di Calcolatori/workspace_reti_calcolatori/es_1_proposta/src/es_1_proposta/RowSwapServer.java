package es_1_proposta;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class RowSwapServer extends Thread{
	private String fileName;
	private int porta;
	private File fileVecchio;
	private File fileNuovo;
	
	public RowSwapServer(String fileName, int porta) {
		this.fileName=fileName;
		this.porta=porta;
		this.fileVecchio = new File(this.fileName);
		this.fileNuovo = new File("/Users/pelle/Desktop/temp.txt");
	}
	
	public void run() {
		
		//ATTENZIONE: inserisci qui tutte le variabili
		byte[] buf = null;
		DatagramSocket socket = null;
		DatagramPacket packet = null;
		ByteArrayInputStream biStream = null;
		DataInputStream diStream = null;
		String numeri = null, linea = null, line1 = null, line2 = null;
		int riga1 = -1, riga2 = -1, righeCount = 0, esito = -1;
		
		FileReader fr = null;
		BufferedReader br = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		
		//	1) Apertura della socket
		try {
			buf = new byte[256];
			socket = new DatagramSocket(this.porta);
			packet = new DatagramPacket(buf, buf.length);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		//	2) preparo la ricezione dei 2 numeri e li leggo
		try {
			packet.setData(buf);
			socket.receive(packet);
			biStream = new ByteArrayInputStream(packet.getData(), 0, packet.getLength());
			diStream = new DataInputStream(biStream);
			numeri = diStream.readUTF();
			
		} catch (IOException e) {
			System.out.println("Errore nella lettura dei 2 numeri");
			e.printStackTrace();
		}
		
		//	3) estraggo i 2 numeri
		try {
			System.out.println("La stringa da splittare sarà: "+numeri);
			riga1 = Integer.parseInt(numeri.split(" ")[0]);
			riga2 = Integer.parseInt(numeri.split(" ")[1]);
		}catch(Exception e) {
			System.out.println("Errore nel parsing dei due numeri");
			e.printStackTrace();
		}
		
		//	4.1) apro il file e inverto le righe (primo giro)
		try {
			fr = new FileReader(this.fileVecchio);
			br = new BufferedReader(fr);
			
			while((linea=br.readLine())!=null) {
				righeCount++;
				if(righeCount==riga1) {
					line1=linea;
				}
				else if(righeCount==riga2) {
					line2=linea;
				}
			}
			br.close();
			fr.close();
			
			
		} catch (Exception e) {
			System.out.println("Errore in apertura/lettura del file (prima volta)");
			e.printStackTrace();
		}
		
		righeCount = 0;
		
		//	4.2) apro il file e inverto le righe (secondo giro)
		try {
			fr = new FileReader(this.fileVecchio);
			br = new BufferedReader(fr);
			fw = new FileWriter(this.fileNuovo, true);
			bw = new BufferedWriter(fw);
			
			while((linea=br.readLine())!=null) {
				righeCount++;
				if(righeCount==riga1) {
					bw.write(line2 + "\n");
				}
				else if(righeCount==riga2) {
					bw.write(line1+ "\n");
				}
				else {
					bw.write(linea+ "\n");
				}
			}
			
			br.close();
			fr.close();
			bw.close();
			fw.close();
			
		} catch (Exception e) {
			System.out.println("Errore in apertura/lettura del file (seconda volta)");
			e.printStackTrace();
		}
		
		//	5) cancello file vecchio e rinomino file nuovo
		fileVecchio.delete();
		fileNuovo.renameTo(fileVecchio);
	
		esito=0;	//è andato tutto bene
		
		//	6) preparo output e rispondo al cliente
		try {
			ByteArrayOutputStream boStream = new ByteArrayOutputStream();
			DataOutputStream doStream = new DataOutputStream(boStream);
			doStream.writeInt(esito);
			buf = boStream.toByteArray();
			packet.setData(buf);
			socket.send(packet);
		} catch (Exception e) {
			System.out.println("Errore nella scrittura dell'esito");
			e.printStackTrace();
		}
	}
}

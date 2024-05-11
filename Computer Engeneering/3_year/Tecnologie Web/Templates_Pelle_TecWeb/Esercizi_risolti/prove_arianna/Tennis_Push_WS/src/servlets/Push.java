package servlets;

import java.io.BufferedReader;
import java.io.FileReader;

//questo thread si occupa dell'invio di aggiornamenti ogni qualvolta si aggiunge una riga al testo

public class Push extends  Thread {

	   private volatile boolean running = true;

	   public void run(){
	        
		   try {
			   while(running) {
		        	
		        	BufferedReader br = new BufferedReader(new FileReader("./risultati.txt"));
		 		   	int newCount = 0, oldCount = 0;
		 		   	String line = null;
		        	
		        	do {
			            newCount = 0;
			            
			            while ( (line = br.readLine()) != null) { 
			                newCount++;
			            }
			            br.close();
			            br = new BufferedReader(new FileReader("./risultati.txt"));  
			            
			            Thread.sleep(100);
			            
			        } while(newCount == oldCount); 	//finchè non ci sono aggiornamenti
		        	
		        	ServerWS.broadcast("Nuovo aggiornamento: "+line);
		        	oldCount = newCount;
		        	
		        }
		   } catch(Exception e) {
			   e.printStackTrace();
		   }
	   }

	   public void stopThread() {
	       running = false;
	   }
	}


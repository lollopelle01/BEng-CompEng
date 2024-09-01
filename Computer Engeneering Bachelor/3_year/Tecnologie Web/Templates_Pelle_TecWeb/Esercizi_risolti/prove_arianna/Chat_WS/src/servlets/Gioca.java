package servlets;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//questo thread si occupa dell'estrazione continua dei numeri, ogni 30 sec
public class Gioca extends  Thread {

	   private volatile boolean running = true;

	   public void run(){
		   Random r = new Random();
		   List<Integer> numEstratti = new ArrayList<>();
		   while (running) {
			   int num = r.nextInt(100);
			   if(!numEstratti.contains(num)) {
				   numEstratti.add(num);
				   
				   try {
					   ServerWS.broadcast("estratto il numero "+num);
					   Thread.sleep(30000);
				   } catch (Exception e) {
					   e.printStackTrace();
				   }
				   
			   }
		   }
	   }

	   public void stopThread() {
	       running = false;
	   }
	}


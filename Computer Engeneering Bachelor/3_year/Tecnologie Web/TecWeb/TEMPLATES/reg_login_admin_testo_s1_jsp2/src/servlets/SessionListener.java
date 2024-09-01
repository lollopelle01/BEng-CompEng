package servlets;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener  {
	
	private static int counter = 0;
   
    public void sessionCreated(HttpSessionEvent event)  {
    	SessionListener.counter++;
    }  


	public void sessionDestroyed(HttpSessionEvent event)  {
		SessionListener.counter--;
	}


	public static int getCounter() {
		return counter;
	}


	public static void setCounter(int counter) {
		SessionListener.counter = counter;
	}
	
	
}
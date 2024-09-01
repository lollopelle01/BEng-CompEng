package Servlet;

import java.io.FileWriter;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Beans.CounterResult;
import Beans.Testo;

import com.google.gson.Gson;

public class FIleManager extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private Gson g;
	
	@Override
	public void init(ServletConfig conf) throws ServletException
	{
		super.init(conf);
		g = new Gson();	
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{     
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Testo t = g.fromJson(request.getReader(), Testo.class);
		int estr1 = t.getX();
		System.out.println("Estremo 1 : "  + estr1);
		int estr2 = t.getY();
		System.out.println("Estremo 2 : "  + estr2);
		String testo = t.getTesto();
		System.out.println("testo : "  + testo);
		String filename = t.getFilename();
        StringBuilder sb = new StringBuilder();
    	int count = 0;       // conta numero chars modificati
        
    	synchronized(this)   //per sincronizzare le 2 servlet
		{
	
	  		for( int i=estr1; i<estr2; i++ ) {
				char lettera = testo.charAt(i);
			  if(Character.isLowerCase(lettera)) {
				  sb.append(Character.toUpperCase(lettera));
				  System.out.println("lettera cambiata : "  + lettera);
				  count++;
			  } else {
				  sb.append(lettera);
			   }
		    }
			
			// apre il file in scrittura
			FileWriter fileout = new FileWriter(filename + ".txt", true);     //true per scrivere in append
	         try {
	            System.out.println("Inizio scrittura su file : "  + filename);
	            fileout.write(sb.toString());   //scrive su file
	            
	        } catch (IOException e) {
	            System.out.println(e);
	        } finally {
	            if (fileout != null) {
	            	fileout.close();      // chiude il file
	                System.out.println("Fine scrittura su file : "  + filename);
	            }
	        }
		        
			CounterResult cr = new CounterResult();
	        cr.setCambiati(count);
			String result = g.toJson(cr);
			System.out.println("Risultato da scrivere : " + result);
			response.getWriter().println(result);
		
	   } 
	}

}

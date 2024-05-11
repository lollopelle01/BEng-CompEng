package Servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import java.io.*;

import Bean.Counter;

public class CounterDispatcher extends HttpServlet{
	
	private Gson g;
	
	@Override
	public void init(ServletConfig conf) throws ServletException
	{
		super.init(conf);
		Gson g = new Gson();
		Counter c = new Counter();
		this.getServletContext().setAttribute("counter", c);
		this.getServletContext().setAttribute("index", 0);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	 throws ServletException, IOException {
		
		String testo =  request.getParameter("testo");
		String fileName = request.getParameter("file");
		
		int numberOfCar = trasformLowerToUpper(testo);
		testo = testo.toUpperCase();
		int index;
		synchronized(this)
		{
			index = (Integer)this.getServletContext().getAttribute("index");
			Counter c = (Counter)this.getServletContext().getAttribute("counter");
			
			writeInUpperCase(fileName+".txt", testo); //scrivo la metà del testo nel file
			c.incrementOf(numberOfCar);
			
			if(index%2==0) // se è pari -> prima servlet
			{
				index++;
				int temp = c.getValue();
				c.setValue(-1);
				
				String res = g.toJson(c); 
				c.setValue(temp);
				
				this.getServletContext().setAttribute("index", index);
				this.getServletContext().setAttribute("counter", c);
				
				response.getWriter().println(res); // ancora è stato scritto solo metà testo
			}
			else // se è dispari -> seconda servlet
			{
				String res = g.toJson(c);
				c.reset();
				this.getServletContext().setAttribute("index", 0);
				this.getServletContext().setAttribute("counter", c);
				
				response.getWriter().println(res); // è stato scritto tutto il testo
			}
		}
	}
	
	private int trasformLowerToUpper(String text)
	{
		int counter =0;
		for(int i=0;i<text.length();i++)
		{
			char car = text.charAt(i);
			String ch = car+"";
			if(!ch.equals(ch.toUpperCase()) )
				counter++;
		}
		return counter;
	}

	private void writeInUpperCase(String fileName, String testo) {
		
		BufferedWriter bw = null;
		try {
			
			File f = new File(fileName);
			bw = new BufferedWriter(new FileWriter(f,true)); // funzione append = true
			bw.write(testo);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}

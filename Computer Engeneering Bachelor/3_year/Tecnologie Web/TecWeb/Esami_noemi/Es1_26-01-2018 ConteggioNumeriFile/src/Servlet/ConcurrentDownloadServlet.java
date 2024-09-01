package Servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import Business.DownloaderSlave;

public class ConcurrentDownloadServlet extends HttpServlet {

	/**
	 * 
	 */
	private Gson gson;
	private static final long serialVersionUID = 1L;
	
	@Override
	public void init(ServletConfig conf) throws ServletException
	{
		super.init(conf);
		gson = new Gson();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		int value= Integer.parseInt(request.getParameter("value"));
		if(value < 1 )
		{
			System.out.println("Error..... numero di pagine da scaricare è minore di uno");
			response.sendRedirect(request.getContextPath()+"/home.html");
//			RequestDispatcher rd = getServletContext().getRequestDispatcher("/home.html");			
//			rd.forward(request, response);
			
			return;
		}
		String[] url = new String[value];
		
		session.setAttribute("url", url);
		int counter = 0;
		session.setAttribute("value", value);
		session.setAttribute("counter", counter);
		System.out.println("E' stata ricevuta una richiesta per scaricare "+value+" pagine");
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/url.html");			
		rd.forward(request, response);
		return;
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		Integer counter = (Integer)session.getAttribute("counter");
		System.out.println("Il counter adesso è: "+counter);
		Integer value = (Integer)session.getAttribute("value");
		System.out.println("Mentre il value è: "+value);
		String[] urls = (String[])session.getAttribute("url");
		
		if(counter == null || value == null)
		{
			System.out.println("Si è verificato un errore con i dati");
			return;
		}
			String url = request.getParameter("url");
			System.out.println("E' stato ricevuto il seguente url-> "+url);
			urls[counter] = url;
			counter++;
			session.setAttribute("counter", counter);
			session.setAttribute("url", urls);
			if(counter < value)
			{	
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/url.html");			
				rd.forward(request, response);
				return;
			
			}
		System.out.println("Sono stati ricevuti tutti gli url correttamente");
		int totalCounter = 0;
		DownloaderSlave[] slaves = new DownloaderSlave[value];
		for(int i=0;i< slaves.length;i++)
		{
			slaves[i]= new DownloaderSlave(urls[i], this.getServletContext().getRealPath(""));
			slaves[i].start();
		}
		
		for(int i=0; i<slaves.length;i++)
		{
			try {
				slaves[i].join();
				totalCounter += slaves[i].getCounter();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Tutto OK.. redirecting home");
		session.setAttribute("result", gson.toJson(totalCounter));
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/home.jsp");			
		rd.forward(request, response);
		
		return;
		
		
	}
	

}

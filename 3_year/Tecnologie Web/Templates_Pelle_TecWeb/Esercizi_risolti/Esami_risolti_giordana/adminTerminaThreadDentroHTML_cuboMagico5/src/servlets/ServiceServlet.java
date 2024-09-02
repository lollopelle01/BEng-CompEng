package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import beans.ExecThread;
import beans.Result;


public class ServiceServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7554977297793320847L;
	Gson gson;
	Random random;
	Map<String, ExecThread> threads;
	
	@Override
	public void init() throws ServletException {
		this.gson = new Gson();
		this.random = new Random(System.currentTimeMillis());
		//mappa per il controllo dei thread
		
		if((threads = (Map<String, ExecThread>) getServletContext().getAttribute("threads")) == null ) {
			threads = new HashMap<>();
			getServletContext().setAttribute("threads", threads);
		}
		else
			threads = (Map<String, ExecThread>) getServletContext().getAttribute("threads");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
		/*--------------------------------------------------------*/
		/* GESTIONE DELLA RICHIESTA */
		
		//ricezione matrice//
		String[] matrice = req.getParameter("matrice").split(",");
		
		int matrix[][] = new int[5][5];
		int count=0;
		
		for(int i=0; i<5; i++) {
			for(int j=0; j<5; j++) {
				matrix[i][j] = Integer.parseInt(matrice[count]);
				count++;
			}
		}
		System.out.println("Servlet1: " + matrix.toString());
		
		//delegazione a un thread//
		ExecThread thread = new ExecThread(1, matrix);
		
		Map<String, ExecThread> threads = (Map<String, ExecThread>) getServletContext().getAttribute("threads");
		threads.put(thread.getName(), thread);
		
		try {
			thread.start();
			thread.join();
			
			threads.remove(thread.getName());
			
			Result result = new Result();
			result.setResult(thread.isResult());
			System.out.println("Servlet1: result: " +thread.isResult());
			
			if(thread.isResult()) {
				result.setSomma(thread.getSomma());
				System.out.println("Servlet1: SOMMMA: " +thread.getSomma());
			}
			
			resp.getWriter().write(gson.toJson(result));
			
		} catch (InterruptedException e) {
			System.out.println("Thread interrupted");
			e.printStackTrace();
			Result result = new Result();
			result.setMessage("Richiesta interrotta dall'amministratore");
			resp.getWriter().write(gson.toJson(result));
		}
		//---------------------------------------------------------------------------------//
		
	}
}
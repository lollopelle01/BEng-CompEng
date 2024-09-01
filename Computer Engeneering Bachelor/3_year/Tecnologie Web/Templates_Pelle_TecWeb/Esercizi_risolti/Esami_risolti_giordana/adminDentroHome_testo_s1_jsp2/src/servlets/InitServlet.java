package servlets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import beans.CounterRichieste;

public class InitServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Gson gson;
	
	@Override
	public void init() throws ServletException {
		this.gson = new Gson();
		
		getServletContext().setAttribute("gson", gson);
		System.out.println("Server inizializzato correttamente.");
		
		//mappa per le richieste per ogni sessione
		Map<String, Integer> richieste = new HashMap<>();
		getServletContext().setAttribute("richieste", richieste);
		
		//attributo per le richieste arrivate negli ultimi 60 minuti
		getServletContext().setAttribute("counter", new CounterRichieste());
		
		//lista per le sessioni attive negli ultimi 30 giorni
		getServletContext().setAttribute("sessioni_attive", new ArrayList<>());
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("./home.jsp");
	}
}

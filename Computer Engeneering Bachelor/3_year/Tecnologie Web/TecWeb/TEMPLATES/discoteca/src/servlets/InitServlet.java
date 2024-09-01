package servlets;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import beans.Tavolo;

public class InitServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Gson gson;
	Map<Integer, Tavolo> tavoli;
	Map<String, Integer> menu;
	
	@Override
	public void init() throws ServletException {
		this.gson = new Gson();
		
		//setto un attributo di applicazione per la gestione dei tavoli della discoteca
		this.tavoli = new HashMap<>();
		
		this.tavoli.put(1, new Tavolo(1));
		this.tavoli.put(2, new Tavolo(2));
		this.tavoli.put(3, new Tavolo(3));
		
		getServletContext().setAttribute("tavoli", tavoli);
		
		//mappa del menu che associa ad ogni nome di un drink il suo costo
		this.menu = new HashMap<>();
		
		this.menu.put("Margarita", 10);
		this.menu.put("Tequila sunrise", 15);
		this.menu.put("Gin lemon", 20);
		this.menu.put("Gin tonic", 10);
		this.menu.put("Benzina", 5);
		this.menu.put("Japanise", 30);
		
		getServletContext().setAttribute("menu", menu);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("./home.jsp");
	}
}

package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import beans.*;

public class Servlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	Gson g = new Gson();
	Tabellone tabellone;
	
	@SuppressWarnings("unchecked")
	@Override
	public void init() throws ServletException {
		// Inizializzazione database
		tabellone = new Tabellone();
		
		// Creo i gironi e li riempo
		ArrayList<Tennista> girone1, girone2, girone3, girone4;
		girone1 = new ArrayList<Tennista>();
		girone2 = new ArrayList<Tennista>();
		girone3 = new ArrayList<Tennista>();
		girone4 = new ArrayList<Tennista>();
		
		// Creo i tennisti 
		Tennista t;
		t = new Tennista("Nome1", 1, 10, 100, 0); girone1.add(t);
		t = new Tennista("Nome2", 2, 9, 50, 1); girone1.add(t);
		t = new Tennista("Nome3", 3, 8, 25, 3); girone2.add(t);
		t = new Tennista("Nome4", 4, 7, 12, 6); girone2.add(t);
		t = new Tennista("Nome5", 5, 6, 6, 12); girone3.add(t);
		t = new Tennista("Nome6", 6, 5, 3, 25); girone3.add(t);
		t = new Tennista("Nome7", 7, 4, 1, 50); girone4.add(t);
		t = new Tennista("Nome8", 8, 3, 0, 100); girone4.add(t);		
		
		// Inseriamo le parti del tabellone
		tabellone.getDatabase().put(1, girone1);
		tabellone.getDatabase().put(2, girone2);
		tabellone.getDatabase().put(3, girone3);
		tabellone.getDatabase().put(4, girone4);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Ricevo un giocatore e devo restituire tutto il suo girone
		String tennista = request.getParameter("tennista"); System.out.println(tennista);
		
		// Otteniamo il risultato
		ArrayList<Tennista> tennisti = this.tabellone.getTennisti(tennista);
		
		String json;
		if(!tennisti.isEmpty()) {
			// Inviamo il risultato
			json = g.toJson(tennisti);
		}
		else {
			// Inviamo errore
			json = g.toJson(-1);
		}
//		response.setContentType("application/json");
//		response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json); System.out.println(json);
	    response.getWriter().flush();
		
		
	}
}

package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Film;

//classe per la lettura da file e popolamento mappa
public class initServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	
	@Override
	public void init() throws ServletException {
		Map<String, Film> titoli = new HashMap<>(); //mappa che associa ad ogni titolo il suo film
		Map<String, List<Film>> attore = new HashMap<>(); //mappa che associa ad ogni attore la lista di film a cui ha preso parte
		
		//creazione mappa titoli//
		titoli.put("il boiler", new Film("il boiler", new ArrayList<>(), "un film davvero enorme",  2002, 0, 1, "horror"));
		titoli.put("il calabrese", new Film("il calabrese", new ArrayList<>(), "sfhiatah cazzuh",  2002, 5, 20, "commedia"));
		titoli.put("quasi scopamici", new Film("quasi scopamici", new ArrayList<>(), "vuoi fare pesc e pesc?",  2001, 6, 2, "tragi-comico"));
		titoli.put("presidente?", new Film("presidente?", new ArrayList<>(), "presidente?",  2002, 9, 400, "suspance"));
		titoli.put("i limoni signoraaaa", new Film("i limoni signoraaaa", new ArrayList<>(), "i limoni signoraaaa, SIGNORAAAAA",  2020, 10, 600, "thriller"));
		
		titoli.get("il boiler").aggiungiAttore("il boiler");
		titoli.get("il boiler").aggiungiAttore("il calabro");
		
		titoli.get("il calabrese").aggiungiAttore("giordana");
		titoli.get("il calabrese").aggiungiAttore("il calabro");
		
		titoli.get("quasi scopamici").aggiungiAttore("daniele");
		titoli.get("quasi scopamici").aggiungiAttore("sara");
		titoli.get("quasi scopamici").aggiungiAttore("sofia");
		
		titoli.get("presidente?").aggiungiAttore("federico");
		titoli.get("presidente?").aggiungiAttore("daniele");
		
		titoli.get("i limoni signoraaaa").aggiungiAttore("daniele");
		titoli.get("i limoni signoraaaa").aggiungiAttore("federico");
		titoli.get("i limoni signoraaaa").aggiungiAttore("giordana");
		titoli.get("i limoni signoraaaa").aggiungiAttore("il boiler");

		//creazione mappa attore//
		String[] a = {"il boiler", "il calabro", "giordana", "il calabro", "daniele", "sara", "sofia", "federico", "daniele"};
		System.out.println(a[0]);
		for(String s: a) {
			attore.put(s, new ArrayList<>());
			System.out.println(s);
			for(String ss : titoli.keySet()) {
				if(titoli.get(ss).controlloAttore(s)) {
					System.out.println(titoli.get(ss).toString());
					attore.get(s).add(titoli.get(ss));
				}
			}
		}
		
		getServletContext().setAttribute("titoli", titoli);
		getServletContext().setAttribute("attore", attore);
	}

	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		
		if(session.getAttribute("counter") ==null) {
			session.setAttribute("counter", 0);
		}
		resp.sendRedirect("./home.jsp");
	}
}

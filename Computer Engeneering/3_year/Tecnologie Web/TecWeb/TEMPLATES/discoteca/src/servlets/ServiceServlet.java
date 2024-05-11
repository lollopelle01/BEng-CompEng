package servlets;


import java.io.IOException;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import beans.Drink;
import beans.Result;
import beans.Tavolo;

public class ServiceServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7554977297793320847L;
	Gson gson;
	Random random;
	
	@Override
	public void init() throws ServletException {
		this.gson = new Gson();
		this.random = new Random(System.currentTimeMillis());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<Integer, Tavolo> tavoli = (Map<Integer, Tavolo>) getServletContext().getAttribute("tavoli");
		if(tavoli == null) {
			resp.sendRedirect("./init");
		}
		
			HttpSession session = req.getSession();
			
			int tavolo = (int) session.getAttribute("entrato");
			
			/*--------------------------------------------------------*/
			/* GESTIONE DELLA RICHIESTA */
			String op = (String) req.getParameter("op");
			System.out.println("Service: Utente (" + session + "), nel tavolo " + tavolo + ", ha richiesto il conto " + op);
			
			Result result = new Result();
			int totale = 0;
			
			if(op.equals("1")) { //calcolo del conto dell'intero tavolo
				
				for(HttpSession utenti: tavoli.get(tavolo).getConsumazioni().keySet()) {
					for(Drink d : tavoli.get(tavolo).getConsumazioni().get(utenti)) {
						totale += d.getCosto();
					}
				}
				System.out.println("Service: Utente (" + session + "), nel tavolo " + tavolo + ", ha richiesto il conto " + op +" di totale:" + totale);
				result.setTotale(totale);
				resp.getWriter().write(gson.toJson(result));
			}
			else {
				if(op.equals("2")){//calcolo del conto personale
					
					for(Drink d : tavoli.get(tavolo).getConsumazioni().get(session)) {
						totale += d.getCosto();
					}
					
					System.out.println("Service: Utente (" + session + "), nel tavolo " + tavolo + ", ha richiesto il conto " + op +" di totale:" + totale);
					result.setTotale(totale);
					resp.getWriter().write(gson.toJson(result));
				}
				else {
					result.setMessage("operazione non riconosciuta");
					resp.getWriter().write(gson.toJson(result));
				}
			}
			
			
		
	}
}

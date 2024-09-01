package servlets;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Giocatore;
import beans.User;

public class LoginServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6570539459993871887L;

	
	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Content-Type", "application/json");
		
		Map<String, User> database = (Map<String, User>) getServletContext().getAttribute("database");
		boolean prese[] = (boolean[]) getServletContext().getAttribute("carte_prese");
		
		if(database == null) {
			resp.sendRedirect("./init");
		}
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String result = "";
		
		HttpSession session = req.getSession();
		
		
		if(database.get(username) == null) {
			result = "Accesso negato: Utente inesistente.";
			session.setAttribute("logged", result);
			resp.sendRedirect("./login.jsp");
			
		} else if(!database.get(username).getPassword().equals(password)) {
			result = "Accesso negato: Password errata.";
			session.setAttribute("logged", result);
			resp.sendRedirect("./login.jsp");
		} else {
			result = "Logged.";
			session.setAttribute("logged", result);
			session.setAttribute("username", username);
			
			//verifica del numero di utenti
			Map<String, Giocatore> giocatori = (Map<String, Giocatore>) getServletContext().getAttribute("giocatori");
			
			if(giocatori.keySet().size()>3) {
				result = "Accesso negato: Numero di giocatori max raggiunto.";
				session.setAttribute("logged", result);
				resp.sendRedirect("./login.jsp");
			}else {
				//ora bisogna assegnare 10 carte (1-30) ai 3 giocatori, senza ripeterle
				List<Integer> carte = new ArrayList<>();
				System.out.println("LOGIN: Utente " + username + "loggato, assegnazione delle carte");
				for(int i=0; i<10; i++) {
					int cc;
					Random random = new Random();
					do {
						cc = random.nextInt(30); //range [0,30)
					}while(prese[cc]);
					
					prese[cc]=true; //la carta 0 in realtà indica la carta 1, usiamo 0 per operare con l'array prese, dopo cc++
					cc++;
					carte.add(cc);
					
					System.out.println("LOGIN: Utente " + username + ", ASSEGNATA CARTA " + cc);
				}
				
				giocatori.put(username, new Giocatore(username, carte));
				System.out.println("LoginServlet: si è unito il giocatore" + giocatori.get(username).toString());
				
				if(giocatori.keySet().size()==3) {
					//da ora settiamo il timer di 30 minuti
					getServletContext().setAttribute("start", LocalDateTime.now());
				}
				
				resp.sendRedirect("./home.jsp");
			}
		}
	}
}

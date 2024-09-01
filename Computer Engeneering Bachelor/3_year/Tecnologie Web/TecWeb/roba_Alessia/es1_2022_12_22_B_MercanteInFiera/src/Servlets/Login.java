package Servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Beans.Carta;
import Beans.Gioco;
import Beans.Utente;

//eventuali bean

public class Login extends HttpServlet {

    private static final long serialVersionUID = 1L;
	
	@Override
	public void init(ServletConfig config) throws ServletException  { 
        super.init(config);
        
        Gioco game = new Gioco();
        this.getServletContext().setAttribute("game", game);	
	}

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		 
	}
	 
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		String usr = req.getParameter("username");
		String pssw = req.getParameter("password");
		
		HttpSession session = req.getSession();
		
		if(usr == null || usr.isBlank() || usr.isEmpty() || pssw == null || pssw.isBlank() || pssw.isEmpty()) {
			session.setAttribute("err", "1");
			getServletContext().getRequestDispatcher("/login.jsp").forward(req, res);
			return;
		}
		
		Gioco game = (Gioco) this.getServletContext().getAttribute("game");
		ArrayList<Utente> users = game.getUsers();
		
		Utente u = new Utente(usr, pssw);
		users.add(u);
		
		if(users.size() > 3) {
			session.setAttribute("err", "2");
			getServletContext().getRequestDispatcher("/login.jsp").forward(req, res);
			return;
		}
		
		users.remove(u);
		
		
		//assegno carte
		List<Carta> carteDisp = game.getCarteUsate();
		List<Carta> carteUtente = new ArrayList<Carta>();
		
		for(int i = 0; i < 10; i++) {
			
			Random r = new Random();
			int val = r.nextInt(29)+1;
			
			boolean find = true;
			while(find) {
				if(carteDisp.get(val).getUtente() == null) {
					carteDisp.get(val).setUtente(u);
					carteUtente.add(carteDisp.get(val));
					find = false;
				} else {
					val = r.nextInt(29)+1;
				}
			}
		}

		u.setCarte(carteUtente);
		
		
		session.setAttribute("currentUser", u);
		
		getServletContext().getRequestDispatcher("/entra.jsp").forward(req, res);
		return;
    }
} 
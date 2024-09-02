package Servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.*;
import java.io.*;

//eventuali bean
import Beans.Tavolo;
import Beans.Utente;


public class Start extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    private static final String adminUsr = "username";
    private static final String adminPsw = "password";
    
	@Override
	public void init(ServletConfig config) throws ServletException  { 
		super.init(config);
		
		Map<String, Tavolo> tavoli = new HashMap<String, Tavolo>();
		
		//creo tavoli
		tavoli.put("t1", new Tavolo("t1"));
		tavoli.put("t2", new Tavolo("t2"));
		tavoli.put("t3", new Tavolo("t3"));
		
		this.getServletContext().setAttribute("tavoli", tavoli);		
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		 
	}
	 
    
    //gestisco richiesta accesso admin o utente
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//se sto ricevendo una doPost vuol dire che qualcuno ha cercato di accedere
		String usr = req.getParameter("username");
		String tavolo = req.getParameter("tavolo");
		
		//verifico se le credenziali appartengono all'amministratore
		if(usr.compareTo(adminUsr) == 0 && tavolo.compareTo(adminPsw) == 0) {
			//indirizzo la pagina sulla gestione amministrativa
			getServletContext().getRequestDispatcher("/preAdmin.jsp").forward(req, res);
			return;
		}
		
		HttpSession session = req.getSession();
		if(session.getAttribute("success") == usr) {
			session.setAttribute("err", "1");
			getServletContext().getRequestDispatcher("/login.jsp").forward(req, res);
			return;
		}
			
		//in questa Hashmap vado ad inserire i gruppi che ho settato in Registrazione
		Map<String, Tavolo> tavoli = (Map<String, Tavolo>) this.getServletContext().getAttribute("tavoli");
		
		if(tavoli.get(tavolo) == null) {
			session.setAttribute("err", "2");
			getServletContext().getRequestDispatcher("/login.jsp").forward(req, res);
			return;
		}
		
		Tavolo currentTable = tavoli.get(tavolo);
		
		Utente u = new Utente(usr, currentTable);
		
		currentTable.addUser(u);
				
		//a questo punto ho un utente che vuole entrare
		session.setAttribute("currentTable", currentTable);
		session.setAttribute("success", u);
		getServletContext().getRequestDispatcher("/home.jsp").forward(req, res);
		return;

    }
}   
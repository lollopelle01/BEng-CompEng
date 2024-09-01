package Servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.*;
import java.io.*;

import Beans.Articolo;

public class Start extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    private static final String adminUsr = "username";
    private static final String adminPsw = "password";
    
	@Override
	public void init(ServletConfig config) throws ServletException  { 
		super.init(config);
		
		List<Articolo> articoli = new ArrayList<>();
		this.getServletContext().setAttribute("articoli", articoli);		
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		 
	}
	 
    
    //gestisco richiesta accesso admin o utente
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//se sto ricevendo una doPost vuol dire che qualcuno ha cercato di accedere
		String usr = req.getParameter("username");
		
		String nomeArticolo = req.getParameter("nome");
		nomeArticolo = nomeArticolo.substring(0, nomeArticolo.length() - 1);
		
		//verifico se le credenziali appartengono all'amministratore
		if(usr.compareTo(adminUsr) == 0 && nomeArticolo.compareTo(adminPsw) == 0) {
			//indirizzo la pagina sulla gestione amministrativa
			getServletContext().getRequestDispatcher("/preAdmin.jsp").forward(req, res);
			return;
		}
		
		HttpSession session = req.getSession();
		if(session.getAttribute("success") == usr) {
			session.setAttribute("err", "1");
			getServletContext().getRequestDispatcher("/login.html").forward(req, res);
			return;
		}
			
		//in questa Hashmap vado ad inserire i gruppi che ho settato in Registrazione
		List<Articolo> articoliTot = (List<Articolo>) this.getServletContext().getAttribute("articoli");
		Articolo currentArt = null;
		
		boolean find = false;
		
		for(Articolo art : articoliTot) {
			//esiste l'articolo
			if(art.getNome().equals(nomeArticolo)) {
				System.out.println("trovato articolo " + nomeArticolo);
				currentArt = art;
				art.addUser(usr);
				session.setAttribute("status", "2");	//accesso articolo esistente
				find = true;
				break;			
			}
		}
		
		//non è stato trovato articolo -> lo creo
		if(find == false) {
			System.out.println("creato articolo " + nomeArticolo);
			currentArt = new Articolo(nomeArticolo);
			//aggiungo utente all'articolo
			currentArt.addUser(usr);
			//aggiungo articolo ai totali
			articoliTot.add(currentArt);
			
			session.setAttribute("status", "1");	//crato articolo			
		}
		
		//a questo punto ho un utente che vuole entrare
		session.setAttribute("currentArticle", currentArt);
		session.setAttribute("success", usr);
		getServletContext().getRequestDispatcher("/preHome.jsp").forward(req, res);
		return;

    }
}   
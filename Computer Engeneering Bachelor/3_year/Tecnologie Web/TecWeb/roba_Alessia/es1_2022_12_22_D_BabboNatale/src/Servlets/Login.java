package Servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Beans.Utente;
import Beans.Regalo;
import Beans.Asta;

import java.io.*;
import java.util.Date;

//eventuali bean

public class Login extends HttpServlet {

    private static final long serialVersionUID = 1L;
	
	@Override
	public void init(ServletConfig config) throws ServletException  { 
        super.init(config);
        
        Asta asta = new Asta();
        
        asta.addRegalo(new Regalo("Orologio", 		"Orologio non funzionante delle Barbie", 					"America", 	15.50));
        asta.addRegalo(new Regalo("Porta Occhiali",	"Porta occhiali per le mamme che stanno diventando nonne", 	"Europa", 	19.99));
        asta.addRegalo(new Regalo("Computer", 		"Laptop computer Hp ne ho tre a casa", 						"Europa", 	89.00));
        asta.addRegalo(new Regalo("Bracciale", 		"Bracciale pandora arrugginito e in regalo il tetano", 		"Asia", 	7.99));
        asta.addRegalo(new Regalo("Viaggio canarie","In realtà sono le canarie del Salento, ancora meglio", 	"Oceania", 	89.99));
    
        this.getServletContext().setAttribute("asta", asta);	
	}

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		 
	}
	 
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		String usr = req.getParameter("username");
		String pssw = req.getParameter("password");
		
		HttpSession session = req.getSession();
		
		Asta asta = (Asta) this.getServletContext().getAttribute("asta");	
		
		if(usr == null || usr.isBlank() || usr.isEmpty() || pssw == null || pssw.isBlank() || pssw.isEmpty()) {
			session.setAttribute("err", "1");
			getServletContext().getRequestDispatcher("/login.jsp").forward(req, res);
			return;
		}
		
		Utente u = new Utente(usr, pssw);
		asta.addUsers(u);
		
		session.setAttribute("utente", u);
		
		if(asta.getUsers().size() >= 4 && asta.getStartTime() == -1) {
			asta.setStartTime(new Date().getTime());
		}
		
		session.setAttribute("esitoOfferta", null);
		getServletContext().getRequestDispatcher("/wait.jsp").forward(req, res);
		return;
    }
} 
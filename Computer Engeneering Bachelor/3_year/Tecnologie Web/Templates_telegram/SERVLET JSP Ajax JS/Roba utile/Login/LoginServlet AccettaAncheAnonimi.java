package servlets;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Utente;
import beans.UtentiManager;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UtentiManager utentiManager;
    
    @Override
	public void init() throws ServletException {
    	super.init();
    	ServletContext application = this.getServletContext();
		synchronized (application) {
			//Inizializzazione stato di applicazione
			utentiManager = (UtentiManager) application.getAttribute("utentiManager");
			if (utentiManager == null) {
				utentiManager = new UtentiManager();
				application.setAttribute("utentiManager", utentiManager);
			}
		}
    }

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		if (nome != null && cognome != null) {
			Utente utente = new Utente(nome, cognome);
			if (utentiManager.hasUtente(utente)) {
				utente = utentiManager.getUtente(nome, cognome);
				request.getSession().setAttribute("utente", utente);
				request.getServletContext().getRequestDispatcher("/portale.jsp").forward(request, response);
			} else {
				if (utentiManager.addUtente(utente)) {
					request.getSession().setAttribute("utente", utente);
					request.getServletContext().getRequestDispatcher("/portale.jsp").forward(request, response);
				} else {
					request.getServletContext().getRequestDispatcher("/portale.jsp").forward(request, response);
				}
			}
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}


}

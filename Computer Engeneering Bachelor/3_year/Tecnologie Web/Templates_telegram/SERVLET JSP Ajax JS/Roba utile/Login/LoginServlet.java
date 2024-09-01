package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.AstaManager;
import beans.Utente;


public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private AstaManager astaManager = null;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServletContext application = getServletContext();
		synchronized (application) {
			astaManager = (AstaManager) application.getAttribute("astamanager");
			if ( astaManager == null) {
				astaManager = new AstaManager();
				application.setAttribute("astamanager", astaManager);
			}
		}
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		if (checkCredenziali(nome, cognome)) {
			Utente utente = new Utente();
			utente.setNome(nome);
			utente.setCognome(cognome);
			astaManager.addUtente(utente);
			request.getSession().setAttribute("utente", utente);
		}
		else {
			request.setAttribute("loginerror", "Utente non valido");
		}
		getServletContext().getRequestDispatcher("/accessoServlet").forward(request, response);
		
	}
	
	//Metodo mock per la gestione delle credenziali di accesso.
	private boolean checkCredenziali (String utente, String password) {
		if (utente != null && password != null)
			return true;
		else
			return false;
	}

}
